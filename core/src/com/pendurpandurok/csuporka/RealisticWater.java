package com.pendurpandurok.csuporka;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class RealisticWater {
    private Batch batch;
    private Camera camera;
    private float viewportW;
    private float viewportH;

    ShaderProgram blurShader;
    ShaderProgram alphaTresholdShader;
    FrameBuffer blurTargetA, blurTargetB;
    TextureRegion fboRegion;

    public static final int FBO_SIZE = 30;//=1024;

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

    public RealisticWater(Batch batch, Camera camera, float viewportW, float viewportH) {
        this.batch = batch;
        this.camera = camera;

        this.viewportW = viewportW;
        this.viewportH = viewportH;

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
        blurShader.setUniformf("resolution", viewportW);
        blurShader.setUniformf("radius", 1f);
        blurShader.end();

        alphaTresholdShader = new ShaderProgram(alphaVERT, alphaFRAG);
        if (!alphaTresholdShader.isCompiled()) {
            System.err.println(alphaTresholdShader.getLog());
            System.exit(0);
        }
        if (alphaTresholdShader.getLog().length()!=0)
            System.out.println(alphaTresholdShader.getLog());


        blurTargetA = new FrameBuffer(Pixmap.Format.RGBA8888, (int)viewportW, (int)viewportH, false);
        blurTargetB = new FrameBuffer(Pixmap.Format.RGBA8888, (int)viewportW, (int)viewportH, false);
        fboRegion = new TextureRegion(blurTargetA.getColorBufferTexture());
        fboRegion.flip(false, true);
    }

    void resizeBatch(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        batch.setProjectionMatrix(camera.combined);
    }

    public void startRender() {
        blurTargetA.begin();

        //Clear the offscreen buffer with an opaque background
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //before rendering, ensure we are using the default shader
        batch.setShader(null);

        resizeBatch((int)viewportW, (int)viewportH);
        //resizeBatch(100, 100);

        //now we can start drawing...
        batch.begin();
    }

    public void stopRender() {
        batch.flush();
        blurTargetA.end();

        //now let's start blurring the offscreen image
        batch.setShader(blurShader);

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
        batch.draw(fboRegion, 0, 0);
        batch.flush();

        //finish rendering target B
        blurTargetB.end();

        resizeBatch((int)viewportW, (int)viewportH);
        //resizeBatch(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        blurShader.setUniformf("dir", 0f, 1f);

        float mouseYAmt = Gdx.input.getY() / (float)Gdx.graphics.getHeight();
        //blurShader.setUniformf("radius", mouseYAmt * MAX_BLUR);
        blurShader.setUniformf("radius", MAX_BLUR - 0.11f);

        fboRegion.setTexture(blurTargetB.getColorBufferTexture());

        batch.setShader(alphaTresholdShader);
        batch.draw(fboRegion, 0, 0);
        batch.setShader(null);

        batch.end();
    }


}
