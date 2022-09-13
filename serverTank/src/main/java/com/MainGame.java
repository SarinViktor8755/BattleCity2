package main.java.com;

import com.badlogic.gdx.math.MathUtils;
import com.mygdx.tanks2d.Locations.GameSpace;
import com.mygdx.tanks2d.Locations.MapsList;

import main.java.com.MatchOrganization.IndexMath;
import main.java.com.Units.Bullet.IndexBullets;
import main.java.com.Units.SpaceMap.IndexMap;

public class MainGame {
    GameServer gameServer;
    IndexBullets bullets;
    IndexMap mapSpace;
    IndexMath indexMath;
    // IndexBot bot;



    public final long timer_tread_50 = 25; //ms поток таймер циклов , рассылвает координаты ботов ))
    public final long timer_tread_25 = 15; // таймер поведения ботов - 25

    public static int targetPlayer = 2;

    public MainGame(GameServer gameServer, int targetPlayer) {
        MainGame.targetPlayer = targetPlayer;

        this.gameServer = gameServer;
        this.bullets = new IndexBullets(this.gameServer);
        this.mapSpace = new IndexMap(MapsList.getMapForServer()); // создание карты
        startSecondaryThread_50();
        startSecondaryThread_25();

        indexMath = new IndexMath();


    }

    public IndexMath getIndexMath() {
        return indexMath;
    }

    public IndexBullets getBullets() {
        return bullets;
    }

    private void startSecondaryThread_50() { // выполняется каждые 50 мс
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        if (gameServer.isServerLivePlayer()) Thread.sleep(timer_tread_50);
                        else Thread.sleep(450);




//                        поток 50 можно остоновить при отсутвии игрков
//                                нужно будет обнулить игру результаты

                        gameServer.indexBot.updateCountBot(gameServer.countLivePlayer(), targetPlayer); // контроль количество ботов
             //           System.out.println("is_end_math : " + is_end_math());

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    public IndexMap getMapSpace() {
        return mapSpace;
    }

    private void startSecondaryThread_25() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        if (gameServer.isServerLivePlayer()) Thread.sleep(timer_tread_25);
                        else Thread.sleep(timer_tread_50);


                        long deltaTime = GameServer.getDeltaTime();
                        indexMath.updateMath(deltaTime, gameServer.lp); // время матча

                        //     System.out.print("+");

                        float time = (float) (deltaTime * .001);
                        bullets.updateBulets(deltaTime);
                        gameServer.indexBot.updaeteBot(time);

                 //      gameServer.lp.respaunPlayer();

                       // gameServer.lp.re


                        // System.out.println("---");

//     не останавливать поток все функции должны быть конечными )))


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

 ///если конец матча

}
