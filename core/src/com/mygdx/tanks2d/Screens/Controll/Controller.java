package com.mygdx.tanks2d.Screens.Controll;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.tanks2d.MainGame;
import com.mygdx.tanks2d.Screens.GamePlayScreen;


/**
 * Created by brentaureli on 10/23/15.
 */
public class Controller {
   // Skin skinGame;

    final private Viewport viewport;
    final private Stage stage;

    OrthographicCamera cam;

    private boolean inTuchMove;
    private boolean attackButon;
    private boolean chance;

    final private Image pointStick;
    final private Vector2 distance;
    private Vector2 temp_Point;
    private Image changingGoal;


    private Label labelHP;

    private Label score_red;
    private Label score_blue;

    private BitmapFont font;

    private GamePlayScreen gamePlayScreen;

    private boolean buttonChangingOpponent;


    Vector2 directionMovement; // Направление движения

    public boolean isInTuchMove() {
        return inTuchMove;
    }

    public Controller(GamePlayScreen gsp) {
       // gsp.getAssetManager().get("de.pack", TextureAtlas.class);



        distance = new Vector2();
        inTuchMove = false;
        attackButon = false;
        chance = false;
        this.directionMovement = new Vector2(0, 0);
        cam = new OrthographicCamera();
        viewport = new FillViewport(MainGame.WHIDE_SCREEN, MainGame.HIDE_SCREEN, cam);
        stage = new Stage(viewport, gsp.getBatch());
        Gdx.input.setInputProcessor(stage);
        temp_Point = new Vector2(0, 0);
        buttonChangingOpponent = false;

        font = gsp.getAssetsManagerGame().get("fonts/font.fnt", BitmapFont.class);
        Label.LabelStyle style = new Label.LabelStyle(font, Color.WHITE);
        font.getData().setScale(.8f);
        font.getColor().set(.5f, .5f, .5f, 1);


        final float sw = MainGame.WHIDE_SCREEN;
        final float sh = MainGame.HIDE_SCREEN;

/////////////////
        final Image stick = new Image(gsp.getAssetsManagerGame().get("button.pack", TextureAtlas.class).findRegion("b"));
        pointStick = new Image(gsp.getAssetsManagerGame().get("button.pack", TextureAtlas.class).findRegion("stick"));
////////////////
       // System.out.println(pointStick.getImageHeight()+ "  ==== ___ ");

        pointStick.setSize(90, 90);

        stick.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                inTuchMove = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                inTuchMove = false;
                resetPoint(pointStick);

            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                temp_Point.set(stick.getImageX() + stick.getImageWidth() / 2, stick.getImageY() + stick.getImageHeight() / 2);
                temp_Point.sub(x, y).scl(-1).clamp(0, 35);
                //System.out.println(temp_Point);
                directionMovement.set(temp_Point);

                pointStick.setPosition(temp_Point.x, temp_Point.y);


            }
        });


        final Image attacButton = new Image(gsp.getAssetsManagerGame().get("button.pack", TextureAtlas.class).findRegion("ba"));
        attacButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                attackButon = true;
                return true;
            }


            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                attackButon = true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                attackButon = false;
            }
        });
////////////////////////////////////////// changingGoal
        changingGoal = new Image(gsp.getAssetsManagerGame().get("button.pack", TextureAtlas.class).findRegion("br"));
        changingGoal.setSize(90, 90);
        changingGoal.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                chance = true;
                // System.out.println("changingGoal");
                return false;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                chance = false;

            }
        });

        stick.setSize(90, 90);
        stick.setPosition(0, 0);

        attacButton.setSize(55, 55);
        attacButton.setPosition(sw - 100, 40);

        changingGoal.setSize(55, 55);
        changingGoal.setPosition(sw - 100, 105);


        Group gropuButton = new Group();
        Group gropuStick = new Group();

        gropuStick.setPosition(35, 35);

        gropuStick.addActor(stick);
        gropuStick.addActor(pointStick);


        gropuButton.addActor(gropuStick);
        gropuButton.addActor(attacButton);
        gropuButton.addActor(changingGoal);
///////////////////
       // skinGame = gsp.getMainGame().assetManager.get("skin/metal-ui.json", Skin.class);
        labelHP = new Label("HP:",style);
        labelHP.setX(30);
        labelHP.setY(sh - 40);
//        labelHP.setColor(Color.FIREBRICK);
//
//        labelHP.setFontScale(2);

        stage.addActor(labelHP);
//////////////////////////////////
        //   stage.setDebugAll(true);
//        if(!MainGame.ANDROID)
        stage.addActor(gropuButton);

        resetPoint(stick);
        pointStick.setTouchable(Touchable.disabled);

        pointStick.setColor(1, 1, 1, .3f);

        stick.setColor(1, 1, 1, .3f);
        pointStick.setColor(1, 1, 1, .7f);

        attacButton.setColor(1, 1, 1, .3f);
        changingGoal.setColor(1, 1, 1, .3f);


    }

    public boolean isButtonChangingOpponent() {
        return buttonChangingOpponent;
    }

    public boolean isChance() {
        return chance;
    }

    public void setChance(boolean chance) {
        this.chance = chance;
    }

    private void resetPoint(Image stick) {
        stick.setPosition(
                stick.getImageX(),
                stick.getImageY()
        );

    }

    public void draw() {
        this.update();
        stage.draw();
        //    System.out.println(this.inTuchMove);
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    public Vector2 getDirectionMovement() {
        return directionMovement;
    }

    public boolean isAttackButon() {
        if (attackButon) {
            attackButon = false;
            return true;
        }
        return attackButon;
    }

    private void update() {
        //System.out.println(buttonChangingOpponent);
        if (buttonChangingOpponent) changingGoal.setColor(1, 1, 1, .3f);
        else changingGoal.setColor(1,0,0,.1f);
    }

    public void setButtonChangingOpponent(boolean buttonChangingOpponent) {
        this.buttonChangingOpponent = buttonChangingOpponent;
    }

    public void setHPHeroTank(int hp){
        this.labelHP.setText("HP: " + hp);
        if (hp < 30) labelHP.setColor(Color.FIREBRICK); else labelHP.setColor(Color.WHITE);
    }

    public void setBlueCommand(int score){
        this.labelHP.setText(score);
        //if (hp < 30) labelHP.setColor(Color.FIREBRICK); else labelHP.setColor(Color.WHITE);
    }

    public void setRedCommand(int score){
        this.labelHP.setText(score);
       // if (hp < 30) labelHP.setColor(Color.FIREBRICK); else labelHP.setColor(Color.WHITE);
    }


}
