package com.pendurpandurok.csuporka;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;

public class Duck {

    OneSpriteStaticActor texture;
    Body kacsaphsy;

    private GameStage gs;

    public Duck(GameStage gs, float x, float y) {
        this.gs = gs;

        if(MathUtils.random(0, 1) == 0) {
            texture = new OneSpriteStaticActor(Assets.manager.get(Assets.KACSA));
            texture.setSize(texture.getWidth() / 300, texture.getHeight() / 300);
        }
        else {
            texture = new OneSpriteStaticActor(Assets.manager.get(Assets.HAJO));
            texture.setSize(texture.getWidth() / 290, texture.getHeight() / 290);

        }

        texture.setVisible(false);

        spawnKacsa(x, y);
    }

    public void draw() {
        texture.setPosition(kacsaphsy.getPosition().x - texture.getWidth() / 2, kacsaphsy.getPosition().y - texture.getHeight() / 2);
        texture.draw(gs.getBatch(), 1);
    }

    private void spawnKacsa(float x, float y) {
        kacsaphsy = createCircleBody(x, y, 2f);
        texture.setVisible(true);
    }

    public void destroyKacsa() {
        texture = null;
        gs.mWorld.destroyBody(kacsaphsy);
    }

    private Body createCircleBody(float pX, float pY, float pRadius) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(pX, pY);
        Body body = gs.mWorld.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(pRadius);

        FixtureDef fixDef = new FixtureDef();
        fixDef.density = 0.5f;
        fixDef.friction = 0.2f;
        fixDef.shape = shape;
        fixDef.restitution = 0.3f;

        body.createFixture(fixDef);
        return body;
    }
}
