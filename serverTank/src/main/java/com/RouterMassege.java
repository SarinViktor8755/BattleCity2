package main.java.com;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.tanks2d.ClientNetWork.Heading_type;
import com.mygdx.tanks2d.ClientNetWork.Network;

public class RouterMassege {

    public static void routeSM(Network.StockMessOut sm,int id_coonect, GameServer gameServer){
        if (Heading_type.MY_SHOT == sm.tip) {
            Vector2 velBullet = new Vector2(700, 0).setAngleDeg(sm.p3);
            gameServer.getMainGame().bullets.addBullet(new Vector2(sm.p1, sm.p2), velBullet, (int)sm.p4,id_coonect);
            gameServer.getServer().sendToAllTCP(sm);
            //System.out.println("shooooooooooot");
            return;
        }

        if (Heading_type.BUTTON_STARTGAME == sm.tip) {
            gameServer.lp.getPlayerForId(id_coonect).setNikName(sm.textM);

            // ответ массив имен играков )))

            return;
        }
        if (Heading_type.MY_TOKKEN == sm.tip) {
           // System.out.println(sm.textM);
            gameServer.lp.getPlayerForId(id_coonect).setTokken(sm.textM);
            return;
        }


    }
}
