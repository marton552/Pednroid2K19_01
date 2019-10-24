package com.pendurpandurok.csuporka;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyStage;
import hu.csanyzeg.master.MyBaseClasses.UI.MyButton;

public class MenuUI extends MyStage {
    public MenuUI(final Batch batch, final MyGdxGame game, final MenuStage ms) {
        super(new ExtendViewport(1280, 720), batch, game);

        MyButton exit = new MyButton("Exit", game.getButtonStyle());
        exit.setSize(400, 200);
        exit.setPosition(getViewport().getWorldWidth() / 2 / 2 - exit.getWidth() / 2, getViewport().getWorldHeight() / 2 - exit.getHeight());
        exit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                ms.createParticles(6, ms.getViewport().getWorldHeight() + 10, 1, 0, 0, 1);

                Timer.schedule(new Timer.Task(){
                                   @Override
                                   public void run() {
                                       Gdx.app.exit();
                                   }
                               }
                        ,4, 0, 1);
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
                ms.createParticles(ms.getViewport().getWorldWidth() - 8, ms.getViewport().getWorldHeight() + 10, 0, 0, 1, 1);;

                Timer.schedule(new Timer.Task(){
                                   @Override
                                   public void run() {
                                       game.setScreen(new GameScreen(game));
                                   }
                               }
                ,4, 0, 1);
            }
        });
        addActor(play);

    }

    @Override
    public void init() {

    }
}
