package com.pendurpandurok.csuporka;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyStage;
import hu.csanyzeg.master.MyBaseClasses.UI.MyButton;

public class MenuUI extends MyStage {
    public MenuUI(final Batch batch, final MyGdxGame game) {
        super(new ExtendViewport(1280, 720), batch, game);

        MyButton exit = new MyButton("Exit", game.getButtonStyle());
        exit.setSize(400, 200);
        exit.setPosition(getViewport().getWorldWidth() / 2 / 2 - exit.getWidth() / 2, getViewport().getWorldHeight() / 2 - exit.getHeight());
        exit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Gdx.app.exit();
            }
        });
        addActor(exit);

        MyButton play = new MyButton("Play", game.getButtonStyle());
        play.setSize(400, 200);
        play.setPosition(getViewport().getWorldWidth() - getViewport().getWorldWidth() / 4 - play.getWidth() / 2, getViewport().getWorldHeight() / 2 - play.getHeight());
        play.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(new GameScreen(game));
            }
        });
        addActor(play);

    }

    @Override
    public void init() {

    }
}
