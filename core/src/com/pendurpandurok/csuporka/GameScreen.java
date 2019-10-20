package com.pendurpandurok.csuporka;

import com.badlogic.gdx.Gdx;

import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyScreen;

public class GameScreen extends MyScreen {

    GameStage gameStage;

    public GameScreen(MyGame game) {
        super(game);

        gameStage = new GameStage(spriteBatch, game);
        Gdx.input.setInputProcessor(gameStage);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        gameStage.act(delta);
        gameStage.draw();
    }

    @Override
    public void init() {

    }
}
