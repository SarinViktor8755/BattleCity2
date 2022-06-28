package com.mygdx.tanks2d.Locations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;

import box2dLight.RayHandler;


public class BuletFlash {
    private float timerLife;
    private float x, y;
    private PointLight bullet;
    int distancia;
    final float timerLifeConst = .28f;


    public BuletFlash(RayHandler rh) {
        this.distancia = 0; // дистанция вспышки
        this.timerLife = 0;
        this.x = 0;
        this.y = 0;
//        bullet = new PointLight(rh, 18, Color.YELLOW, 1, 0, 0); /// свитильник героя
//        bullet.getColor().set(0, 0, 0, -.01f);
//        bullet.setActive(false);
    }

    public void upDate() {
//        if (this.timerLife > 0) {
//            this.timerLife -= Gdx.graphics.getDeltaTime();
//            bullet.setPosition(x, y);
//            bullet.setDistance(150 * Interpolation.swing.apply(MathUtils.map(
//                    0, timerLifeConst,
//                    0, 1,
//                    timerLife
//            )));
//        } else {
//            bullet.setActive(false);
//        }
    }
//
//    public void newFlesh(float x, float y) {
//        this.timerLife = timerLifeConst;
//        this.bullet.setActive(true);
//        this.x = x;
//        this.y = y;
//        //     System.out.println(this);
//    }
//
//    @Override
//    public String toString() {
//        return "BuletFlash{" +
//                "timerLife=" + timerLife +
//                ", x=" + x +
//                ", y=" + y +
//                ", bullet=" + bullet +
//                '}';
//    }
//
//    public boolean isLife() {
//        if (timerLife < 0) return false;
//        else
//            return true;
//    }
}
