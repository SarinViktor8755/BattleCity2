package com.mygdx.tanks2d;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher_OnePlus8Pro {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("Tanks2D");
		config.setWindowedMode(3168/5  ,1440 /5 );
//		config.width = MainGame.WHIDE_SCREEN / 2;
//		config.height = MainGame.HIDE_SCREEN / 2;
		new Lwjgl3Application(new MainGame(3), config);
	}
}
