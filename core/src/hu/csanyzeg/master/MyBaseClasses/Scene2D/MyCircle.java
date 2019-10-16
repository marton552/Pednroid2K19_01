package hu.csanyzeg.master.MyBaseClasses.Scene2D;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by tanulo on 2017. 12. 13..
 */

public class MyCircle extends MyShape {
    protected float radius = 0;
    public static int debugLineNumbers = 16;





    /**
     * @param radius A kör sugara
     */
    public MyCircle(float radius) {
        super(0, 0, radius, radius, 0, 0, 0, 0, 0, 0, true);
        setRadius(radius);
    }


    /**
     * @param offsetX Eltolás az X koordinátától
     * @param offsetY Eltolás az Y koordinátától
     * @param radius A kör sugara
     */
    public MyCircle(float radius, float offsetX, float offsetY) {
        super(0, 0, radius*2, radius*2, 0, 0,0, 0, offsetX, offsetY, true);
        setRadius(radius);
    }


    /**
     * @param originX A forgatás középpontja
     * @param originY A forgatás középpontja
     * @param offsetX Eltolás az X koordinátától
     * @param offsetY Eltolás az Y koordinátától
     */
    public MyCircle(float radius, float offsetX, float offsetY, float originX, float originY) {
        super(0, 0, radius*2, radius*2, 0, 0, originX, originY, offsetX, offsetY, true);
        setRadius(radius);
    }

    /**
     * @param x Az alakzat helye
     * @param y Az alakzat helye
     * @param originX A forgatás középpontja
     * @param originY A forgatás középpontja
     * @param offsetX Eltolás az X koordinátától
     * @param offsetY Eltolás az Y koordinátától
     * @param radius A kör sugara
     */
    public MyCircle(float radius, float offsetX, float offsetY, float originX, float originY, float x, float y) {
        super(x, y, radius*2, radius*2, 0, 0, originX, originY, offsetX, offsetY, true);
        setRadius(radius);
    }


    /**
     * @param alignToLeftBottom Igaz esetén az alakzatot a bal alsó sarkától számított X és Y koordinátákkal hozza létre, ellenkező esetben a küzepétől.
     * @param radius A kör sugara
     */
    public MyCircle(float radius,  boolean alignToLeftBottom) {
        super(0, 0, radius*2, radius*2, 0, 0, 0, 0, 0, 0, alignToLeftBottom);
        setRadius(radius);
    }
    /**
     * @param offsetX Eltolás az X koordinátától
     * @param offsetY Eltolás az Y koordinátától
     * @param alignToLeftBottom Igaz esetén az alakzatot a bal alsó sarkától számított X és Y koordinátákkal hozza létre, ellenkező esetben a küzepétől.
     * @param radius A kör sugara
     */
    public MyCircle( float radius, float offsetX, float offsetY, boolean alignToLeftBottom) {
        super(0, 0, radius*2, radius*2, 0, 0,0, 0, offsetX, offsetY, alignToLeftBottom);
        setRadius(radius);
    }

    /**
     * @param originX A forgatás középpontja
     * @param originY A forgatás középpontja
     * @param offsetX Eltolás az X koordinátától
     * @param offsetY Eltolás az Y koordinátától
     * @param alignToLeftBottom Igaz esetén az alakzatot a bal alsó sarkától számított X és Y koordinátákkal hozza létre, ellenkező esetben a küzepétől.
     * @param radius A kör sugara
     */
    public MyCircle( float radius, float offsetX, float offsetY, float originX, float originY,  boolean alignToLeftBottom) {
        super(0, 0, radius*2, radius*2, 0, 0, originX, originY, offsetX, offsetY, alignToLeftBottom);
        setRadius(radius);
    }

    /**
     * @param x Az alakzat helye
     * @param y Az alakzat helye
     * @param originX A forgatás középpontja
     * @param originY A forgatás középpontja
     * @param offsetX Eltolás az X koordinátától
     * @param offsetY Eltolás az Y koordinátától
     * @param alignToLeftBottom Igaz esetén az alakzatot a bal alsó sarkától számított X és Y koordinátákkal hozza létre, ellenkező esetben a küzepétől.
     * @param radius A kör sugara
     */
    public MyCircle(float radius, float offsetX, float offsetY, float originX, float originY, float x, float y,  boolean alignToLeftBottom) {
        super(x, y, radius*2, radius*2, 0, 0, originX, originY, offsetX, offsetY, alignToLeftBottom);
        setRadius(radius);
    }



    @Override
    public Vector2[] getCorners() {
        Vector2[] vector2 = new Vector2[debugLineNumbers];
        for(int i=0; i<debugLineNumbers;i++){
            vector2[i] = new Vector2(radius, 0);
            vector2[i].rotate(360.0f/debugLineNumbers*i+rotation);
            vector2[i].add(realCenterX, realCenterY);
        }
        return vector2;
    }


    public static boolean overlaps(MyCircle objA, MyCircle objB) {
        return (objA.realCenterX - objB.realCenterX) * (objA.realCenterX - objB.realCenterX) +
                (objA.realCenterY - objB.realCenterY) * (objA.realCenterY - objB.realCenterY) <=
                (objA.radius + objB.radius) * (objA.radius + objB.radius);
    }

    public static boolean overlaps(MyCircle objA, MyRectangle objB){
        return MyRectangle.overlaps(objB, objA);
    }

    @Override
    public boolean overlaps(MyShape other) {
        if (other instanceof MyCircle) {
            return overlaps(this, (MyCircle)other);
        }
        if (other instanceof MyRectangle){
            return MyRectangle.overlaps((MyRectangle)other, this);
        }
        return false;
    }

    @Override
    public void setSize(float width, float height) {
        radius = (float)Math.sqrt(width*height)/2;
        setRadius(radius);

    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
        float rx2=radius*2;
        this.centerX -= (this.width - rx2) / 2f;
        this.centerY -= (this.height - rx2) / 2f;
        this.width = rx2;
        this.height = rx2;
        calculateCenterXY();
    }

    public void setRadiusByCenter(float radius) {
        this.radius = radius;
        this.width = radius*2;
        this.height = radius*2;
        calculateCenterXY();
    }
}
