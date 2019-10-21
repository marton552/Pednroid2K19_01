package com.pendurpandurok.csuporka;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;

import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyScreen;

public class MenuScreen extends MyScreen {
    MenuStage stage;
    MenuUI ui;

    @Override
    public void render(float delta) {
        super.render(delta);
        stage.act(delta);
        stage.draw();

        ui.act(delta);
        ui.draw();
    }

    public MenuScreen(MyGdxGame game) {
        super(game);
        stage = new MenuStage(spriteBatch, game);
        ui = new MenuUI(spriteBatch, game);

        InputMultiplexer im = new InputMultiplexer();
        im.addProcessor(stage);
        im.addProcessor(ui);
        Gdx.input.setInputProcessor(im);

    }

    @Override
    public void init() {

    }
}
