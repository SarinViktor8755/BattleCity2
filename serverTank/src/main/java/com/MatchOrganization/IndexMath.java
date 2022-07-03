package main.java.com.MatchOrganization;

import main.java.com.MainGame;
import main.java.com.Units.ListPlayer.ListPlayers;

public class IndexMath {
    private static final float MATH_LENGHT = 1000 * 60 * 2; // время матча
    private static float realTimeMath; // время матча
    private ListPlayers listPlayers;



    public void updateMath(float dt){
        this.realTimeMath+=dt;
        System.out.println(realTimeMath);
    }

    private boolean is_end_math() {
        if (realTimeMath > MATH_LENGHT) return true;
        else return false;
    }



}
