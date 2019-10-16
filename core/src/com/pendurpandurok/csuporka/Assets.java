//https://github.com/tuskeb/mester
package com.pendurpandurok.csuporka;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;


public class Assets {
    // https://github.com/libgdx/libgdx/wiki/Managing-your-assets
    // http://www.jacobplaster.net/using-libgdx-asset-manager
    // https://www.youtube.com/watch?v=JXThbQir2gU
    // https://github.com/Matsemann/libgdx-loading-screen/blob/master/Main/src/com/matsemann/libgdxloadingscreen/screen/LoadingScreen.java

    public static AssetManager manager;
    public static final AssetDescriptor<Texture> BADLOGIC_TEXTURE
            = new AssetDescriptor<Texture>("badlogic.jpg", Texture.class);

    public static void prepare() {
        manager = new AssetManager();
        Texture.setAssetManager(manager);
    }

    public static void load() {

        manager.load(BADLOGIC_TEXTURE);

    }

    public static void afterLoaded() {
        //manager.get(MUSIC).setLooping(true);
    }

    public static void unload() {
        manager.dispose();
    }

}