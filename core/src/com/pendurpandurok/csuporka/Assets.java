//https://github.com/tuskeb/mester
package com.pendurpandurok.csuporka;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;


public class Assets {
    // https://github.com/libgdx/libgdx/wiki/Managing-your-assets
    // http://www.jacobplaster.net/using-libgdx-asset-manager
    // https://www.youtube.com/watch?v=JXThbQir2gU
    // https://github.com/Matsemann/libgdx-loading-screen/blob/master/Main/src/com/matsemann/libgdxloadingscreen/screen/LoadingScreen.java
    public static AssetManager manager;


    public static final String CHARS = "0123456789öüóqwertzuiopőúasdfghjkléáűíyxcvbnm'+!%/=()ÖÜÓQWERTZUIOPŐÚASDFGHJKLÉÁŰÍYXCVBNM?:_*<>#&@{}[],-.";


    static final FreetypeFontLoader.FreeTypeFontLoaderParameter fontParameter = new FreetypeFontLoader.FreeTypeFontLoaderParameter();

    static {
        fontParameter.fontFileName = "alegreyaregular.otf";
        fontParameter.fontParameters.size = 30;
        fontParameter.fontParameters.borderColor = Color.WHITE;
        fontParameter.fontParameters.borderWidth = 1;
        fontParameter.fontParameters.characters = CHARS;
        fontParameter.fontParameters.color = Color.WHITE;
    }
    public static final AssetDescriptor<BitmapFont> ALEGREYAREGULAR_FONT
            = new AssetDescriptor<BitmapFont>(fontParameter.fontFileName, BitmapFont.class, fontParameter);



    //Button
    public static final AssetDescriptor<Texture> BTN_BACK = new AssetDescriptor<Texture>("ui_textures/btn_back.png", Texture.class);
    public static final AssetDescriptor<Texture> BTN_HOVER = new AssetDescriptor<Texture>("ui_textures/btn_hover.png", Texture.class);
    public static final AssetDescriptor<Texture> BETTER_PROF = new AssetDescriptor<Texture>("better_prof.png", Texture.class);
    public static final AssetDescriptor<Texture> PROF = new AssetDescriptor<Texture>("prof.png", Texture.class);
    public static final AssetDescriptor<Texture> CSAP = new AssetDescriptor<Texture>("csap.png", Texture.class);
    public static final AssetDescriptor<Texture> JESUS = new AssetDescriptor<Texture>("Jesus.png", Texture.class);
    public static final AssetDescriptor<Texture> KACSA = new AssetDescriptor<Texture>("kaca.png", Texture.class);
    public static final AssetDescriptor<Texture> HAJO = new AssetDescriptor<Texture>("papperHajo.png", Texture.class);
    public static final AssetDescriptor<Texture> T_HATTER = new AssetDescriptor<Texture>("T_Hatter.png", Texture.class);
    public static final AssetDescriptor<Texture> T_SZEG = new AssetDescriptor<Texture>("T_szeg.png", Texture.class);
    public static final AssetDescriptor<Texture> TART_HATTER = new AssetDescriptor<Texture>("Tart_Hatter.png", Texture.class);
    public static final AssetDescriptor<Texture> TART_SZEG = new AssetDescriptor<Texture>("Tart_szeg.png", Texture.class);
    public static final AssetDescriptor<Texture> VODOR_HATTER = new AssetDescriptor<Texture>("vodor.png", Texture.class);
    public static final AssetDescriptor<Texture> BAR = new AssetDescriptor<Texture>("bar.png", Texture.class);
    public static final AssetDescriptor<Texture> HATTER = new AssetDescriptor<Texture>("hatter.png", Texture.class);
    public static final AssetDescriptor<Texture> PLUS = new AssetDescriptor<Texture>("plus.png", Texture.class);
    public static final AssetDescriptor<Texture> MINUS = new AssetDescriptor<Texture>("minus.png", Texture.class);


    //Stb.
    public static final AssetDescriptor<Texture> BADLOGIC_TEXTURE
            = new AssetDescriptor<Texture>("badlogic.jpg", Texture.class);

    public static void prepare() {
        manager = new AssetManager();
        Texture.setAssetManager(manager);
    }

    public static void load() {
        FileHandleResolver resolver = new InternalFileHandleResolver();
        manager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        manager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));
        manager.setLoader(BitmapFont.class, ".otf", new FreetypeFontLoader(resolver));

        manager.load(BADLOGIC_TEXTURE);

        //Btn
        manager.load(BTN_BACK);
        manager.load(BTN_HOVER);
        manager.load(BETTER_PROF);
        manager.load(PROF);
        manager.load(CSAP);
        manager.load(JESUS);
        manager.load(KACSA);
        manager.load(HAJO);
        manager.load(T_HATTER);
        manager.load(T_SZEG);
        manager.load(TART_HATTER);
        manager.load(TART_SZEG);
        manager.load(VODOR_HATTER);
        manager.load(BAR);
        manager.load(HATTER);
        manager.load(PLUS);
        manager.load(MINUS);


        manager.load(ALEGREYAREGULAR_FONT);

    }

    public static void afterLoaded() {
        //manager.get(MUSIC).setLooping(true);
    }

    public static void unload() {
        manager.dispose();
    }

}