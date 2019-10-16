package hu.csanyzeg.master.MyBaseClasses.Scene2D;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by tuskeb on 2016. 09. 30..
 */
public class OneSpriteStaticActor extends OneSpriteActor {

    public OneSpriteStaticActor(String file) {
        super(new Sprite(new Texture(file)));
    }

    public OneSpriteStaticActor(Texture texture) {
        super(new Sprite(texture));
    }

    public Texture getTexture()
    {
        return sprite.getTexture();
    }

}
