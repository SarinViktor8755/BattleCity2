package com.mygdx.tanks2d.Screens.Controll;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;

public class Banner { // банер на гланом экране
    private float timeLife;
    private static final float defoult_time_life = 1;
    private ArrayList<Integer> q = new ArrayList<>();

    public Banner() {
        this.timeLife = 0;
    }

    public void update() {
        System.out.println(timeLife);
        if (MathUtils.randomBoolean(.005f)) {
            addBaner(MathUtils.random(500));
        }
        System.out.println(q);

        ////////////////////////////////////
        if (!isWorking()) {
            timeLife = defoult_time_life;
            return;
        }
        timeLife -= Gdx.graphics.getDeltaTime();
        if (timeLife < 0) {
            delBanner();
            timeLife = defoult_time_life;
        }


        if (isWorking()) rander();
    }

    private void delBanner() {
        if (isWorking()) q.remove(0);
    }


    private void rander() {

    }

    public void addBaner(int bn) {
        q.add(bn);
    }

    private boolean isWorking() {
        if (q.size() > 0) return true;
        else return false;
    }
}


