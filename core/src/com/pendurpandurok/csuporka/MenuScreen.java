package com.pendurpandurok.csuporka;

import com.badlogic.gdx.Gdx;

import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyScreen;

public class MenuScreen extends MyScreen {
    MenuStage stage;

    @Override
    public void render(float delta) {
        super.render(delta);
        stage.act(delta);
        stage.draw();
    }

    public MenuScreen(MyGame game) {
        super(game);
        stage = new MenuStage(spriteBatch, game);
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void init() {

    }
}
