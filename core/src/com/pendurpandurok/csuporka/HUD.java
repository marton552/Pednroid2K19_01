package com.pendurpandurok.csuporka;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyStage;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteActor;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;
import hu.csanyzeg.master.MyBaseClasses.UI.MyButton;
import hu.csanyzeg.master.MyBaseClasses.UI.MyLabel;

public class HUD extends MyStage {

    MyLabel state;

    OneSpriteStaticActor dark;
    MyButton exit;
    MyButton back;

    public HUD(Batch batch, final MyGdxGame game, final GameScreen screen) {
        super(new ExtendViewport(1080, 1920), batch, game);


        state = new MyLabel("Víz állása: Betöltés", game.getLabelStyle());
        state.setPosition(50, 1500);
        state.setFontScale(2);
        addActor(state);


        OneSpriteStaticActor menu = new OneSpriteStaticActor(Assets.manager.get(Assets.MENU));
        menu.setSize(100, 100);
        menu.setPosition(getViewport().getWorldWidth() - menu.getWidth() - 20, getViewport().getWorldHeight() - menu.getHeight() - 20);

        menu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                screen.isPaused = true;

                dark.setVisible(true);
                back.setVisible(true);
                exit.setVisible(true);

            }
        });

        addActor(menu);

        //Menu
        dark = new OneSpriteStaticActor(Assets.manager.get(Assets.DARK));
        dark.setSize(getViewport().getWorldWidth(), getViewport().getWorldHeight());
        dark.setVisible(false);
        addActor(dark);

        back = new MyButton("Vissza", game.getButtonStyle());
        back.setSize(600, 200);
        back.setPosition(getViewport().getWorldWidth() / 2 - back.getWidth() / 2, getViewport().getWorldHeight() / 2 + 50);
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                screen.isPaused = false;

                dark.setVisible(false);
                back.setVisible(false);
                exit.setVisible(false);
            }
        });
        back.setVisible(false);
        addActor(back);

        exit = new MyButton("Menü", game.getButtonStyle());
        exit.setSize(600, 200);
        exit.setPosition(getViewport().getWorldWidth() / 2 - exit.getWidth() / 2, getViewport().getWorldHeight() / 2 - exit.getHeight() - 50);
        exit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                game.setScreen(new MenuScreen(game));
            }
        });
        exit.setVisible(false);
        addActor(exit);

    }

    public void updateText(String s) {
        state.setText(s);
    }

    @Override
    public void init() {

    }
}
