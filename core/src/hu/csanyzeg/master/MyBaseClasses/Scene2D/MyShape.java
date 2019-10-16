package hu.csanyzeg.master.MyBaseClasses.Scene2D;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by tanulo on 2017. 12. 13..
 */

public abstract class MyShape {

    /**
     * Tényleges középpont. Ez alapján számolja a pozícióját. center=cx+offsetx forgatva origin körül
     */
    protected float realCenterX = 0;

    /**
     * Tényleges középpont. Ez alapján számolja a pozícióját. center=cx+offsetx forgatva origin körül
     */
    protected float realCenterY = 0;

    /**
     * Tényleges forgatás. A offsetRotation forgatás és az elforgatás összege.
     */
    protected float realRotation = 0;


    /**
     * Szélesség. Forgatásnál nem változik.
     */
    protected float width = 0;

    /**
     * Magasság. Forgatásnál nem változik.
     */
    protected float height = 0;

    /**
     * Forgatás fokban megadva.
     */
    protected float rotation = 0;

    /**
     * Relatív elforgatás. realRotation=offsetRotation+rotation
     */
    protected float offsetRotation = 0;

    /**
     * Relatív eltolás cx-től számítva. center=cx+offsetx
     */
    protected float offsetX = 0;

    /**
     * Relatív eltolás cy-tól számítva. center=cy+offsety
     */
    protected float offsetY=0;

    /**
     * A középpont abszolút pozíciója a játéktérben.
     */
    protected float centerX =0;

    /**
     * A középpont abszolút pozíciója a játéktérben.
     */
    protected float centerY =0;

    /**
     * A forgatás középpontja. Relatív a valódi helyétől (offsetxy+cxy) az alakzatnak.
     */
    protected float originX = 0;

    /**
     * A forgatás középpontja. Relatív a valódi helyétől (offsetxy+cxy) az alakzatnak.
     */
    protected float originY = 0;

    static protected float PI = (float) Math.PI;

    public boolean active = true;

    /**
     * Az alakzathoz hozzácsatolható objektum, például egy Actor
     */
    public Object extraData = null;

    abstract public Vector2[] getCorners();
    abstract public boolean overlaps(MyShape other);

    /**
     * @param x Az alakzat helye
     * @param y Az alakzat helye
     * @param width  Az alakzat szélessége
     * @param height Az alakzat magassága
     * @param rotation Az alakzat forgatása az origin körül
     * @param offsetRotation Az alakzat forgatása saját maga körül
     * @param originX A forgatás középpontja
     * @param originY A forgatás középpontja
     * @param offsetX Eltolás az X koordinátától
     * @param offsetY Eltolás az Y koordinátától
     * @param alignToLeftBottom Igaz esetén az alakzatot a bal alsó sarkától számított X és Y koordinátákkal hozza létre, ellenkező esetben a küzepétől.
     */
    public MyShape(float x, float y, float width, float height, float rotation,  float offsetRotation, float originX, float originY, float offsetX, float offsetY, boolean alignToLeftBottom) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.width = width;
        this.height = height;
        this.offsetRotation = offsetRotation;
        this.rotation = rotation;
        if (alignToLeftBottom){
            setPosition(x,y);
            setOrigin(originX,originY);
        }else {
            setPositionFromCenter(x,y);
            setOriginFromCenter(originX,originY);
        }
    }


    public void setSizeByCenter(float width, float height) {
        this.width = width;
        this.height = height;
        calculateCenterXY();
    }

    public void setSize(float width, float height) {
        this.centerX -= (this.width - width) / 2f;
        this.centerY -= (this.height - height) / 2f;
        this.width = width;
        this.height = height;
        calculateCenterXY();
    }

    protected void calculateCenterXY(){
        realRotation = rotation + offsetRotation;
        Vector2 origCenter = new Vector2(centerX + offsetX, centerY + offsetY);
        Vector2 origin =  new Vector2(originX + centerX + offsetX,originY + centerY + offsetY);
        Vector2 v = origCenter.sub(origin);
        v.rotate(rotation);
        Vector2 s = v.add(origin);
        //this.realCenterX = centerX + offsetX;
        //this.realCenterY = centerY + offsetY;
        this.realCenterX = s.x;
        this.realCenterY = s.y;
        //System.out.println(this.toString());
    }

    public void setPosition(float X, float Y) {
        this.centerX = X + width/2;
        this.centerY = Y + height/2;
        calculateCenterXY();
    }

    public void setX(float X) {
        this.centerX = X + width/2;
        //this.centerY = Y + height/2;
        calculateCenterXY();
    }

    public void setY(float Y) {
        //this.centerX = X + width/2;
        this.centerY = Y + height/2;
        calculateCenterXY();
    }


    public void setPositionFromCenter(float centerX, float centerY) {
        this.centerX = centerX;
        this.centerY = centerY;
        calculateCenterXY();
    }

    public void setOffset(float offsetX, float offsetY){
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        calculateCenterXY();
    }

    public void rotateBy(float degree) {
        rotation += degree;
        calculateCenterXY();
    }

    public void setRotation(float degree) {
        rotation = degree;
        calculateCenterXY();
    }

    public float getRealCenterX() {
        return realCenterX;
    }

    public float getRealCenterY() {
        return realCenterY;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getRotation() {
        return rotation;
    }

    public float getOffsetX() {
        return offsetX;
    }

    public float getOffsetY() {
        return offsetY;
    }

    public float getCenterX() {
        return centerX;
    }

    public float getCenterY() {
        return centerY;
    }

    /**
     * A bal alsó sarok abszolút pozíciója a játéktérben, eltolással (offsetXY), forgatással együtt
     * @return
     */
    public float getX() {
        return realCenterX - width/2;
    }

    /**
     * A bal alsó sarok abszolút pozíciója a játéktérben, eltolással (offsetXY), forgatással együtt
     * @return
     */
    public float getY() {
        return realCenterY - height/2;
    }


    public void setOriginToCenter(){
        originX = 0;
        originY = 0;
        calculateCenterXY();
    }

    /**
     * Forgatási középpont beállítása a középponttól számítva
     * @param x
     * @param y
     */
    public void setOriginFromCenter(float x, float y){
        originX = x - offsetX;
        originY = y - offsetY;
        calculateCenterXY();
    }

    /**
     * Forgatási középpont beállítása a bal alsó saroktól mérve.
     * @param x
     * @param y
     */
    public void setOrigin(float x, float y){
        originX = x - width / 2 - offsetX;
        originY = y - height / 2 - offsetY;
        calculateCenterXY();
    }

    public float getOffsetRotation() {
        return offsetRotation;
    }

    public void setOffsetRotation(float offsetRotation) {
        this.offsetRotation = offsetRotation;
        calculateCenterXY();
    }

    public void offsetRotateBy(float degree) {
        this.offsetRotation += degree;
        calculateCenterXY();
    }

    public float getRealRotation() {
        return realRotation;
    }

    public float getOriginX() {
        return originX;
    }

    public void setOriginX(float originX) {
        this.originX = originX;
        calculateCenterXY();
    }

    public float getOriginY() {
        return originY;
    }

    public void setOriginY(float originY) {
        this.originY = originY;
        calculateCenterXY();
    }

    public void setWidth(float width) {
        this.width = width;
        calculateCenterXY();
    }

    public void setHeight(float height) {
        this.height = height;
        calculateCenterXY();
    }

    public void setOffsetX(float offsetX) {
        this.offsetX = offsetX;
        calculateCenterXY();
    }

    public void setOffsetY(float offsetY) {
        this.offsetY = offsetY;
        calculateCenterXY();
    }

    public void setCenterX(float centerX) {
        this.centerX = centerX;
        calculateCenterXY();
    }

    public void setCenterY(float centerY) {
        this.centerY = centerY;
        calculateCenterXY();
    }

    @Override
    public String toString() {
        Vector2[] vector2s = getCorners();
        String corners = "";
        if (vector2s != null) {
            int x = 1;
            for (Vector2 v : vector2s) {
                corners += "\n(X" + x + "=" + Math.round(v.x*100.0f)/100.0f + " Y" + x + "=" + Math.round(v.y*100.0f)/100.0f + ")";
            }
        }
        return "MyShape{" +
                "realCenterX=" + realCenterX +
                ", realCenterY=" + realCenterY +
                ", realRotation=" + realRotation +
                ", width=" + width +
                ", height=" + height +
                ", rotation=" + rotation +
                ", offsetRotation=" + offsetRotation +
                ", offsetX=" + offsetX +
                ", offsetY=" + offsetY +
                ", centerX=" + centerX +
                ", centerY=" + centerY +
                ", originX=" + originX +
                ", originY=" + originY +
                " getCorners {" + corners +
                "}}";
    }

    public Object getExtraData() {
        return extraData;
    }

    public void setExtraData(Object extraData) {
        this.extraData = extraData;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }


}
