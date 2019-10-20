package com.pendurpandurok.csuporka;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyStage;

public class GameStage extends MyStage {
    public GameStage(Batch batch, MyGame game) {
        super(new ExtendViewport(1920f / 32, 1080f / 32), batch, game);
    }

    @Override
    public void init() {

    }
}
