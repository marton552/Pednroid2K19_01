package com.pendurpandurok.csuporka.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.pendurpandurok.csuporka.MyGdxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = 1280;
		config.width = 720;

		new LwjglApplication(new MyGdxGame(), config);
	}
}
