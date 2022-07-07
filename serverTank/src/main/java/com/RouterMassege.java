package main.java.com;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.tanks2d.ClientNetWork.Heading_type;
import com.mygdx.tanks2d.ClientNetWork.Network;

import main.java.com.Units.ListPlayer.StatusPlayer;

public class RouterMassege {

    public static void routeSM(Network.StockMessOut sm, int id_coonect, GameServer gameServer) {
        System.out.println("-->> " + sm);
        if (Heading_type.MY_SHOT == sm.tip) {
            Vector2 velBullet = new Vector2(700, 0).setAngleDeg(sm.p3);
            gameServer.getMainGame().bullets.addBullet(new Vector2(sm.p1, sm.p2), velBullet, (int) sm.p4, id_coonect);
            gameServer.getServer().sendToAllTCP(sm);
            //System.out.println("shooooooooooot");
            return;
        }

        if (Heading_type.BUTTON_STARTGAME == sm.tip) {
            gameServer.lp.getPlayerForId(id_coonect).setNikName(sm.textM);
            gameServer.lp.getPlayerForId(id_coonect).setStatus(StatusPlayer.CLICK_BOTON_START);

            return;
        }
        if (Heading_type.MY_TOKKEN == sm.tip) {
            gameServer.lp.getPlayerForId(id_coonect).setTokken(sm.textM);
            return;
        }


        if (Heading_type.STATUS_GAME == sm.tip) {
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!");
            gameServer.lp.getPlayerForId(id_coonect).setCommand((int)sm.p1);
            System.out.println(gameServer.lp.getPlayerForId(id_coonect).getCommand() + " ---");
          //  gameServer.getLp().getPlayerForId(id_coonect).setCommand();
            System.out.println();
            System.out.println();
            /// ответить время . карта параметры игры
            return;
        }




    }
}
