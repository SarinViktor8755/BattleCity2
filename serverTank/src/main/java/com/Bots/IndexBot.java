package main.java.com.Bots;

import static main.java.com.Bots.BehaviourBot.SPEED_BULLET;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.tanks2d.ClientNetWork.Heading_type;
import com.mygdx.tanks2d.ClientNetWork.Network;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import main.java.com.GameServer;
import main.java.com.Units.ListPlayer.ListPlayers;
import main.java.com.Units.ListPlayer.Player;

public class IndexBot extends Thread {
    public static GameServer gs;
    ConcurrentHashMap<Integer, DBBot> dbBots;


    private static BehaviourBot botBehavior;

    private static int NOM_ID_BOT = -100;

    private final Vector2 speed_constanta = new Vector2(90, 0);

    private Vector2 temp_position_vector;
    private static Vector2 temp_position_vector_static;
    private int countBot;


    private int sizeBot = 10;

    private TowerRotationLogic tr;


    public IndexBot(GameServer gameServer, int number_bots) {
        this.countBot = number_bots;
        this.temp_position_vector = new Vector2();

        this.gs = gameServer;
        this.dbBots = new ConcurrentHashMap<>();
        this.sizeBot = number_bots;
        this.tr = new TowerRotationLogic();
        // this.botBehavior = new BotBehavior(botList); // поведение бота - тут вся логика  )))
        // this.allPlayers = new HashMap<Integer, TowerRotation>();

        System.out.println("install_Bot : " + GameServer.getDate() + "  " + countBot);
    }


    private void addBot() {
        Player p = new Player(NOM_ID_BOT);

        if (MathUtils.randomBoolean()) p.setCommand(Heading_type.RED_COMMAND);
        else p.setCommand(Heading_type.BLUE_COMMAND);

        p.setPosition(MathUtils.random(200, 1100), MathUtils.random(200, 1100));
        p.setHp(100);
        p.setNikName(getNikNameGen());

        NOM_ID_BOT--;
        System.out.println("add_bot+ : " + NOM_ID_BOT);

        gs.getLp().addPlayer(p); // добавляем в базу играков

        DBBot bot = new DBBot(p.getId());
        dbBots.put(p.getId(), bot);

    }

//    public void upateMainPlayerList(ListPlayers listPlayers) { //обносить основй список играков )_)) закидуть ботов в листПлаер
//        Iterator<Map.Entry<Integer, DBBot>> bot = this.dbBots.entrySet().iterator();
//        while (bot.hasNext()) {
//            listPlayers.accept_bot((DBBot) bot);
//        }
//
//    }

    public void send_bot_coordinates() {
        gs.getLp().send_bot_coordinates();
    }

    public void updaeteBot(float deltaTime) {

        actionBot(deltaTime);

        send_bot_coordinates();
    }


    private void attackBot(DBBot dbtank, float deltaTime, Player tank) {
        dbtank.updateTackAttack(deltaTime);
        if (!tank.isLive()) return;
        if (dbtank.isRedyToAttac()) {
            if (dbtank.getNomTarget() == null) return;
            // gs.getLp().getPlayerForId(tank.getValue().getId()).setRotTower(MathUtils.random(360));
            //    dbtank.setTarget_angle_rotation_tower(MathUtils.random(360));

            botShoot(tank.getId());
        }
    }

    //////////////////////////////
    public void actionBot(float deltaTime) { // перемещения поля
        Iterator<Map.Entry<Integer, DBBot>> entries = dbBots.entrySet().iterator();
        while (entries.hasNext()) {
            try {
                Map.Entry<Integer, DBBot> entry = entries.next();
                DBBot tank = entry.getValue();
                Player p = gs.getLp().getPlayerForId(tank.getId());
                respaunBot(p);
                DBBot dbtank = dbBots.get(p.getId());

                attackBot(tank, deltaTime, p);/// атака бота
                gs.getMainGame().getMapSpace().resolving_conflict_with_objects(p.getPosi(), deltaTime); /// проверка столкновений с обьектами
                collisinOtherTanksTrue(p.getPosi(), deltaTime, p.getBody_rotation()); /// calisiion tanks

                // if(MathUtils.randomBoolean(.005f))tank.getValue().getTarget_body_rotation_angle().setAngleDeg(MathUtils.random(-180,180));
                TowerRotationLogic.updateTowerRotation(deltaTime, tank, p, gs.getLp()); /// поворот башни
                gs.getMainGame().getMapSpace().returnToSpace(p.getPosi());
                moveBot(deltaTime, tank, p, gs.getLp());
            } catch (ConcurrentModificationException e) {
                e.printStackTrace();
            }
        }

    }

    private void moveBot(float deltaTime, DBBot tank, Player p, ListPlayers lp) {
        rotation_body(deltaTime, tank, p.getBody_rotation()); // поворот туловеща
        p.getPosi().sub(p.getBody_rotation().cpy().scl(deltaTime * 90)); /// перемещение танка
        // перемещеени вперед



    }

    private void respaunBot(Player p) {
        if (!p.isLive()) {
            if (MathUtils.randomBoolean(0.05f)) {
                p.setHp(100);
                p.setPosition(MathUtils.random(0, 1000), MathUtils.random(0, 1000));
                gs.send_PARAMETERS_PLAYER(p);
            }
        }
    }

    private void collisinOtherTanksTrue(Vector2 position, float dt, Vector2 rotation) {
        Vector2 ct = gs.getLp().isCollisionsTanks(position);
        if (ct != null) {  // танки другие
            //System.out.println("collisinOtherTanksTrue");
            position.sub(rotation.cpy().scl(dt * 90 * -2.5f)); // тут вроде норм
        }
    }


    private static void rotation_body(float dt, DBBot db_bot, Vector2 rotaton) { // поворот тела
        boolean a = MathUtils.isEqual(rotaton.angleDeg(), db_bot.getTarget_body_rotation_angle().angleDeg(), 4);
        if (MathUtils.randomBoolean(.05f))
            db_bot.getTarget_body_rotation_angle().rotateDeg(MathUtils.random(180));
        //   System.out.println(rotaton.angleDeg() + "  " + db_bot.getTarget_body_rotation_angle().angleDeg() + "  " + MathUtils.isEqual(db_bot.getTarget_body_rotation_angle().angleDeg(), rotaton.angleDeg()));
        if (!a) {
            if ((rotaton.angleDeg(db_bot.getTarget_body_rotation_angle()) > 180)) {
                rotaton.rotateDeg(3);
            } else rotaton.rotateDeg(-3);
        }
    }

    /////////////////////////////////////////////////


    public void updateCountBot(int lPlayers, int target_plaers) {
        if ((dbBots.size() + lPlayers) < target_plaers) addBot();
        if ((dbBots.size() + lPlayers) > target_plaers) delBot();
        //    System.out.println(lPlayers + "  " + dbBots.size());
    }

    public static void botShoot(int id) { /// выстрел LAVEL_1
        Player p = gs.getLp().getPlayerForId(id);
        Player bot = gs.getLp().getPlayerForId(id);
        Vector2 velBullet = new Vector2(SPEED_BULLET, 0).setAngleDeg(bot.getRotTower());

        Network.StockMessOut sm = new Network.StockMessOut();

        //   IndexBot.temp_position_vector_static.setAngleDeg(p.getRotTower());

        Vector2 rot = new Vector2(1, 0).setAngleDeg(p.getRotTower()).scl(-30);
        Vector2 smooke = p.getPosi().cpy().sub(rot);

        int n = 5000 + MathUtils.random(99999999);
        sm.p1 = smooke.x;
        sm.p2 = smooke.y;
        sm.p3 = p.getRotTower();
        sm.p4 = n;
        sm.tip = Heading_type.MY_SHOT;

        gs.getMainGame().getBullets().addBullet(new Vector2(bot.getPosi().x, bot.getPosi().y), velBullet, n, bot.getId());
        gs.getServer().sendToAllTCP(sm);
    }


    private void delBot() {

    }


    private void delBot(int id) {

    }


    static String getNikNameGen() {
        ArrayList<String> names = new ArrayList<>();
        names.add("Bubba");
        names.add("Honey");
        names.add("Bo");
        names.add("Sugar");
        names.add("Doll");
        names.add("Peach");
        names.add("Snookums");
        names.add("Queen");
        names.add("Ace");
        names.add("Punk");
        names.add("Sugar");
        names.add("Gump");
        names.add("Rapunzel");
        names.add("Teeny");
        names.add("MixFix");
        names.add("BladeMight");
        names.add("Rubogen");
        names.add("Lucky");
        names.add("Tailer");
        names.add("IceOne");
        names.add("Sugar");
        names.add("Gump");
        names.add("Rapunzel");
        names.add("Teeny");
        names.add("MixFix");
        names.add("BladeMight");
        names.add("Rubogen");
        names.add("Lucky");
        names.add("Tailer");
        names.add("IceOne");
        names.add("TrubochKa");
        names.add("HihsheCKA");
        names.add("R2-D2");
        names.add("Breha Organa");
        names.add("Yoda");
        names.add("Obi-Wan Kenob");
        names.add("C-3PO");
        names.add("Darth Sidious");
        names.add("Darth Vader");
        names.add("Boba Fett");
        names.add("Sarin");
        names.add("Sasha");
        return names.get(MathUtils.random(names.size() - 1)) + "@Bot";
    }


}
