package com.mygdx.tanks2d.AudioEngine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.tanks2d.Screens.GamePlayScreen;

import java.util.concurrent.ConcurrentHashMap;


public class AudioEngine {
    GamePlayScreen mainGaming;
    ConcurrentHashMap<Integer, Float> stepCounter;
    Sound sound;
    Sound track;
    Sound tower;
    Sound explosion;

    private Long idTrack;
    private Long idTower;
    private float timer_towr_ratation;
    private boolean timerTower;

    private static Vector2 tempV = new Vector2(0, 0);

    public AudioEngine(GamePlayScreen mainGaming) {
        timer_towr_ratation = 0;
        this.mainGaming = mainGaming;
        timerTower = false;

//        Sound sound = Gdx.audio.newSound(Gdx.files.internal("sound/BSB.ogg"));
//        Sound track = Gdx.audio.newSound(Gdx.files.internal("sound/00708.ogg"));
//        Sound tower = Gdx.audio.newSound(Gdx.files.internal("sound/bash.ogg"));

        sound = mainGaming.getAssetsManagerGame().get("sound/BSB.ogg", Sound.class);
        track = mainGaming.getAssetsManagerGame().get("sound/00708.ogg", Sound.class);
        tower = mainGaming.getAssetsManagerGame().get("sound/bash.ogg", Sound.class);
        explosion = mainGaming.getAssetsManagerGame().get("sound/explode4.ogg", Sound.class);

    }

    public void pleySoundKickStick() {
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

    private static float countVolmeDistantion(Vector2 a,Vector2 b) {
       return countVolmeDistantion(a.x,a.y,b.x,b.y);
    }


    public void pleySoundKickExplosion(float x, float y, float x1, float y1) {
        float distanc = countVolmeDistantion(x, y, x1, y1);
        if (distanc <= 0) return;
        long id = explosion.play();
        explosion.setPitch(id, MathUtils.random(.95f, 1.1f));
        explosion.setVolume(id, distanc);
    }

    public void pleySoundKickStick(float x, float y, float x1, float y1) {

        float distanc = countVolmeDistantion(x, y, x1, y1);
        if (distanc <= 0) return;
        long id = sound.play();
        sound.setPitch(id, MathUtils.random(.95f, 1.1f));
        sound.setVolume(id, distanc);
    }

    public void pleySoundKickStick(float vol) {
        if (vol < 0) return;
        float distanc = 1;
        long id = sound.play();
        sound.setPitch(id, MathUtils.random(.95f, 1.1f));
        sound.setVolume(id, distanc);
    }

    public void pleySoundOfTracks() {
        //  System.out.println(idTrack);

        if (idTrack == null) {
            idTrack = track.play();
            sound.setLooping(idTrack, true);
        } else sound.resume(idTrack);


    }

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

}
