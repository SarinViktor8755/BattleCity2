package com.mygdx.tanks2d.Assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.HashMap;
import java.util.Map;


public class AssetsManagerGame {

    HashMap<String, Class> assets;
    AssetManager assetManager;

    public AssetsManagerGame(AssetManager as) {
        this.assets = new HashMap<String, Class>();
        ;
        this.assetManager = as;
    }

    public final AssetManager loadAllAssetMenu() {
        //GdxNativesLoader.load();

        /////////menu
        assets.put("menuAsset/wallpaper.png", Texture.class);
        assets.put("menuAsset/wallpaper1.png", Texture.class);
        assets.put("menuAsset/pley.png", Texture.class);
        assets.put("menuAsset/logo.png", Texture.class);
        assets.put("menuAsset/disconct.png", Texture.class);

        assets.put("skin/uiskin.json", Skin.class);

        loadedAseets();
        return assetManager;
    }


    public final AssetManager loadAllAsseGame() {
        //GdxNativesLoader.load();
        assets.put("de.pack", TextureAtlas.class);
        assets.put("button.pack", TextureAtlas.class);

        assets.put("sound/BSB.ogg", Sound.class);
        //  assets.put("badlogic.png", Texture.class);
        assets.put("target.png", Texture.class);


//            assets.put("badlogic.png", Texture.class);
//             assets.put("badlogic1.png", Texture.class);


        assets.put("particle1.png", Texture.class);
////////////////////
        assets.put("tb.png", Texture.class);
        assets.put("tr.png", Texture.class);

        assets.put("tbb1.png", Texture.class);
        assets.put("tbb2.png", Texture.class);

        assets.put("trb1.png", Texture.class);
        assets.put("trb2.png", Texture.class);


        assets.put("garnd.png", Texture.class);
        assets.put("sled.png", Texture.class);
        assets.put("crater.png", Texture.class);
        //controller
//        assets.put("flatDark26.png", Texture.class);
//        assets.put("flatDark261.png", Texture.class);

        //sound
        // assets.setLoader(TiledMap.class, new TmxMapLoader());
        assets.put("sound/BSB.ogg", Sound.class);
        assets.put("sound/00708.ogg", Sound.class);
        assets.put("sound/bash.ogg", Sound.class);
        assets.put("sound/explode4.ogg", Sound.class);

        assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        assets.put("map/desert.tmx", TiledMap.class);

        assets.put("fire.png", Texture.class);
        assets.put("fonts/font.fnt", BitmapFont.class);

        assets.put("bullet.png", Texture.class);

        assets.put("iron.png", Texture.class);


//        assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
//        assets.put("C:\\tank2d\\android\\assets\\map\\desert.tmx", Texture.class);
        assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        assetManager.load("map/desert.tmx", TiledMap.class);

        loadedAseets();
        return assetManager;
    }

    private void loadedAseets() {
        for (Map.Entry<String, Class> entry : assets.entrySet()) {
            //  System.out.println(entry.getValue());
            if (assetManager.isLoaded(entry.getKey(), entry.getValue())) continue;
            assetManager.load(entry.getKey(), entry.getValue());

        }
        assetManager.finishLoading();
        assetManager.update();
    }

    public final AssetManager unloadAllAsset(AssetManager assetManager) {
        assetManager.dispose();
        return assetManager;
    }

    public float getProgress(AssetManager assetManager) {
        return assetManager.getProgress();
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }
}
