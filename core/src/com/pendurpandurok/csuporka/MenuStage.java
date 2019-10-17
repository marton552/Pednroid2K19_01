package com.pendurpandurok.csuporka;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
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

public class MenuStage extends MyStage {


    //http://www.patrickmatte.com/stuff/physicsLiquid/
    //https://github.com/mattdesl/lwjgl-basics/wiki/ShaderLesson5#Ports - Blur
    //https://github.com/libgdx/libgdx/wiki/Distance-field-fonts -- Alpha treshold
    private World mWorld;
    private ParticleSystem mParticleSystem;
    private ColorParticleRenderer mParticleDebugRenderer;
    private Box2DDebugRenderer mDebugRenderer;

    private ParticleGroupDef mParticleGroupDef1;
    private ParticleGroupDef mParticleGroupDef2;

    private final static float BOX_TO_WORLD = 1f;
    private final static float WORLD_TO_BOX = 1f / BOX_TO_WORLD;

    //Blur
    ShaderProgram blurShader;
    ShaderProgram alphaTresholdShader;
    FrameBuffer blurTargetA, blurTargetB;
    TextureRegion fboRegion;

    public static final int FBO_SIZE = 60;//1024;

    public static final float MAX_BLUR = 0.22f;

    final String VERT =
            "attribute vec4 "+ ShaderProgram.POSITION_ATTRIBUTE+";\n" +
                    "attribute vec4 "+ShaderProgram.COLOR_ATTRIBUTE+";\n" +
                    "attribute vec2 "+ShaderProgram.TEXCOORD_ATTRIBUTE+"0;\n" +

                    "uniform mat4 u_projTrans;\n" +
                    " \n" +
                    "varying vec4 vColor;\n" +
                    "varying vec2 vTexCoord;\n" +

                    "void main() {\n" +
                    "	vColor = "+ShaderProgram.COLOR_ATTRIBUTE+";\n" +
                    "	vTexCoord = "+ShaderProgram.TEXCOORD_ATTRIBUTE+"0;\n" +
                    "	gl_Position =  u_projTrans * " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" +
                    "}";

    final String FRAG =
            "#ifdef GL_ES\n" +
                    "#define LOWP lowp\n" +
                    "precision mediump float;\n" +
                    "#else\n" +
                    "#define LOWP \n" +
                    "#endif\n" +
                    "varying LOWP vec4 vColor;\n" +
                    "varying vec2 vTexCoord;\n" +
                    "\n" +
                    "uniform sampler2D u_texture;\n" +
                    "uniform float resolution;\n" +
                    "uniform float radius;\n" +
                    "uniform vec2 dir;\n" +
                    "const float smoothing = 1.0/16.0;\n" +
                    "void main() {\n" +
                    "	vec4 sum = vec4(0.0);\n" +
                    "	vec2 tc = vTexCoord;\n" +
                    "	float blur = radius/resolution; \n" +
                    "    \n" +
                    "    float hstep = dir.x;\n" +
                    "    float vstep = dir.y;\n" +
                    "    float gate = 0.2;\n" +
                    "	sum += texture2D(u_texture, vec2(tc.x - 4.0*blur*hstep, tc.y - 4.0*blur*vstep)) * 0.05;\n" +
                    "	sum += texture2D(u_texture, vec2(tc.x - 3.0*blur*hstep, tc.y - 3.0*blur*vstep)) * 0.09;\n" +
                    "	sum += texture2D(u_texture, vec2(tc.x - 2.0*blur*hstep, tc.y - 2.0*blur*vstep)) * 0.12;\n" +
                    "	sum += texture2D(u_texture, vec2(tc.x - 1.0*blur*hstep, tc.y - 1.0*blur*vstep)) * 0.15;\n" +
                    "	\n" +
                    "	sum += texture2D(u_texture, vec2(tc.x, tc.y)) * 0.16;\n" +
                    "	\n" +
                    "	sum += texture2D(u_texture, vec2(tc.x + 1.0*blur*hstep, tc.y + 1.0*blur*vstep)) * 0.15;\n" +
                    "	sum += texture2D(u_texture, vec2(tc.x + 2.0*blur*hstep, tc.y + 2.0*blur*vstep)) * 0.12;\n" +
                    "	sum += texture2D(u_texture, vec2(tc.x + 3.0*blur*hstep, tc.y + 3.0*blur*vstep)) * 0.09;\n" +
                    "	sum += texture2D(u_texture, vec2(tc.x + 4.0*blur*hstep, tc.y + 4.0*blur*vstep)) * 0.05;\n" +
                    "   \n" +
                    "   \n"+
                    "   \n" +
                    "   " +
                    "   //vec4 t = texture2D(u_texture, tc);\n" +
                    "//float c = step( 0.3,t.r);\n" +
                    "gl_FragColor = vec4(sum.rgb,1.0);\n"+
                    "   "+
                    "}";

    final String alphaVERT =
                    "attribute vec4 "+ ShaderProgram.POSITION_ATTRIBUTE+";\n" +
                    "attribute vec4 "+ShaderProgram.COLOR_ATTRIBUTE+";\n" +
                    "attribute vec2 "+ShaderProgram.TEXCOORD_ATTRIBUTE+"0;\n" +

                    "uniform mat4 u_projTrans;\n" +
                    " \n" +
                    "varying vec4 vColor;\n" +
                    "varying vec2 vTexCoord;\n" +

                    "void main() {\n" +
                    "	vColor = "+ShaderProgram.COLOR_ATTRIBUTE+";\n" +
                    "	vTexCoord = "+ShaderProgram.TEXCOORD_ATTRIBUTE+"0;\n" +
                    "	gl_Position =  u_projTrans * " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" +
                    "}";
    final String alphaFRAG = "#ifdef GL_ES\n" +
            "#define LOWP lowp\n" +
            "#define MED mediump\n" +
            "precision mediump float;\n" +
            "#else\n" +
            "#define LOWP \n" +
            "#define MED\n" +
            "#endif\n" +
            "uniform MED sampler2D u_texture0;\n" +
            "varying vec2 vTexCoord;\n" +
            "void main(){\n" +
            "   vec4 t = texture2D(u_texture0, vTexCoord);\n" +
            "   float c = step(0.55,t.r);\n" +
            "   gl_FragColor = vec4(t.r, 0, 0, c);\n"+
            "}";

    private Sprite sprite;
    OneSpriteStaticActor actor;

    public MenuStage(Batch batch, MyGame game) {
        super(new ExtendViewport(1920f / 32, 1080f / 32), batch, game);

        actor = new OneSpriteStaticActor(Assets.manager.get(Assets.BADLOGIC_TEXTURE));
        actor.setSize(5, 5);
        //addActor(actor);


        mWorld = new World(new Vector2(0, -9.2f), false);
        float width = getViewport().getWorldWidth();
        float height = getViewport().getWorldHeight();

        /*sprite = new Sprite(region);
        sprite.setSize(width, height);
        sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
        sprite.setPosition(0, 0);*/

        createBox2DWorld(width, height);
        createParticleStuff(width, height);


        //for (int i = 0; i < 100; i++) createParticles1(width * (30f / 100f) * WORLD_TO_BOX + i * 3, height * (80f / 100f) * WORLD_TO_BOX);


        mDebugRenderer = new Box2DDebugRenderer();
        mParticleDebugRenderer = new ColorParticleRenderer(1000000);
        mParticleDebugRenderer.setMaxParticleNumber(mParticleSystem.getParticleCount() + 10000);

        ///////////////////////
        //Create blur things //

        ShaderProgram.pedantic = false;

        blurShader = new ShaderProgram(VERT, FRAG);
        if (!blurShader.isCompiled()) {
            System.err.println(blurShader.getLog());
            System.exit(0);
        }
        if (blurShader.getLog().length()!=0)
            System.out.println(blurShader.getLog());


        //setup uniforms for our shader
        blurShader.begin();
        blurShader.setUniformf("dir", 0f, 0f);
        blurShader.setUniformf("resolution", FBO_SIZE);
        blurShader.setUniformf("radius", 1f);
        blurShader.end();

        alphaTresholdShader = new ShaderProgram(alphaVERT, alphaFRAG);
        if (!alphaTresholdShader.isCompiled()) {
            System.err.println(alphaTresholdShader.getLog());
            System.exit(0);
        }
        if (alphaTresholdShader.getLog().length()!=0)
            System.out.println(alphaTresholdShader.getLog());


        blurTargetA = new FrameBuffer(Pixmap.Format.RGBA8888, FBO_SIZE, FBO_SIZE, false);
        blurTargetB = new FrameBuffer(Pixmap.Format.RGBA8888, FBO_SIZE, FBO_SIZE, false);
        fboRegion = new TextureRegion(blurTargetA.getColorBufferTexture());
        fboRegion.flip(false, true);


    }


    private void createBox2DWorld(float width, float height) {
        mWorld = new World(new Vector2(0, -9.8f), false);

        /* Bottom */
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

        /* Walls */
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
    }

    private void createParticleStuff(float width, float height) {
        //First we create a new particlesystem and
        //set the radius of each particle to 6 / 120 m (5 cm)
        ParticleSystemDef systemDef = new ParticleSystemDef();
        systemDef.radius = 0.08f * WORLD_TO_BOX;
        systemDef.dampingStrength = 0.6f;
        systemDef.gravityScale = 1f;

        mParticleSystem = new ParticleSystem(mWorld, systemDef);
        mParticleSystem.setParticleDensity(1000f);

        //Create a new particlegroupdefinition and set some properties
        //For the flags you can set more than only one
        mParticleGroupDef1 = new ParticleGroupDef();
        mParticleGroupDef1.color.set(1f, 0, 0, 1);
        mParticleGroupDef1.flags.add(ParticleDef.ParticleType.b2_waterParticle);
        mParticleGroupDef1.position.set(width * (30f / 100f) * WORLD_TO_BOX, height * (80f / 100f) * WORLD_TO_BOX);

        //Create a shape, give it to the definition and
        //create the particlegroup in the particlesystem.
        //This will return you a ParticleGroup instance, but
        //we don't need it here, so we drop that.
        //The shape defines where the particles are created exactly
        //and how much are created
        PolygonShape parShape = new PolygonShape();
        parShape.setAsBox(width * (20f / 100f) * WORLD_TO_BOX / 2f, width * (20f / 100f) * WORLD_TO_BOX / 2f);
        //parShape.setAsBox(10, 10);
        //parShape.setRadius(1f);
        mParticleGroupDef1.shape = parShape;
        mParticleSystem.createParticleGroup(mParticleGroupDef1);

        //Exactly the same! This is the second group with a different
        //color and shifted on the x-Axis
        mParticleGroupDef2 = new ParticleGroupDef();
        mParticleGroupDef2.shape = mParticleGroupDef1.shape;
        mParticleGroupDef2.flags = mParticleGroupDef1.flags;
        mParticleGroupDef2.groupFlags = mParticleGroupDef1.groupFlags;
        mParticleGroupDef2.position.set(width * (70f / 100f) * WORLD_TO_BOX, height * (80f / 100f) * WORLD_TO_BOX);
        mParticleGroupDef2.color.set(1f, 0f, 0f, 1);
        mParticleSystem.createParticleGroup(mParticleGroupDef2);

        //Here we create a new shape and we set a
        //linear velocity. This is used in createParticles1()
        //and createParticles2()
        CircleShape partShape = new CircleShape();
        partShape.setRadius(0.1f);

        mParticleGroupDef1.shape = partShape;
        mParticleGroupDef2.shape = partShape;

        mParticleGroupDef1.linearVelocity.set(new Vector2(0, -10f));
        mParticleGroupDef2.linearVelocity.set(new Vector2(0, -10f));
    }

    public void createParticles1(float pX, float pY) {
        mParticleGroupDef1.position.set(pX * WORLD_TO_BOX, pY * WORLD_TO_BOX);
        mParticleSystem.createParticleGroup(mParticleGroupDef1);
        //updateParticleCount();
    }

    private void createParticles2(float pX, float pY) {
        mParticleGroupDef2.position.set(pX * WORLD_TO_BOX, pY * WORLD_TO_BOX);
        mParticleSystem.createParticleGroup(mParticleGroupDef2);
        updateParticleCount();
    }

    private void updateParticleCount() {
        if(mParticleSystem.getParticleCount() > mParticleDebugRenderer.getMaxParticleNumber()) {
            mParticleDebugRenderer.setMaxParticleNumber(mParticleSystem.getParticleCount() + 1000);
        }
    }

    void resizeBatch(int width, int height) {
        getCamera().viewportWidth = width;
        getCamera().viewportHeight = height;
        getBatch().setProjectionMatrix(getCamera().combined);
    }

    @Override
    public void draw() {
        mWorld.step(Gdx.graphics.getDeltaTime(), 10, 6, mParticleSystem.calculateReasonableParticleIterations(Gdx.graphics.getDeltaTime()));

        super.draw();

        blurTargetA.begin();

        //Clear the offscreen buffer with an opaque background
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //before rendering, ensure we are using the default shader
        getBatch().setShader(null);

        resizeBatch(FBO_SIZE, FBO_SIZE);
        //resizeBatch(100, 100);


        //now we can start drawing...
        getBatch().begin();

        //draw our scene here
        //actor.draw(getBatch(), 1f);
        mParticleDebugRenderer.render(mParticleSystem, BOX_TO_WORLD, getCamera().combined);
        //mDebugRenderer.render(mWorld, getCamera().combined);


        getBatch().flush();
        blurTargetA.end();

        //now let's start blurring the offscreen image
        getBatch().setShader(blurShader);

        blurShader.setUniformf("dir", 1f, 0f);
        float mouseXAmt = Gdx.input.getX() / (float)Gdx.graphics.getWidth();
        //blurShader.setUniformf("radius", mouseXAmt * MAX_BLUR);
        blurShader.setUniformf("radius",  MAX_BLUR);
        //System.out.println("x: "+(mouseXAmt * MAX_BLUR));


        //our first blur pass goes to target B
        blurTargetB.begin();

        //we want to render FBO target A into target B
        fboRegion.setTexture(blurTargetA.getColorBufferTexture());

        //draw the scene to target B with a horizontal blur effect
        getBatch().draw(fboRegion, 0, 0);
        getBatch().flush();

        //finish rendering target B
        blurTargetB.end();

        resizeBatch(1920 / 32, 1080 / 32);
        //resizeBatch(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        blurShader.setUniformf("dir", 0f, 1f);

        float mouseYAmt = Gdx.input.getY() / (float)Gdx.graphics.getHeight();
        //blurShader.setUniformf("radius", mouseYAmt * MAX_BLUR);
        blurShader.setUniformf("radius", MAX_BLUR - 0.11f);

        fboRegion.setTexture(blurTargetB.getColorBufferTexture());

        getBatch().setShader(alphaTresholdShader);
        getBatch().draw(fboRegion, 0, 0);
        getBatch().setShader(null);

        getBatch().end();

        //mParticleDebugRenderer.render(mParticleSystem, BOX_TO_WORLD, getCamera().combined);

        //render box2d
        mDebugRenderer.render(mWorld, getCamera().combined);


    }

    @Override
    public void init() {

    }
}
