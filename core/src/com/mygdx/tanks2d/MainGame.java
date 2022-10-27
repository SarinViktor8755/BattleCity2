package com.mygdx.tanks2d;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.mygdx.tanks2d.Assets.AssetsManagerGame;
import com.mygdx.tanks2d.ClientNetWork.MainClient;
import com.mygdx.tanks2d.Screens.GamePlayScreen;
import com.mygdx.tanks2d.Screens.MenuScreen;
import com.mygdx.tanks2d.Screens.PauseScreen.PauseScreen;
import com.mygdx.tanks2d.adMod.AdAds;


public class MainGame extends Game {

    public AssetManager assetManager;
    public AssetsManagerGame assetsManagerGame;
    private MainClient mainClient;
    private GamePlayScreen gamePlayScreen;
    private PauseScreen pauseScreen;

    private Screen mainMenu;

    private static byte flagChangeScreen = 0; // фоаг смены экрана - 0 не менять далее по показателям

    public static final int STATUS_GAME_MENU = 1;
    public static final int STATUS_GAME_GAMEPLAY = 2;
    public static final int STATUS_GAME_PAUSE = 3;


    public static String nik_name;

    //public AssetsManagerGame assetsManagerGame;

    static public boolean ANDROID;      // андроид
    private AdAds adMod;                // реклама
    public String tokken;

    static public final int WHIDE_SCREEN = 555;
    static public final int HIDE_SCREEN = 315;




    static public int status = STATUS_GAME_MENU;

    public MainGame(int tip) {
        mainClient = new MainClient(this);
        assetManager = new AssetManager();
        assetsManagerGame = new AssetsManagerGame(assetManager);




        if (tip == 1) ANDROID = false;
        else ANDROID = true;


    }


    @Override
    public void create() {
        // tokken = NikName.getTokken();
        assetsManagerGame.loadAllAssetMenu();
        mainMenu = new MenuScreen(this);
        this.setScreen(mainMenu);
    }

    public void startGameMPley() {
        mainMenu.dispose();
        // getMainClient().setOnLine(true);
        assetsManagerGame.loadAllAsseGame();
        this.gamePlayScreen = new GamePlayScreen(this);
        this.setScreen(this.gamePlayScreen);
    }

    public void startGameSPley() {
        mainMenu.dispose();
        //  getMainClient().setOnLine(false);
        assetsManagerGame.loadAllAsseGame();
        this.gamePlayScreen = new GamePlayScreen(this);
        this.setScreen(this.gamePlayScreen);
        MainGame.status = STATUS_GAME_GAMEPLAY;
        //     mainClient.getClient().dispose();
    }

    public void startPauseScreen() {
        if(MainGame.flagChangeScreen != MainGame.STATUS_GAME_PAUSE) return;
        MainGame.flagChangeScreen = 0;
        this.screen.dispose();
      //  assetsManagerGame.loadAllAsseGame();
       // this.setScreen(null);
        this.pauseScreen = new PauseScreen(this);
        this.setScreen(pauseScreen);
        MainGame.status = STATUS_GAME_PAUSE;

    }

    public void goGameForPause() { // выход из паузы в игру
        if(MainGame.flagChangeScreen != MainGame.STATUS_GAME_GAMEPLAY) return;
        MainGame.flagChangeScreen = 0;
        this.screen.dispose();


        // assetsManagerGame.loadAllAsseGame();
//        this.pauseScreen = new PauseScreen(this);
//        this.setScreen(pauseScreen);
//        MainGame.status = STATUS_GAME_PAUSE;

        this.gamePlayScreen = new GamePlayScreen(this);
        this.setScreen(this.gamePlayScreen);

    }

    public AssetsManagerGame getAssetsManagerGame() {
        return assetsManagerGame;
    }

    public void transitionScreenGameToMenu() {
        this.mainMenu = new MenuScreen(this);
        this.setScreen(this.mainMenu);
        this.gamePlayScreen.dispose();
    }

    public static void setFlagChangeScreen(byte flagChangeScreen) { // флаг смены экрана
        MainGame.flagChangeScreen = flagChangeScreen;
    }

    public void switchingFromGameMenu() {
        //    playScreen.dispose();
        //assetsManagerGame.loadAllAsseGame();
        //this.gsp.dispose();
        mainMenu = new MenuScreen(this);
        this.setScreen(mainMenu);

    }

    public static boolean isANDROID() {
        return ANDROID;
    }

    public MainClient getMainClient() {
        return mainClient;
    }


    public void getAllAssets() {
        this.assetManager = new AssetManager();
        this.assetManager.update();
        this.assetManager.finishLoading();
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public void updateClien() {
        this.getMainClient().upDateClient();
    }

    public GamePlayScreen getGamePlayScreen() {
        return gamePlayScreen;
    }

    public boolean isMainMenuScreen(){
        System.out.println("---------  " + this.screen.equals(this.mainMenu));
        return this.screen.equals(this.mainMenu);
    }
}
