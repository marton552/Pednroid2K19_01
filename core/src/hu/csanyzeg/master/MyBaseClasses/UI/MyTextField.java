package hu.csanyzeg.master.MyBaseClasses.UI;

import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import hu.csanyzeg.master.MyBaseClasses.Game.InitableInterface;

/**
 * Created by tuskeb on 2016. 10. 01..
 */
public class MyTextField extends TextField implements InitableInterface {

    public MyTextField(String text, TextFieldStyle style) {
        super(text, style);
        setWidth(300);
        setHeight(50);
        init();
    }

    @Override
    public void init() {

    }
}
