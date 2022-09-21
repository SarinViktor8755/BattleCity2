package com.mygdx.tanks2d;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.InterstitialAd;


public class AndroidLauncher extends AndroidApplication {

//	private InterstitialAd mInterstitialAd;
//	private static final int PERMISSION_REQUEST_CODE = 1;


	@Override
	protected void onCreate (Bundle savedInstanceState) {


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            // The app has the camera permission.
            Log.v("ERROR", "CAMERA YESSS !!!!!!");
        } else {
            // Apply for the camera permission.
            Log.v("ERROR", "CAMERA NOOO !!!!!!!!!!!!!! ");
        }



		
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		initialize(new MainGame(1), config);
	}
}
