package main.java.com.Units.ListPlayer;

import com.mygdx.tanks2d.ClientNetWork.Heading_type;

import java.util.Iterator;
import java.util.Map;

public class StatisticMath {   // класс дял подчета количетва играков )))))


    private ListPlayers lp;

    private int size_live_player; // оличество живых играков
    private int size_bot_player; // количество ботов

    private int blue_size; // всех в команде
    private int red_size;


    // разница команд в коичестве
    private int team_difference;

    private int live_blue_size;
    private int live_red_size;

    private boolean access_key;


    public StatisticMath(ListPlayers lp) {
        this.lp = lp;
        access_key = true; // ключ домтупа
    }

//    public int[] getArrayStatic(){
//        return int
//    }

    public synchronized StatisticMath counting_p() { // посчитать статичтику
        if(!access_key) return null;
        access_key = false;

        size_live_player = 0;
        size_bot_player = 0;
        blue_size = 0;
        red_size = 0;

        Iterator<Map.Entry<Integer, Player>> entries = lp.getPlayers().entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<Integer, Player> entry = entries.next();
            Player p = entry.getValue();
            lp.checking_empty_players(p); // ???
            if(!isBot(p)){
                if (!p.in_game_player()) continue;
                update_number_of_clicks(p.getCommand(), p.isLive()); // добавить в команду
                if(p.getPosi().x != -10_000) size_live_player++; // количество жиых играков ___ реальных играков
            }else{
                // if(p.getNikName().equals("tokken_123")) this.players.remove(p);
                update_number_of_clicks(p.getCommand(), p.isLive()); // добавить в команду
                size_bot_player++;// количество БОТОВ играков ___ НЕ РЕАЛЬНЫХ играков
            }
        }
        access_key = true;
        return this;
    }


    private void update_number_of_clicks(int coomand, boolean live) {
        if (coomand == Heading_type.BLUE_COMMAND) {
            blue_size++;
            //    if (live) live_blue_size_player++;
        }


        if (coomand == Heading_type.RED_COMMAND) {
            red_size++;
            //        if (live) live_red_size_player++;
        }
    }


    private boolean isBot(Player p) {
        if (p.id > -99) return false;
        else return true;
    }


    ///////////
    public int getSize_live_player() {
        return size_live_player;
    }

    public void setSize_live_player(int size_live_player) {
        this.size_live_player = size_live_player;
    }

    public int getSize_bot_player() {
        return size_bot_player;
    }

    public void setSize_bot_player(int size_bot_player) {
        this.size_bot_player = size_bot_player;
    }

    public int getTeam_difference() {
        return team_difference;
    }

    public void setTeam_difference(int team_difference) {
        this.team_difference = team_difference;
    }

    public int getLive_blue_size() {
        return live_blue_size;
    }

    public void setLive_blue_size(int live_blue_size) {
        this.live_blue_size = live_blue_size;
    }

    public int getLive_red_size() {
        return live_red_size;
    }

    public void setLive_red_size(int live_red_size) {
        this.live_red_size = live_red_size;
    }

    public boolean isAccess_key() {
        return access_key;
    }
}
