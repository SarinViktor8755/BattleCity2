package main.java.com.MatchOrganization;

import com.badlogic.gdx.math.MathUtils;
import com.mygdx.tanks2d.ClientNetWork.Heading_type;

import main.java.com.MainGame;
import main.java.com.Units.ListPlayer.ListPlayers;

public class IndexMath {
    private static final float MATH_LENGHT = 1000 * 60 * 2; // время матча
    private static float realTimeMath; // время матча
    private ListPlayers listPlayers;


    public void updateMath(float dt,ListPlayers listPlayers) {
        this.realTimeMath += dt;
      //  System.out.println(realTimeMath);
        this.listPlayers = listPlayers;
    }

    private boolean is_end_math() {
        if (realTimeMath > MATH_LENGHT) return true;
        else return false;
    }

    public int getCommand() {
             if (listPlayers.blue_players_size() > listPlayers.red_players_size()) {
                return Heading_type.RED_COMMAND;
            } else if (listPlayers.blue_players_size() < listPlayers.red_players_size()) {
                return Heading_type.BLUE_COMMAND;
            } else if (MathUtils.randomBoolean()) return Heading_type.RED_COMMAND;
            else return Heading_type.BLUE_COMMAND;



    }

}
