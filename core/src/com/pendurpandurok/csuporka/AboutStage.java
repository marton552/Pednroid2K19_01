package com.pendurpandurok.csuporka;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyStage;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;
import hu.csanyzeg.master.MyBaseClasses.UI.MyButton;
import hu.csanyzeg.master.MyBaseClasses.UI.MyLabel;

public class AboutStage extends MyStage {
    public AboutStage(Batch batch, final MyGdxGame game) {
        super(new ExtendViewport(1080f, 1920f), batch, game);

        OneSpriteStaticActor bg = new OneSpriteStaticActor(Assets.manager.get(Assets.HATTER));
        bg.setSize(getViewport().getWorldWidth(), getViewport().getWorldHeight());
        bg.setPosition(0, 0);
        addActor(bg);

        MyLabel info = new MyLabel("A játék neve: Csuporka\n\nEz egy víz szimuláló program,\na csapok mellett található '+' és '-' gombokkal\nlehet szabályozni a csap átereszthetőségét\n\nA középső nagy tankra érintve,\nakár egy kacsát vagy egy papírhajót is lerakhatunk.\n\nA játékot készítette a PENdúrPandúrok csapata.\n(Peszleg Márton, Horváth Martin, Gút Dávid, Farkas Valter)\nFelkészítő tanár: Tüske Balázs", game.getLabelStyle());
        info.setPosition(getViewport().getWorldWidth() / 2 - info.getWidth() / 2, getViewport().getWorldHeight() / 2 - info.getHeight() / 2 + 300);
        info.setAlignment(Align.center);
        info.setFontScale(1.3f);
        addActor(info);

        MyButton back = new MyButton("Vissza", game.getButtonStyle());
        back.setSize(600, 200);
        back.setPosition(getViewport().getWorldWidth() / 2 - back.getWidth() / 2, 80);
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                game.setScreen(new MenuScreen(game));
            }
        });
        addActor(back);
    }

    @Override
    public void init() {

    }
}
