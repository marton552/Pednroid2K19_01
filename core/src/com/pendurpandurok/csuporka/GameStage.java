package com.pendurpandurok.csuporka;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

import finnstr.libgdx.liquidfun.ColorParticleRenderer;
import finnstr.libgdx.liquidfun.ParticleDef;
import finnstr.libgdx.liquidfun.ParticleGroupDef;
import finnstr.libgdx.liquidfun.ParticleSystem;
import finnstr.libgdx.liquidfun.ParticleSystemDef;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyStage;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;

public class GameStage extends MyStage {

    private World mWorld;
    private ParticleSystem mParticleSystem;
    private ColorParticleRenderer mParticleDebugRenderer;
    private Box2DDebugRenderer mDebugRenderer;

    private ParticleGroupDef mParticleGroupDef1;

    RealisticWater rw;

    OneSpriteStaticActor t;
    OneSpriteStaticActor t_szeg;
    OneSpriteStaticActor tartaly;
    Body a;
    Body b;
    Body c;
    Body d;

    public ArrayList<Body> bodyk = new ArrayList<Body>();


    public GameStage(Batch batch, MyGame game) {
        super(new ExtendViewport(1080 / 32, 1920 / 32), batch, game);

        rw = new RealisticWater(batch, getCamera(), getViewport().getWorldWidth(), getViewport().getWorldHeight());


        mWorld = new World(new Vector2(0, -9.2f), false);
        float width = getViewport().getWorldWidth();
        float height = getViewport().getWorldHeight();

        createParticleStuff(width, height);

        mDebugRenderer = new Box2DDebugRenderer();
        mParticleDebugRenderer = new ColorParticleRenderer(mParticleSystem.getParticleCount() + 10000);

        //a nagy tartály
        t = new OneSpriteStaticActor(Assets.manager.get(Assets.T_HATTER));
        t.setSize(30,30);
        t.setPosition(1,15);
        t.setZIndex(1);
        addActor(t);
        t_szeg = new OneSpriteStaticActor(Assets.manager.get(Assets.T_SZEG));
        t_szeg.setSize(30,30);
        t_szeg.setPosition(1,15);
        addActor(t_szeg);
        createRectangle(1, 30, (float)0.1, 30, 0* MathUtils.degreesToRadians);
        createRectangle(31, 30, (float)0.1, 30, 0* MathUtils.degreesToRadians);
        //csőőőő
        createRectangle((float)2.8, 15, (float)3.33,(float)0.1, 0* MathUtils.degreesToRadians);
        createRectangle((float)9.46, 15, (float)3.75,(float)0.1, 0* MathUtils.degreesToRadians);
        createRectangle((float)16.12, 15, (float)3.75,(float)0.1, 0* MathUtils.degreesToRadians);
        createRectangle((float)22.78, 15, (float)3.75,(float)0.1, 0* MathUtils.degreesToRadians);
        createRectangle((float)29.44, 15, (float)3.75,(float)0.1, 0* MathUtils.degreesToRadians);

        //ahol nyílik a cső
        a = createRectangle((float)6.13, 15, (float)3.75,(float)0.1, 0* MathUtils.degreesToRadians);
        b = createRectangle((float)12.79, 15, (float)3.75,(float)0.1, 0* MathUtils.degreesToRadians);
        c = createRectangle((float)19.45, 15, (float)3.75,(float)0.1, 0* MathUtils.degreesToRadians);
        d = createRectangle((float)26.11, 15, (float)3.75,(float)0.1, 0* MathUtils.degreesToRadians);
        bodyk.add(a);
        bodyk.add(b);
        bodyk.add(c);
        bodyk.add(d);

        //akkor itt már tényleg hozzá adjuk a csapokat

        //ahonnan folyik a víz
        tartaly = new OneSpriteStaticActor(Assets.manager.get(Assets.TART_HATTER));
        tartaly.setSize(30,10);
        tartaly.setPosition(1,51);
        addActor(tartaly);
        //bal oldala
        createRectangle((float)8.5, (float)57.5, (float)0.1, 13, 60* MathUtils.degreesToRadians);
        createRectangle(3, 75, (float)0.1, 30, 0* MathUtils.degreesToRadians);
        createRectangle((float)14.2, (float)52.8, (float)0.1, 3, 0* MathUtils.degreesToRadians);
        //jobb oldala
        createRectangle((float)23.5, (float)57.5, (float)0.1, 13, 300* MathUtils.degreesToRadians);
        createRectangle(29, 75, (float)0.1, 30, 0* MathUtils.degreesToRadians);
        createRectangle((float)17.8, (float)52.8, (float)0.1, 3, 0* MathUtils.degreesToRadians);
        createParticles(getViewport().getWorldWidth()/2, getViewport().getWorldHeight()+10);
    }

    public Body createRectangle(float posX, float posY, float width, float height, float angle) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(posX, posY);
        bodyDef.angle = angle;

        Body body = mWorld.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2.0f, height / 2.0f);
        //System.out.println(height / 2.0f, width / 2.0f);

        FixtureDef fixDef = new FixtureDef();
        fixDef.friction = 0.8f;
        fixDef.shape = shape;
        body.createFixture(fixDef);
        //mWorld.destroyBody(body);

        shape.dispose();

        return body;
    }

    private void createParticleStuff(float width, float height) {
        //First we create a new particlesystem and
        //set the radius of each particle to 6 / 120 m (5 cm)
        ParticleSystemDef systemDef = new ParticleSystemDef();
        systemDef.radius = 0.2f;
        systemDef.dampingStrength = 0.6f;

        mParticleSystem = new ParticleSystem(mWorld, systemDef);
        mParticleSystem.setParticleDensity(2f);

        //Create a new particlegroupdefinition and set some properties
        //For the flags you can set more than only one
        mParticleGroupDef1 = new ParticleGroupDef();
        mParticleGroupDef1.color.set(0, 0, 1f, 1);
        mParticleGroupDef1.flags.add(ParticleDef.ParticleType.b2_waterParticle);
        mParticleGroupDef1.position.set(width * (30f / 100f) * 1, height * (80f / 100f) * 1);

        PolygonShape parShape = new PolygonShape();
        parShape.setAsBox(2 / 2f, 10 / 2f);
        //parShape.setAsBox(10, 10);
        //parShape.setRadius(1f);
        mParticleGroupDef1.shape = parShape;
        //mParticleSystem.createParticleGroup(mParticleGroupDef1);

    }

    public void createParticles(float pX, float pY) {
        mParticleGroupDef1.position.set(pX, pY);
        mParticleSystem.createParticleGroup(mParticleGroupDef1);
        updateParticleCount();
    }

    private void updateParticleCount() {
        if(mParticleSystem.getParticleCount() > mParticleDebugRenderer.getMaxParticleNumber()) {
            mParticleDebugRenderer.setMaxParticleNumber(mParticleSystem.getParticleCount() + 10);
        }
    }

    @Override
    public void draw() {
        mWorld.step(Gdx.graphics.getDeltaTime(), 10, 6, mParticleSystem.calculateReasonableParticleIterations(Gdx.graphics.getDeltaTime()));

        super.draw();
        //draw our scene here
        //actor.draw(getBatch(), 1f);

        rw.startRender();
        mParticleDebugRenderer.render(mParticleSystem, 1, getCamera().combined);
        rw.stopRender();

        //render box2d
        mDebugRenderer.render(mWorld, getCamera().combined);


    }
    int counter = 0;
    public void act(float delta) {
        super.act(delta);
        counter++;
        if(counter % MathUtils.random(8, 500) == 0)
            createParticles(getViewport().getWorldWidth()/2, getViewport().getWorldHeight()+10);

    }

    @Override
    public void init() {

    }
}
