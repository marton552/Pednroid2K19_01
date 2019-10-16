package hu.csanyzeg.master.MyBaseClasses.UI;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import hu.csanyzeg.master.MyBaseClasses.Game.InitableInterface;


/**
 * Created by tuskeb on 2016. 10. 01..
 */
public class MyLabel extends Label implements InitableInterface {


    public MyLabel(CharSequence text, LabelStyle style) {
        super(text, style);
        init();
    }

    @Override
    public void init() {

    }

    protected float elapsedtime =0;

    @Override
    public void act(float delta) {
        super.act(delta);
        elapsedtime += delta;

        //setFontScale(Math.abs((float)Math.sin(elapsedtime*2f))/2f+0.8f);
    }
}
