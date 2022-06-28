package main.java.com;

import com.badlogic.gdx.math.MathUtils;

import main.java.com.Units.Bullet.IndexBullets;
import main.java.com.Units.SpaceMap.IndexMap;

public class MainGame {
    GameServer gameServer;
    IndexBullets bullets;
    IndexMap mapSpace;
    // IndexBot bot;

    private static float realTimeMath; // время матча
    private final static float MATH_LENGHT = 1000 * 60 * 2; // время матча

    public final long timer_tread_50 = 25; //ms поток таймер циклов , рассылвает координаты ботов ))
    public final long timer_tread_25 = 15; // таймер поведения ботов - 25

    public static int targetPlayer = 10;

    public MainGame(GameServer gameServer, int targetPlayer) {
        MainGame.targetPlayer = targetPlayer;
        // this.bot = new IndexBot(this,number_of_bots);
        realTimeMath = 0;
        this.gameServer = gameServer;
        this.bullets = new IndexBullets(this.gameServer);
        this.mapSpace = new IndexMap();
        startSecondaryThread_50();
        startSecondaryThread_25();


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
                        realTimeMath += deltaTime; // время матча

                        //     System.out.print("+");

                        if (!gameServer.isServerLivePlayer()) {
                            if (MathUtils.randomBoolean(.005f)) realTimeMath = 0;
                        }


                        float time = (float) (deltaTime * .001);
                        bullets.updateBulets(deltaTime);

                        gameServer.indexBot.updaeteBot(time);


                        // System.out.println("---");

//     не останавливать поток все функции должны быть конечными )))


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private boolean is_end_math() {
        if (MainGame.realTimeMath > MainGame.MATH_LENGHT) return true;
        else return false;

    }

}
