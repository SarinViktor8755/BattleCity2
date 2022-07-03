package com.mygdx.tanks2d.ClientNetWork;


import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.tanks2d.MainGame;
import com.mygdx.tanks2d.Units.Tanks.OpponentsTanks;
import com.mygdx.tanks2d.Units.Tanks.Tank;

public class RouterSM {

    MainGame mainGame;

    static Vector2 velocity;
    static Vector2 position;


    static Vector2 positionTemp;


    public RouterSM(MainGame mainGame) {
        velocity = new Vector2();
        position = new Vector2();
        positionTemp = new Vector2();
        this.velocity.set(45, 1);
        this.position.set(1, 1);

        this.mainGame = mainGame;
    }

    public void routeSM (Network.StockMessOut sm) throws NullPointerException{
        System.out.println("-->>> in :: " + sm.tip);
        if (Heading_type.MY_SHOT == sm.tip) {
            try {
            position.set(sm.p1, sm.p2);
            velocity.set(0, 400);
            velocity.setAngleDeg(sm.p3); /// навправление

                mainGame.getGamePlayScreen().playAnimation(position, velocity, (int) sm.p4);
            } catch (NullPointerException e) {
                  e.printStackTrace();
            }
            return;
        }

        if (Heading_type.SHELL_RUPTURE == sm.tip) { // РАЗРЫВ СНАРЯДА
            try {
            Vector2 pp = new Vector2(sm.p1, sm.p2);

                // Vector2 v = mainGame.getGamePlayScreen().getBullets().getBullet((int) sm.p3).direction;
                mainGame.getGamePlayScreen().playExplosion(pp, velocity);
                System.out.println(sm.p3 + "-------------");
                Vector2 v = mainGame.getGamePlayScreen().getBullets().removeBullet((int) sm.p3);
                v.rotateDeg(180);

                for (int i = 0; i < MathUtils.random(10, 30); i++) {
                    v.rotateDeg(MathUtils.random(-20, 20));
                    mainGame.getGamePlayScreen().getPc().addShares(sm.p1, sm.p2, v.x, v.y);
                }
            } catch (NullPointerException e) {
                  e.printStackTrace();
                    return;
            }
            return;
        }

        if (Heading_type.PARAMETERS_PLAYER == sm.tip) {
            try {
//                stockMessOut.tip = Heading_type.PARAMETERS_PLAYER; c SERVER
//                stockMessOut.p1 = aboutPlayer; // ХП
//                stockMessOut.p2 = Heading_type.RED_COMMAND;// КОМАНДА
//                stockMessOut.p3 = HP; // номер игрока
//                stockMessOut.p4 = HP; // номер игрока
//                stockMessOut.textM = nikName; // ник нейм
                int id = mainGame.getMainClient().getClient().getID();
                if (id == (int) sm.p1) {
                    saveParametrsMtTank(sm);
                } else {
                    OpponentsTanks opponentsTanks = mainGame.getGamePlayScreen().getTanksOther().getTankForID((int) sm.p1);
                    //    System.out.println(opponentsTanks);
                    // opponentsTanks = new OpponentsTanks();
                    opponentsTanks.hp = (int) sm.p3;
                    opponentsTanks.command = (int) sm.p2;
                    opponentsTanks.setNikPlayer(sm.textM);
                    if (!opponentsTanks.isLive()) {
                        if (mainGame.getGamePlayScreen().getTimeInGame() < 1) return;

                        mainGame.getGamePlayScreen().getPc().addAnimationDeath(opponentsTanks.getPosition().x, opponentsTanks.getPosition().y);




                    }

                }

            } catch (NullPointerException e) {
                 e.printStackTrace();
              //  System.out.println("1");
//                Tank myTank = mainGame.getGamePlayScreen().getTank();
//                myTank.setHp((int) sm.p3);
//                myTank.setCommand((int) sm.p2);
                // System.out.println(sm.p1);
            }
            return;
        }


        if (Heading_type.DISCONECT_PLAYER == sm.tip) {
            OpponentsTanks opponentsTanks = mainGame.getGamePlayScreen().getTanksOther().getTankForID((int) sm.p3);
//            opponentsTanks.hp = (int) sm.p1;

            // mainGame.getGamePlayScreen().playAnimation(position, velocity, (int) sm.p4);
            return;
        }

        //Heading_type.SHELL_RUPTURE


    }

    private void saveParametrsMtTank(Network.StockMessOut sm) {
        mainGame.getGamePlayScreen().getTank().setHp((int) sm.p3);
        mainGame.getGamePlayScreen().getTank().setCommand(Heading_type.RED_COMMAND);
        if (!mainGame.getGamePlayScreen().getTank().isLive())
            mainGame.getGamePlayScreen().getPc().addAnimationDeath(mainGame.getGamePlayScreen().getTank().getPosition().x, mainGame.getGamePlayScreen().getTank().getPosition().y);
    }
}
