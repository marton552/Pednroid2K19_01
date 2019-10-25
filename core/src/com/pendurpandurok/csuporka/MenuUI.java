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
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;
import hu.csanyzeg.master.MyBaseClasses.UI.MyButton;



public class MenuUI extends MyStage {
    public int clicked = 0;
    OneSpriteStaticActor jesus;
    OneSpriteStaticActor prof;
    public MenuUI(final Batch batch, final MyGdxGame game, final MenuStage ms) {
        super(new ExtendViewport(1280, 720), batch, game);

        MyButton exit = new MyButton("Exit", game.getButtonStyle());
        exit.setSize(400, 200);
        exit.setPosition(getViewport().getWorldWidth() / 2 / 2 - exit.getWidth() / 2, getViewport().getWorldHeight() / 2 - exit.getHeight());
        exit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                clicked = 1;
                ms.createParticles(6, ms.getViewport().getWorldHeight() + 5, 1, 0, 0, 1);

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
                clicked = 2;
                ms.createParticles(ms.getViewport().getWorldWidth() - 8, ms.getViewport().getWorldHeight() + 5, 0, 0, 1, 1);;

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

        MyButton about = new MyButton("About", game.getButtonStyle());
        about.setSize(200, 100);
        about.setPosition(getViewport().getWorldWidth() - getViewport().getWorldWidth() / 2 - about.getWidth() / 2, getViewport().getWorldHeight() / 2 - about.getHeight()+100);
        about.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(new AboutScreen(game));}}
        );
        addActor(about);

        OneSpriteStaticActor logo = new OneSpriteStaticActor(Assets.manager.get(Assets.LOGO));
        logo.setSize(800,500);
        logo.setPosition(getViewport().getWorldWidth() / 2 - logo.getWidth() / 2, getViewport().getWorldHeight() / 6);
        addActor(logo);
        OneSpriteStaticActor cim = new OneSpriteStaticActor(Assets.manager.get(Assets.CIM));
        cim.setSize(800,100);
        cim.setPosition(getViewport().getWorldWidth() / 2 - cim.getWidth() / 2, getViewport().getWorldHeight() / 10);
        addActor(cim);

        jesus = new OneSpriteStaticActor(Assets.manager.get(Assets.JESUS));
        jesus.setSize(800,1200);
        jesus.setPosition(getViewport().getWorldWidth() / 2 - jesus.getWidth() / 2, getViewport().getWorldHeight()*-0.9f);
        addActor(jesus);

        prof = new OneSpriteStaticActor(Assets.manager.get(Assets.BETTER_PROF));
        prof.setSize(800,1200);
        prof.setPosition(getViewport().getWorldWidth() / 2 - prof.getWidth() / 2, getViewport().getWorldHeight()*-0.9f);
        addActor(prof);

    }

    @Override
    public void init() {

    }

    int counter=0;
    float min = (getViewport().getWorldHeight()*-0.9f);
    float most = min;
    float max = getViewport().getWorldHeight()*-0.4f;
    public void act(float delta) {
        super.act(delta);
        if(clicked != 0)counter++;
        if(clicked == 1 && max > most){
            most+=10;
            jesus.setPosition(getViewport().getWorldWidth() / 2 - jesus.getWidth() / 2, most);
        }
        if(clicked == 2 && max > most){
            most+=10;
            prof.setPosition(getViewport().getWorldWidth() / 2 - prof.getWidth() / 2, most);
        }
    }
}
