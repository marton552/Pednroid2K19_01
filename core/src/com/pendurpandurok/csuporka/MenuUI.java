package com.pendurpandurok.csuporka;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyStage;

public class MenuUI extends MyStage {
    public MenuUI(Batch batch, MyGame game) {
        super(new ExtendViewport(1280, 720), batch, game);

    }

    @Override
    public void init() {

    }
}
