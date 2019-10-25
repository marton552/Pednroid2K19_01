package com.pendurpandurok.csuporka;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
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
import com.badlogic.gdx.utils.Timer;
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
import hu.csanyzeg.master.MyBaseClasses.UI.MyButton;
import hu.csanyzeg.master.MyBaseClasses.UI.MyLabel;

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
    Body fedel;
    OneSpriteStaticActor csap1;
    OneSpriteStaticActor csap2;
    OneSpriteStaticActor csap3;
    OneSpriteStaticActor csap4;
    OneSpriteStaticActor bg;
    OneSpriteStaticActor plus1;
    OneSpriteStaticActor minus1;
    OneSpriteStaticActor plus2;
    OneSpriteStaticActor minus2;
    OneSpriteStaticActor plus3;
    OneSpriteStaticActor minus3;
    OneSpriteStaticActor plus4;
    OneSpriteStaticActor minus4;

    OneSpriteStaticActor nagyplus;
    OneSpriteStaticActor nagyminus;

    MyLabel var1;
    MyLabel var2;
    MyLabel var3;
    MyLabel var4;
    MyLabel varfo;

    public double befolyo = 10;
    public double kozep = 9;
    public int csapv1 = 0;
    public int csapv2 = 0;
    public int csapv3 = 0;
    public int csapv4 = 0;
    public int fo = 1;
    public boolean csapbool1 = false;
    public boolean csapbool2 = false;
    public boolean csapbool3 = false;
    public boolean csapbool4 = false;
    public boolean seton = false;

    OneSpriteStaticActor kacsa;
    Body kacsaphsy;
    boolean kacsaSpawned = false;

    public ArrayList<Body> bodyk = new ArrayList<Body>();


    public GameStage(Batch batch, MyGdxGame game) {
        super(new ExtendViewport(1080 / 32, 1920 / 32), batch, game);
        bg = new OneSpriteStaticActor(Assets.manager.get(Assets.HATTER));
        bg.setSize(getViewport().getWorldWidth(),getViewport().getWorldHeight());
            bg.setPosition(0,0);
        addActor(bg);

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
        csap1 = new OneSpriteStaticActor(Assets.manager.get(Assets.CSAP));
        csap1.setSize(8.5f,8.5f);
        csap1.setPosition((float)2,(float)7);
        createRectangle((float)4.6, (float)11.5, (float)0.1, 7, 0* MathUtils.degreesToRadians);
        createRectangle((float)7.8, (float)11.5, (float)0.1, 7, 0* MathUtils.degreesToRadians);

        csap2 = new OneSpriteStaticActor(Assets.manager.get(Assets.CSAP));
        csap2.setSize(8.5f,8.5f);
        csap2.setPosition((float)8.66,(float)7);
        createRectangle((float)4.6, (float)11.5, (float)0.1, 7, 0* MathUtils.degreesToRadians);
        createRectangle((float)7.8, (float)11.5, (float)0.1, 7, 0* MathUtils.degreesToRadians);

        csap3 = new OneSpriteStaticActor(Assets.manager.get(Assets.CSAP));
        csap3.setSize(8.5f,8.5f);
        csap3.setPosition((float)15.32,(float)7);
        createRectangle((float)4.6, (float)11.5, (float)0.1, 7, 0* MathUtils.degreesToRadians);
        createRectangle((float)7.8, (float)11.5, (float)0.1, 7, 0* MathUtils.degreesToRadians);

        csap4 = new OneSpriteStaticActor(Assets.manager.get(Assets.CSAP));
        csap4.setSize(8.5f,8.5f);
        csap4.setPosition((float)21.98,(float)7);
        createRectangle((float)4.6, (float)11.5, (float)0.1, 7, 0* MathUtils.degreesToRadians);
        createRectangle((float)7.8, (float)11.5, (float)0.1, 7, 0* MathUtils.degreesToRadians);

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


        kacsa = new OneSpriteStaticActor(Assets.manager.get(Assets.KACSA));
        kacsa.setSize(kacsa.getWidth() / 300, kacsa.getHeight() / 300);
        kacsa.setVisible(false);

        var1 = new MyLabel(csapv1+"", game.getLabelStyle());
        var1.setPosition(4.6f,-10);
        var1.setFontScale(0.06f);
        var2 = new MyLabel(csapv2+"", game.getLabelStyle());
        var2.setPosition(11.66f,-10);
        var2.setFontScale(0.06f);
        var3 = new MyLabel(csapv3+"", game.getLabelStyle());
        var3.setPosition(18.8f,-10);
        var3.setFontScale(0.06f);
        var4 = new MyLabel(csapv4+"", game.getLabelStyle());
        var4.setPosition(24.8f,-10);
        var4.setFontScale(0.06f);
        varfo = new MyLabel(fo+"", game.getLabelStyle());
        varfo.setPosition(15.4f,31);
        varfo.setFontScale(0.1f);

        plus1 = new OneSpriteStaticActor(Assets.manager.get(Assets.PLUS));
        plus1.setSize(2,2);
        plus1.setPosition(5f,4.5f);
        plus1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                csapv1++;
                var1.setText(""+csapv1);
            }
        });
        addActor(plus1);
        minus1 = new OneSpriteStaticActor(Assets.manager.get(Assets.MINUS));
        minus1.setSize(2,2);
        minus1.setPosition(5f,1.5f);
        minus1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if(csapv1>0)csapv1--;
                var1.setText(csapv1+"");
            }
        });
        addActor(minus1);

        plus2 = new OneSpriteStaticActor(Assets.manager.get(Assets.PLUS));
        plus2.setSize(2,2);
        plus2.setPosition(12f,4.5f);
        plus2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                csapv2++;
                var2.setText(""+csapv2);
            }
        });
        addActor(plus2);
        minus2 = new OneSpriteStaticActor(Assets.manager.get(Assets.MINUS));
        minus2.setSize(2,2);
        minus2.setPosition(12f,1.5f);
        minus2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if(csapv2>0)csapv2--;
                var2.setText(csapv2+"");
            }
        });
        addActor(minus2);

        plus3 = new OneSpriteStaticActor(Assets.manager.get(Assets.PLUS));
        plus3.setSize(2,2);
        plus3.setPosition(19f,4.5f);
        plus3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                csapv3++;
                var3.setText(""+csapv3);
            }
        });
        addActor(plus3);
        minus3 = new OneSpriteStaticActor(Assets.manager.get(Assets.MINUS));
        minus3.setSize(2,2);
        minus3.setPosition(19f,1.5f);
        minus3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if(csapv3>0)csapv3--;
                var3.setText(csapv3+"");
            }
        });
        addActor(minus3);

        plus4 = new OneSpriteStaticActor(Assets.manager.get(Assets.PLUS));
        plus4.setSize(2,2);
        plus4.setPosition(26f,4.5f);
        plus4.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                csapv4++;
                var4.setText(""+csapv4);
            }
        });
        addActor(plus4);
        minus4 = new OneSpriteStaticActor(Assets.manager.get(Assets.MINUS));
        minus4.setSize(2,2);
        minus4.setPosition(26f,1.5f);
        minus4.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if(csapv4>0)csapv4--;
                var4.setText(csapv4+"");
            }
        });
        addActor(minus4);

        nagyplus = new OneSpriteStaticActor(Assets.manager.get(Assets.PLUS));
        nagyplus.setSize(2,2);
        nagyplus.setPosition(20,52);
        nagyplus.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                fo++;
                varfo.setText(""+fo);
            }
        });
        addActor(nagyplus);
        nagyminus = new OneSpriteStaticActor(Assets.manager.get(Assets.MINUS));
        nagyminus.setSize(2,2);
        nagyminus.setPosition(10,52);
        nagyminus.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if(fo>0)fo--;
                varfo.setText(fo+"");
            }
        });
        addActor(nagyminus);


        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (seton)spawnKacsa(x, y);
                else{
                    seton = true;
                    mWorld.destroyBody(fedel);
                }
            }
        });
for (int i = 0; i < 40; i++)
        createParticles(getViewport().getWorldWidth()/2, getViewport().getWorldHeight()/2);


        fedel = createRectangle(16, 45, 30,(float)0.1, 0* MathUtils.degreesToRadians);
        bodyk.add(fedel);
    }

    public void spawnKacsa(float x, float y) {
        if (kacsaSpawned == true) return;

        kacsaSpawned = true;
        kacsaphsy = createCircleBody(x, y, 2f);
        kacsa.setVisible(true);
    }

    public void destroyKacsa() {
        if(kacsaSpawned == false) return;

        kacsaSpawned = false;
        kacsa.setVisible(false);
        mWorld.destroyBody(kacsaphsy);
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


        shape.dispose();

        return body;
    }

    public Body createCircleBody(float pX, float pY, float pRadius) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(pX, pY);
        Body body = mWorld.createBody(bodyDef);

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

        getBatch().begin();
        csap1.draw(getBatch(),1);
        csap2.draw(getBatch(),1);
        csap3.draw(getBatch(),1);
        csap4.draw(getBatch(),1);
        var1.draw(getBatch(),1);
        var2.draw(getBatch(),1);
        var3.draw(getBatch(),1);
        var4.draw(getBatch(),1);
        varfo.draw(getBatch(),1);


        if(kacsaSpawned) {
            kacsa.setPosition(kacsaphsy.getPosition().x - kacsa.getWidth() / 2, kacsaphsy.getPosition().y - kacsa.getHeight() / 2);
            kacsa.draw(getBatch(), 1);
        }
        getBatch().end();
    }
    int counter = 0;
    public void act(float delta) {
        super.act(delta);
        if(seton){counter++;
        if(seton)counter++;
            if (counter % MathUtils.random(8, 100) == 0)
                createParticles(getViewport().getWorldWidth() / 2, getViewport().getWorldHeight() + 10);
        if(counter > 800){
            System.out.println("asd");
        }}
    }

    @Override
    public void init() {

    }
}
