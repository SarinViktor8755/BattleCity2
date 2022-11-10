//package com.mygdx.tanks2d.Locations;
//
//
//import com.badlogic.gdx.Gdx;
//
//import com.badlogic.gdx.graphics.Color;
//import com.badlogic.gdx.graphics.OrthographicCamera;
//import com.badlogic.gdx.math.MathUtils;
//
//import com.badlogic.gdx.math.Vector2;
//import com.badlogic.gdx.physics.box2d.World;
//import com.mygdx.tanks2d.MainGame;
//
//import java.util.ArrayList;
//
//import box2dLight.ConeLight;
//import box2dLight.PointLight;
//import box2dLight.RayHandler;
//
//
//public class LightingBox2D {
//    private World world;
//
//    private PointLight pointLightHero;
//   // private ObFromLight object;
//    private RayHandler rayHandlerHero;
//
//    private ArrayList<PointLight> pointLightsList = new ArrayList<PointLight>();
//
//    private ConeLight coneLightHero;
//    private ConeLight coneLightTower;
//
//    private BuletFlash buletFlash;
//    private boolean lasetOn;
//
//    private float laserLith;
//
//    public LightingBox2D(MainGame mg) {
//        lasetOn = true;
//
//        this.world = new World(new Vector2(0, 0), true);
//        pointLightsList = new ArrayList<>();
//        RayHandler.useDiffuseLight(true);
//        this.rayHandlerHero = new RayHandler(this.world);
////        object = new ObFromLight(this.world); // припятсвия
////        object.crearBodys(mg.getIndexMap().getTopQualityMap_Box());
//        PointLight pl;
//
//
//        // поле
//        for (int i = 0; i < 5000; i += 500) {
//            for (int j = 0; j < 5000; j += 500) {
//                pl = new PointLight(rayHandlerHero, 10, getColorFromPoint(), 1300, j, i);
//                pl.setIgnoreAttachedBody(false);
//                pointLightsList.add(pl);
//            }
//        }
//        coneLightTower = new ConeLight(rayHandlerHero, 3, Color.RED, 1800, 0, 0, 45, 4); // лазер
//        pointLightHero = new PointLight(rayHandlerHero, 4, Color.ROYAL, 600, 0, 0); /// свитильник героя
//        coneLightHero = new ConeLight(rayHandlerHero, 65, Color.ROYAL, 1500, 0, 0, 90, 60);
////
//
////        buletFlash = new BuletFlash(rayHandlerHero);
//
//        //на машинах
////        for (Body cars : object.getBodyList()) {
////            pl = new PointLight(rayHandlerHero, 4, getColorFromPoint(), 800, cars.getPosition().x, cars.getPosition().y);
////            pl.attachToBody(cars);
////        }
//
//        rayHandlerHero.setAmbientLight(.5f);
//
//        // rayHandlerHero.setShadows();
//    }
//
//
//    public void setConeTower(float x, float y, float align) {
//        this.coneLightTower.setPosition(x, y);
//        this.coneLightTower.setDirection(align);
//    }
//
//    Color getColorFromPoint() {
//        Color colorPoint;
//        if (MathUtils.randomBoolean(.1f)) colorPoint = Color.CHARTREUSE;
//        else if ((MathUtils.randomBoolean(.1f))) colorPoint = Color.RED;
//        else if ((MathUtils.randomBoolean(.1f))) colorPoint = Color.NAVY;
//        else if ((MathUtils.randomBoolean(.1f))) colorPoint = Color.BLUE;
//        else if ((MathUtils.randomBoolean(.2f))) colorPoint = Color.OLIVE;
//        else if ((MathUtils.randomBoolean(.3f))) colorPoint = Color.YELLOW;
//        else colorPoint = Color.BROWN;
//        return colorPoint;
//    }
//
//
//    public void upDateLights(float xHero, float yHero, float align) {
//        world.step(1 / 30f, 1, 1);
//        coneLightHero.setPosition(xHero, yHero);
//        pointLightHero.setPosition(xHero, yHero);
//        coneLightHero.setDirection(align);
//        buletFlash.upDate();
//
//
//        /////////////////////
//        laserLith = MathUtils.clamp(laserLith, 300, 1800);
//        coneLightTower.setDistance(laserLith);
//        if (lasetOn) laserLith = laserLith + Gdx.graphics.getDeltaTime() * 550;
//        else laserLith = laserLith - Gdx.graphics.getDeltaTime() * 550;
//        coneLightTower.setDistance(laserLith);
//
//    }
//
//    public void renderLights(OrthographicCamera camera) {
//        rayHandlerHero.setCombinedMatrix(camera);
//        rayHandlerHero.updateAndRender();
//    }
//
//    public boolean isLasetOn() {
//        return lasetOn;
//    }
//
//    public void setLasetOn(boolean lasetOn) {
//        this.lasetOn = lasetOn;
//    }
//
//    public boolean isAtShadow(float x, float y) {
//        return rayHandlerHero.pointAtShadow(x, y);
//    }
//
////   // public Texture getTexture() {
////        return rayHandlerHero.getLightMapTexture();
////    }
//
//
////    public void startBulletFlash(float x, float y) {
////        buletFlash.newFlesh(x, y);
////    }
//
//    public World getWorld() {
//        return world;
//    };
//}
//
