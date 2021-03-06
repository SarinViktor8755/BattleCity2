package main.java.com.MatchOrganization;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.tanks2d.ClientNetWork.Heading_type;

import java.util.Iterator;
import java.util.Map;

import main.java.com.MainGame;
import main.java.com.Units.ListPlayer.ListPlayers;
import main.java.com.Units.ListPlayer.Player;

public class IndexMath {
    private static final float MATH_LENGHT = 1000 * 60 * 2; // время матча
    private static float realTimeMath; // время матча
    private ListPlayers listPlayers; // копиялиста

    private static int red_team_score;
    private static int blue_team_score;



    public void updateMath(float dt,ListPlayers listPlayers) {
        this.realTimeMath += dt;
        this.listPlayers = listPlayers;
        this.restartMath(this.realTimeMath);
    }

    private boolean is_end_math() {
        if (realTimeMath > MATH_LENGHT) return true;
        else return false;
    }


    public int getCommand() { // определить команду
             if (listPlayers.blue_players_size() > listPlayers.red_players_size()) {
                return Heading_type.RED_COMMAND;
            } else if (listPlayers.blue_players_size() < listPlayers.red_players_size()) {
                return Heading_type.BLUE_COMMAND;
            } else if (MathUtils.randomBoolean()) return Heading_type.RED_COMMAND;
            else return Heading_type.BLUE_COMMAND;
    }
//////////////////////////////////////////////////////
    public static int getRed_team_score() {
        return red_team_score;
    }

    public static void setRed_team_score(int red_team_score) {
        IndexMath.red_team_score = red_team_score;
    }

    public static int getBlue_team_score() {
        return blue_team_score;
    }

    public static void setBlue_team_score(int blue_team_score) {
        IndexMath.blue_team_score = blue_team_score;
    }

    public static void add_score_blue_team(){
        blue_team_score++;
    }

    public static void add_score_red_team(){
        red_team_score++;
    }

    public static void  add_score_team(int team){
        if(team == Heading_type.BLUE_COMMAND)add_score_blue_team();
        if(team == Heading_type.RED_COMMAND)add_score_red_team();
       // System.out.println("RED  " + red_team_score + "  BLUE  " + blue_team_score);
    }

    private void restartMath(float mathTime){
//        System.out.println(ListPlayers.getRed_average().x == null);
//        System.out.println(ListPlayers.getBlue_average().equals(null));
//        System.out.println("00000000000000");
        if(mathTime < 3000) return;


                if((listPlayers.getLive_blue_size()==0)||(listPlayers.getLive_red_size()==0)){
                    listPlayers.respownAllPlaers();
                    realTimeMath = 0;
                    System.out.println("RESTART MATH");
                }
    }



    public static float getRealTimeMath() {
        return realTimeMath;
    }

    ///////////////////

}
