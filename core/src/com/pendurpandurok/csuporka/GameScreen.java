package com.pendurpandurok.csuporka;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;

import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyScreen;

public class GameScreen extends MyScreen {

    GameStage gameStage;
    HUD hud;

    public boolean isPaused = false;

    public GameScreen(MyGdxGame game) {
        super(game);
        hud = new HUD(spriteBatch, game, this);
        gameStage = new GameStage(spriteBatch, game, hud);

        InputMultiplexer im = new InputMultiplexer();
        im.addProcessor(hud);
        im.addProcessor(gameStage);

        Gdx.input.setInputProcessor(im);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        if(isPaused == false)
            gameStage.act(delta);
        gameStage.draw();

        hud.act(delta);
        hud.draw();
    }

    @Override
    public void init() {

    }
}
