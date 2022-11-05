package com.mygdx.tanks2d.AudioEngine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.tanks2d.MainGame;
import com.mygdx.tanks2d.Screens.GamePlayScreen;

import java.util.concurrent.ConcurrentHashMap;


public class AudioEngine {
    MainGame mainGaming;
    ConcurrentHashMap<Integer, Float> stepCounter;
    Sound sound;
    Sound track;
    Sound tower;
    Sound explosion;

    Sound fight, loose;
    Sound music_pause;

    Sound redy_for_action;


    private Long idTrack;
    private Long idTower;
    private float timer_towr_ratation;
    private boolean timerTower;
    private static long pause_music_id;

    private static Vector2 tempV = new Vector2(0, 0);

    public AudioEngine(MainGame mainGaming) {
        timer_towr_ratation = 0;
        this.mainGaming = mainGaming;
        timerTower = false;

        redy_for_action = mainGaming.getAMG().get("sound/READY2A.ogg", Sound.class);
        sound = mainGaming.getAMG().get("sound/BSB.ogg", Sound.class);
        track = mainGaming.getAMG().get("sound/00708.ogg", Sound.class);
        tower = mainGaming.getAMG().get("sound/bash.ogg", Sound.class);
        fight = mainGaming.getAMG().get("sound/f.ogg", Sound.class);
        loose = mainGaming.getAMG().get("sound/loose.ogg", Sound.class);

        music_pause = mainGaming.getAMG().get("pause_screen/pm.ogg", Sound.class);

        explosion = mainGaming.getAMG().get("sound/explode4.ogg", Sound.class);


    }

//    public AudioEngine(MainGame mainGame) {
//        timer_towr_ratation = 0;
//        this.mainGaming = mainGaming;
//        timerTower = false;
//        sound = mainGaming.getAssetsManagerGame().get("sound/BSB.ogg", Sound.class);
//        track = mainGaming.getAssetsManagerGame().get("sound/00708.ogg", Sound.class);
//        tower = mainGaming.getAssetsManagerGame().get("sound/bash.ogg", Sound.class);
//        fight = mainGaming.getAssetsManagerGame().get("sound/f.ogg", Sound.class);
//        loose = mainGaming.getAssetsManagerGame().get("sound/loose.ogg", Sound.class);
//
//        music_pause = mainGaming.getAssetsManagerGame().get("pause_screen/pm.ogg", Sound.class);
//
//        explosion = mainGaming.getAssetsManagerGame().get("sound/explode4.ogg", Sound.class);
//
//
//    }

    public void pleySoundKickStick() {
        stopSoundOfTracks();
        float distanc = 1;
        long id = sound.play();
        sound.setPitch(id, MathUtils.random(.95f, 1.1f));
        sound.setVolume(id, distanc);
    }

    private static float countVolmeDistantion(float x, float y, float x1, float y1) {
        tempV.set(x, y);
        float distanc = tempV.dst2(x1, y1);
        distanc = MathUtils.map(0, 250_000, 1, 0, distanc);
        return distanc;
    }

    public void pley_fight_ad_sound() {
        if(isPause()) return;
        this.fight.play();
    }

    public void pley_lose_ad_sound() {
        if(isPause()) return;
        this.loose.play();
    }

    public void rady_for_action() {
        this.redy_for_action.play();
    }


    private static float countVolmeDistantion(Vector2 a, Vector2 b) {
        return countVolmeDistantion(a.x, a.y, b.x, b.y);
    }


    public void pleySoundKickExplosion(float x, float y, float x1, float y1) {
        if(isPause()) return;
        float distanc = countVolmeDistantion(x, y, x1, y1);
        if (distanc <= 0) return;
        long id = explosion.play();
        explosion.setPitch(id, MathUtils.random(.95f, 1.1f));
        explosion.setVolume(id, distanc);
    }

    public void pleySoundKickStick(float x, float y, float x1, float y1) {
        if(isPause()) return;

        float distanc = countVolmeDistantion(x, y, x1, y1);
        if (distanc <= 0) return;
        long id = sound.play();
        sound.setPitch(id, MathUtils.random(.95f, 1.1f));
        sound.setVolume(id, distanc);
    }

    public void pleySoundKickStick(float vol) {
        if(isPause()) return;
        if (vol < 0) return;
        float distanc = 1;
        long id = sound.play();
        sound.setPitch(id, MathUtils.random(.95f, 1.1f));
        sound.setVolume(id, distanc);
    }

    public void pleySoundOfTracks() {
        if(isPause()) return;
        //  System.out.println(idTrack);

        if (idTrack == null) {
            idTrack = track.play();
            sound.setLooping(idTrack, true);
        } else sound.resume(idTrack);


    }

    ///////////////
    public void playMusicPaseMenu() {
        pause_music_id = music_pause.loop(1);
        stopSoundOfTracks();
        // pause_music_id = music_pause.play();

    }

    public void stopMusicPaseMenu() {
        // music_pause.stop(pause_music_id);
        music_pause.stop();
    }

    public void update_volme_pause(float vol) {
        music_pause.setVolume(pause_music_id, vol);
    }

    ////////
    public void stopSoundOfTracks() {
        if (idTrack != null)
            sound.pause(idTrack);
    }


    public void pleySoundOfTower() {
        //   System.out.println(idTrack);
//        timerTower = true;
        timer_towr_ratation = 0;
        if (idTower == null) {
            idTower = tower.play();
            sound.setLooping(idTower, true);
        } else sound.resume(idTower);
        timer_towr_ratation += Gdx.graphics.getDeltaTime();
    }

    public void stopSoundOfTower() {
        // System.out.println(timer_towr_ratation);
        try {
            timer_towr_ratation += Gdx.graphics.getDeltaTime();
            if (timer_towr_ratation > .25f) {
                sound.pause(idTower);
                timer_towr_ratation = 0;
                if (idTower != null) {
                    sound.pause(idTower);
                }
            }
        } catch (NullPointerException e) {
            //   System.out.println("11");
        }
    }


    public boolean isPause() {
        if (mainGaming.isPause()) return true;
        return false;
    }

}
