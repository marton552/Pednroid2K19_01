package com.pendurpandurok.csuporka;

import com.badlogic.gdx.Gdx;

import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyScreen;

public class AboutScreen extends MyScreen {
    AboutStage aboutStage;

    public AboutScreen(MyGame game) {
        super(game);

        aboutStage = new AboutStage(spriteBatch, game);
        Gdx.input.setInputProcessor(aboutStage);
    }


    @Override
    public void render(float delta) {
        super.render(delta);
        aboutStage.act(delta);
        aboutStage.draw();
    }

    @Override
    public void init() {

    }
}
