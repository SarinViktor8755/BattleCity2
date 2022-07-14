package main.java.com.Units.ListPlayer;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.tanks2d.ClientNetWork.Heading_type;
import com.mygdx.tanks2d.ClientNetWork.Network;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.esotericsoftware.kryonet.Connection;

import main.java.com.Bots.TowerRotationLogic;
import main.java.com.GameServer;
import main.java.com.MatchOrganization.IndexMath;

public class ListPlayers {
    static public final int DEFULT_COUNT_BOT = 20;

    ConcurrentHashMap<Integer, Player> players;
    ConcurrentHashMap<String, Integer> playersTokken; // tooken/ id

    GameServer gameServer;
    Network.PleyerPositionNom pn = new Network.PleyerPositionNom();

    Vector2 temp1 = new Vector2();
    Vector2 temp2 = new Vector2();

    static final float MAX_DIST = 150 * 150;

    private int blue_size;
    private int red_size;

    private static Vector2 blue_average = new Vector2(0, 0);
    private static Vector2 red_average = new Vector2(0, 0);
    private static Vector2 average_cord = new Vector2(0, 0);

    public ListPlayers(GameServer gameServer) {
        this.players = new ConcurrentHashMap<>();
        this.playersTokken = new ConcurrentHashMap<>();
        this.gameServer = gameServer;

        //  System.out.println("install_ListPlayers : " + GameServer.getDate());

        red_size = 0;
        blue_size = 0;
    }

    public Player getPlayerForId(int id) { // почему то вызывается  иногда
        Player result = players.get(id);
        if (result == null)
            players.put(id, new Player(id, gameServer.getMainGame().getIndexMath().getCommand()));
        return players.get(id);
    }


    private boolean checkTokken(String tokken, int connct_id) { // проверяет был литакой токкен
        playersTokken.put(tokken, connct_id);
        Integer p = playersTokken.get(tokken);
        if (p == null) {
            playersTokken.put(tokken, connct_id);
            return false;
        } else {
            return true;
        }
    }

    public void addPlayer(int con) {
        update_the_average_coordinates_of_the_commands();
        this.players.put(con, new Player(con, gameServer.getMainGame().getIndexMath().getCommand()));
        //   System.out.println(this.players);
    }

    public void addPlayer(Player p) { // конструктоор для ботов
        update_the_average_coordinates_of_the_commands();
        this.players.put(p.getId(), p);
    }

    public void clearList() {
        this.players.clear();
    }


    public int getSize() {
        return this.players.size();
    }


    public void updatePosition(int id, Network.PleyerPosition pp) { // записать парметры Игрока
        Player p = players.get(id);
        if (p == null)
            players.put(id, new Player(id, gameServer.getMainGame().getIndexMath().getCommand()));

        p.setPosition(pp.xp, pp.yp);
        p.setRotTower(pp.roy_tower);
    }

    public void sendToAllPlayerPosition(int id, Network.PleyerPosition pp) {
        pn.nom = id;
        pn.xp = pp.xp;
        pn.yp = pp.yp;
        pn.roy_tower = pp.roy_tower;
        gameServer.getServer().sendToAllExceptTCP(id, pn);
    }

//    public void accept_bot(DBBot dbBot) {
//        Player p_bot = getPlayerForId(dbBot.getId());
//        p_bot.getPosi().x = dbBot.getPosition().x;
//        p_bot.getPosi().y = dbBot.getPosition().y;
//        p_bot.rotTower = dbBot.getAngle_rotation_tower().angleDeg();
//    }

    public void sendParametersPlayers(int aboutPlayerID) { // рассылка о характеристиках игрока по id
        Network.StockMessOut sm = new Network.StockMessOut();
        Player p = this.players.get(aboutPlayerID);
        try {
            //  System.out.println(">>>" + p.nikName);
            sm.textM = p.nikName;
            sm.p1 = aboutPlayerID;
            sm.p2 = p.command;
            sm.p3 = p.hp;
            //sm.p4 = еще какой т опараметр;
            sm.tip = Heading_type.PARAMETERS_PLAYER;
            gameServer.getServer().sendToAllExceptTCP(aboutPlayerID, sm);
        } catch (NullPointerException e) {
        }
    }

    public int projectile_collide_with_players(int author_id, float xs, float ys) {
        int res = -1;
        temp1.set(xs, ys);
        Iterator<Map.Entry<Integer, Player>> entries = players.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<Integer, Player> entry = entries.next();
            if (entry.getValue().hp < 1) continue;
            temp2.set(entry.getValue().getPosi().x, entry.getValue().getPosi().y);
            if (((temp1.dst2(temp2) < 500) && author_id != entry.getValue().getId()))
                res = entry.getKey();
            if (res != -1) return res;
        }
        return res;
    }


    public void send_bot_coordinates() {
        if(MathUtils.randomBoolean(.005f))update_the_average_coordinates_of_the_commands();
        Iterator<Map.Entry<Integer, Player>> entries = players.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<Integer, Player> entry = entries.next();
            if (entry.getKey() > -99) continue;
            Player p = entry.getValue();
            if (p.getPosi().x == StatusPlayer.IN_MENU) continue;
            pn.nom = entry.getKey();
            pn.xp = p.getPosi().x;
            pn.yp = p.getPosi().y;
            pn.roy_tower = p.getRotTower();
            //gameServer.getServer().sendToAllUDP(pn);
            //  Object object;
            Connection[] connections = this.gameServer.getServer().getConnections();
            for (int i = 0, n = connections.length; i < n; i++) {
                Connection connection = connections[i];
                //  System.out.println(getPlayerForId(connection.getID()).isClickButtonStart());
                if (!getPlayerForId(connection.getID()).isClickButtonStart()) continue;
                connection.sendUDP(pn);
            }
        }
    }


    private void update_number_of_clicks(int coomand) {
        if (coomand == Heading_type.BLUE_COMMAND) blue_size++;
        if (coomand == Heading_type.RED_COMMAND) red_size++;
    }

    public void update_the_average_coordinates_of_the_commands() { // обновить средние координаты команд
        Iterator<Map.Entry<Integer, Player>> entries = this.players.entrySet().iterator();
        float xb = 0;
        float yb = 0;
        float xr = 0;
        float yr = 0;
        int r = 0;
        int b = 0;
        while (entries.hasNext()) {
            Player p = entries.next().getValue();
            if (p.getCommand() == Heading_type.BLUE_COMMAND) {
                xb += p.getPosi().x;
                yb += p.getPosi().y;
                b++;
            }
            if (p.getCommand() == Heading_type.RED_COMMAND) {
                xr += p.getPosi().x;
                yr += p.getPosi().y;
                r++;
            }
        }
        ListPlayers.blue_average.set(xb / b, yb / b);
        ListPlayers.red_average.set(xr / r, yr / r);

        ListPlayers.average_cord.set( ((ListPlayers.blue_average.x + ListPlayers.red_average.x)/2) ,((ListPlayers.blue_average.y + ListPlayers.red_average.y)/2));
        System.out.println(blue_average + "  " + r + "   " + b + "  " + red_average + "    --  " + average_cord);


    }


//////////////collisin

    public Vector2 isCollisionsTanks(Vector2 pos) {
        red_size = 0;
        blue_size = 0;
        for (Map.Entry<Integer, Player> tank : this.players.entrySet()) {
            update_number_of_clicks(tank.getValue().getCommand());
            // System.out.println(tank.getValue().id + "  isCollisionsTanks");
            if (!tank.getValue().isLive()) continue;
            if (tank.getValue().isCollisionsTanks(pos))
                return new Vector2().set(pos.cpy().sub(tank.getValue().pos).nor());
        }
        // System.out.println("red " + red_size + " " + "blue " + blue_size + "  " + (blue_size+red_size));
        return null;
    }


///////////////////

    public Integer targetTankForBotAttack(Vector2 myPosi, Player my) { // найти цель для бота - просто перебор кто первый попадется
        Iterator<Map.Entry<Integer, Player>> entries = players.entrySet().iterator();
        while (entries.hasNext()) {
            Player p = entries.next().getValue();
            if (my.getCommand() == p.getCommand()) continue;
            float dst = p.getPosi().dst2(myPosi);
            if (dst > TowerRotationLogic.rast_to_target) continue;
            if (dst < 5) continue;
            if (MathUtils.randomBoolean() && myPosi.equals(p.getPosi())) continue;
            return p.id;

        }
        return null;

    }

    public Vector2 search_for_nearest_tank(float x, float y) {
        float min_dst = 100_000;

        Vector2 result = null;
        Iterator<Map.Entry<Integer, Player>> entries = players.entrySet().iterator();
        while (entries.hasNext()) {
            Player p = entries.next().getValue();
            if (!p.isLive()) continue;
            float d = p.getPosi().dst2(x, y);

            if (d < min_dst && d != 0) {
                min_dst = d;
                result = p.getPosi();
            }
        }
        //  System.out.println(min_dst + " !!!");
        if (min_dst > MAX_DIST || min_dst == 100_000) result = null;
        return result;

    }

    public Vector2 search_for_nearest_tank(Vector2 p) {
        return this.search_for_nearest_tank(p.x, p.y);
    }

    public int getCommandForId(int id) { // взять команду по ид ))) не тестировалоась
        try {
            return players.get(id).getCommand();
        } catch (NullPointerException e) {
            return 0;
        }

    }


    public int blue_players_size() {
        return this.blue_size;
    }

    public int red_players_size() {
        return this.red_size;
    }


    public void respaunPlayer() { // респаун игрока живого - новый плеер
        Iterator<Map.Entry<Integer, Player>> entries = players.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<Integer, Player> entry = entries.next();
            if (entry.getKey() < -1) continue;
            Player p = entry.getValue();
            if (!p.isLive()) {
                if (MathUtils.randomBoolean(0.05f)) {
                    p.setHp(100);
                    if (p.getCommand() == Heading_type.RED_COMMAND) p.setPosition(1000, 1000);
                    else p.setPosition(10, 10);
                    gameServer.send_PARAMETERS_PLAYER(p);

                    System.out.println("!!!!!!!! RESPOWN" + p.getPosi());
                }
            }
        }
    }


}
