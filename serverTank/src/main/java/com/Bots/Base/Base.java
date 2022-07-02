package main.java.com.Bots.Base;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.tanks2d.ClientNetWork.Heading_type;
import com.mygdx.tanks2d.ClientNetWork.Network;

import java.util.concurrent.ConcurrentHashMap;

import main.java.com.Bots.DBBot;
import main.java.com.GameServer;
import main.java.com.Units.ListPlayer.Player;

public class Base {
    public static GameServer gs;
    ConcurrentHashMap<Integer, DBBot> dbBots;

    final static float SPEED_ROTATION = 180f;
    final static float SPEED_BULLET = 700;

    private static int NOM_ID_BOT = -10_00;

    private float time_attack;


    public void updaeteBase(float deltaTime){

    }

    private void attackBot(DBBot dbtank, float deltaTime, Player tank) {

    }

    public static void baseShoot(int id) { /// выстрел LAVEL_1
        Player p = gs.getLp().getPlayerForId(id);
        Player bot = gs.getLp().getPlayerForId(id);
        Vector2 velBullet = new Vector2(SPEED_BULLET, 0).setAngleDeg(bot.getRotTower());

        Network.StockMessOut sm = new Network.StockMessOut();


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



}
