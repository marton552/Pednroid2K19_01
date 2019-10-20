package com.pendurpandurok.csuporka;

import com.badlogic.gdx.graphics.g2d.BitmapFont;

import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyScreen;

public class LoadingScreen extends MyScreen {

    private MyGdxGame g;
    public LoadingScreen(MyGdxGame game) {
        super(game);
        this.g = game;
    }


    @Override
    public void show() {
        Assets.manager.finishLoading();
        Assets.load();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        if (Assets.manager.update()) {
            Assets.afterLoaded();
            game.setScreen(new MenuScreen(g));
        }
    }

    @Override
    public void hide() {

    }

    @Override
    public void init() {
        setBackGroundColor(0f, 0f, 0f);
    }
}