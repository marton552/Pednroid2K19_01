package com.pendurpandurok.csuporka;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import finnstr.libgdx.liquidfun.ColorParticleRenderer;
import finnstr.libgdx.liquidfun.ParticleDebugRenderer;
import finnstr.libgdx.liquidfun.ParticleDef;
import finnstr.libgdx.liquidfun.ParticleGroupDef;
import finnstr.libgdx.liquidfun.ParticleSystem;
import finnstr.libgdx.liquidfun.ParticleSystemDef;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyStage;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;
import hu.csanyzeg.master.MyBaseClasses.UI.MyButton;

public class MenuStage extends MyStage {


    //http://www.patrickmatte.com/stuff/physicsLiquid/
    //https://github.com/mattdesl/lwjgl-basics/wiki/ShaderLesson5#Ports - Blur
    //https://github.com/libgdx/libgdx/wiki/Distance-field-fonts -- Alpha treshold
    private World mWorld;
    private ParticleSystem mParticleSystem;
    private ColorParticleRenderer mParticleDebugRenderer;
    private Box2DDebugRenderer mDebugRenderer;

    private ParticleGroupDef mParticleGroupDef1;

    private final static float BOX_TO_WORLD = 1f;
    private final static float WORLD_TO_BOX = 1f / BOX_TO_WORLD;

    private Sprite sprite;
    OneSpriteStaticActor actor;

    RealisticWater rw;

    OneSpriteStaticActor vodor1;

    public MenuStage(Batch batch, MyGdxGame game) {
        super(new ExtendViewport(1080 / 32f, 1920 / 32f), batch, game);
        System.out.println( getCamera().viewportWidth);
        rw = new RealisticWater(batch, getCamera(), getViewport().getWorldWidth(), getViewport().getWorldHeight());
        vodor1 = new OneSpriteStaticActor(Assets.manager.get(Assets.VODOR_HATTER));
        vodor1.setPosition((float)0,4);
        vodor1.setSize(14,7);
        addActor(vodor1);

        mWorld = new World(new Vector2(0, -9.2f), false);
        float width = getViewport().getWorldWidth();
        float height = getViewport().getWorldHeight();

        /*sprite = new Sprite(region);
        sprite.setSize(width, height);
        sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
        sprite.setPosition(0, 0);*/

        createBox2DWorld(width, height, game);
        createParticleStuff(width, height);



        //for (int i = 0; i < 100; i++) createParticles1(width * (30f / 100f) * WORLD_TO_BOX + i * 3, height * (80f / 100f) * WORLD_TO_BOX);


        mDebugRenderer = new Box2DDebugRenderer();
        mParticleDebugRenderer = new ColorParticleRenderer(mParticleSystem.getParticleCount() + 10000);
        //mParticleDebugRenderer.setMaxParticleNumber(mParticleSystem.getParticleCount() + 10000);

    }

    public void createRectangle(float posX, float posY, float width, float height, float angle) {
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

        shape.dispose();
    }

    public void createCircleBody(float pX, float pY, float pRadius) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(pX * WORLD_TO_BOX, pY * WORLD_TO_BOX);
        Body body = mWorld.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(pRadius * WORLD_TO_BOX);

        FixtureDef fixDef = new FixtureDef();
        fixDef.density = 0.5f;
        fixDef.friction = 0.2f;
        fixDef.shape = shape;
        fixDef.restitution = 0.3f;

        body.createFixture(fixDef);
    }


    private void createBox2DWorld(float width, float height, MyGdxGame g) {
        mWorld = new World(new Vector2(0, -9.8f), false);

        createRectangle(7, 5, 10, 2, 0);
        createRectangle(12, 8, 2, 7, 170* MathUtils.degreesToRadians);
        createRectangle(2, 8, 2, 7, 190* MathUtils.degreesToRadians);


        createRectangle(width - 14, 5, 10, 2, 0);
        createRectangle(width - 19, 8, 2, 7, 190* MathUtils.degreesToRadians);
        createRectangle(width - 9, 8, 2, 7, 170* MathUtils.degreesToRadians);



        //getCamera().position.y -= 5;

        /*
        // Bottom
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(width * WORLD_TO_BOX / 2f, height * (2f / 100f) * WORLD_TO_BOX / 2f);
        //bodyDef.angle = (float) Math.toRadians(-30);
        Body ground = mWorld.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width * WORLD_TO_BOX / 2, height * (2f / 100f) * WORLD_TO_BOX / 2f);

        FixtureDef fixDef = new FixtureDef();
        fixDef.friction = 0.8f;
        fixDef.shape = shape;
        ground.createFixture(fixDef);

        shape.dispose();

        // Walls
        BodyDef bodyDef1 = new BodyDef();
        bodyDef1.type = BodyDef.BodyType.StaticBody;
        bodyDef1.position.set(width * (2f / 100f) * WORLD_TO_BOX / 2f, height * WORLD_TO_BOX / 2);
        Body left = mWorld.createBody(bodyDef1);

        bodyDef1.position.set(width * WORLD_TO_BOX - width * (2f / 100f) * WORLD_TO_BOX / 2f, height * WORLD_TO_BOX / 2);
        Body right = mWorld.createBody(bodyDef1);

        shape = new PolygonShape();
        shape.setAsBox(width * (2f / 100f) * WORLD_TO_BOX / 2f, height * WORLD_TO_BOX / 2);
        fixDef.shape = shape;

        left.createFixture(fixDef);
        right.createFixture(fixDef);
        shape.dispose();
        */
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
        mParticleGroupDef1.color.set(0f, 0, 1, 1);
        mParticleGroupDef1.flags.add(ParticleDef.ParticleType.b2_waterParticle);
        mParticleGroupDef1.position.set(width * (30f / 100f) * WORLD_TO_BOX, height * (80f / 100f) * WORLD_TO_BOX);

        //Create a shape, give it to the definition and
        //create the particlegroup in the particlesystem.
        //This will return you a ParticleGroup instance, but
        //we don't need it here, so we drop that.
        //The shape defines where the particles are created exactly
        //and how much are created
        PolygonShape parShape = new PolygonShape();
        parShape.setAsBox(2 / 2f, 10 / 2f);
        //parShape.setAsBox(10, 10);
        //parShape.setRadius(1f);
        mParticleGroupDef1.shape = parShape;
        //mParticleSystem.createParticleGroup(mParticleGroupDef1);
    }

    public void createParticles(float pX, float pY, float r, float g, float b, float a) {
        mParticleGroupDef1.position.set(pX, pY);
        mParticleGroupDef1.color.set(r, g, b, a);
        mParticleSystem.createParticleGroup(mParticleGroupDef1);
        updateParticleCount();
    }

    private void updateParticleCount() {
        if(mParticleSystem.getParticleCount() > mParticleDebugRenderer.getMaxParticleNumber()) {
            mParticleDebugRenderer.setMaxParticleNumber(mParticleSystem.getParticleCount() + 10);
        }
    }

    int counter = 0;
    @Override
    public void act(float delta) {
        super.act(delta);
        counter++;

    }

    @Override
    public void draw() {

        super.draw();


        mWorld.step(Gdx.graphics.getDeltaTime(), 10, 6, mParticleSystem.calculateReasonableParticleIterations(Gdx.graphics.getDeltaTime()));


        //draw our scene here
        //actor.draw(getBatch(), 1f);

        rw.startRender();
            mParticleDebugRenderer.render(mParticleSystem, 1, getCamera().combined);
        rw.stopRender();

        //mDebugRenderer.render(mWorld, getCamera().combined);




        //mParticleDebugRenderer.render(mParticleSystem, BOX_TO_WORLD, getCamera().combined);

        //render box2d
        mDebugRenderer.render(mWorld, getCamera().combined);



    }

    @Override
    public void init() {

    }
}
