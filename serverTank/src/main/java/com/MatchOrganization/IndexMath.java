package main.java.com.MatchOrganization;

import main.java.com.MainGame;

public class IndexMath {
    private static final float MATH_LENGHT = 1000 * 60 * 2; // время матча
    private static float realTimeMath; // время матча





    public void updateMath(float dt){
        this.realTimeMath+=dt;
        System.out.println(realTimeMath);
    }

    private boolean is_end_math() {
        if (realTimeMath > MATH_LENGHT) return true;
        else return false;
    }

}
