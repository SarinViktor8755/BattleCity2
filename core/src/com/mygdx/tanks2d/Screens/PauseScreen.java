package com.mygdx.tanks2d.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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
    private Label puseTextLabel;
    BitmapFont font;

    public PauseScreen(MainGame mainGame) {
        System.out.println("PAUSE ");
        this.batch = new SpriteBatch();
        this.mainGame = mainGame;

        viewport = new StretchViewport(MainGame.WHIDE_SCREEN, MainGame.HIDE_SCREEN, camera);
        //viewport.apply();
        camera = new OrthographicCamera();
        this.mainClient = mainGame.getMainClient();
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

        mainGame.goGameForPause();
        //if(MathUtils.randomBoolean(.005f)) MainGame.setFlagChangeScreen((byte) MainGame.STATUS_GAME_GAMEPLAY);
        Gdx.gl.glClearColor(MathUtils.random(.3f,.1f), MathUtils.random(.8f,1f), 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


//            batch.begin();
//
//            font.draw(batch, "Hello World!", 250, 250);
//            batch.end();

      //  puseTextLabel.draw(this.batch,1);

        System.out.println("pause");


    }

    @Override
    public void resize(int width, int height) {

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
