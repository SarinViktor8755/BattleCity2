package com.mygdx.tanks2d.Screens.PauseScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.tanks2d.ClientNetWork.MainClient;
import com.mygdx.tanks2d.MainGame;

public class PauseScreen implements Screen {
    MainGame mainGame;

    private SpriteBatch batch;

    private StretchViewport viewport;
    private OrthographicCamera camera;
    private MainClient mainClient;

    /////////////////////////////////////
    private float timeInScreen;
    Texture f;


    public PauseScreen(MainGame mainGame) {
        System.out.println("PAUSE ");
        this.batch = new SpriteBatch();
        this.mainGame = mainGame;

        viewport = new StretchViewport(MainGame.WHIDE_SCREEN, MainGame.HIDE_SCREEN, camera);
        //viewport.apply();
        camera = new OrthographicCamera();
        viewport = new StretchViewport(MainGame.WHIDE_SCREEN/2, MainGame.HIDE_SCREEN/2, camera);
        viewport.apply();
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();



        this.mainClient = mainGame.getMainClient();

        f =  mainGame.assetManager.get("pause_screen/bg.png", Texture.class);

        timeInScreen = 15;
//
//        BitmapFont font = mainGame.getAssetManager().get("fonts/font.fnt", BitmapFont.class);
//        Label.LabelStyle style = new Label.LabelStyle(font, Color.WHITE);
//        puseTextLabel = new Label("PAUSE_GAME",style);
//        font = new BitmapFont(); //or use alex answer to use custom font
        ///////////////////////////////

    }



    @Override
    public void show() {


    }

    @Override
    public void render(float delta) {
        update();
        mainGame.goGameForPause();
        //if(MathUtils.randomBoolean(.005f)) MainGame.setFlagChangeScreen((byte) MainGame.STATUS_GAME_GAMEPLAY);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            batch.begin();

            batch.setColor(1,1,1,getAlpha());
            batch.draw(f, viewport.getScreenX(), viewport.getScreenY(),Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

            batch.end();



        System.out.println(timeInScreen);


    }

    private void update(){
        this.timeInScreen -= Gdx.graphics.getDeltaTime();
    }

    private float getAlpha(){
        float result = 1;
        if(timeInScreen> 13){
            result = Interpolation.exp10Out.apply(14 - timeInScreen);
        }else if(timeInScreen <1.3f){
            result = Interpolation.exp10Out.apply(timeInScreen -1);
        }

        return result;

    }

    @Override
    public void resize(int width, int height) {
        this.viewport.update(width, height, true);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();

    }
}
