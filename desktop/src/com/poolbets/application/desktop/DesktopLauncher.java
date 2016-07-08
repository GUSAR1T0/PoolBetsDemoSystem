package com.poolbets.application.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.poolbets.application.PoolBetsApp;
import com.poolbets.application.additions.Constants;

public class DesktopLauncher {

	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Constants.WIDTH;
		config.height = Constants.HEIGHT;
		config.title = Constants.TITLE;
		config.resizable = false;

		new LwjglApplication(new PoolBetsApp(), config);
	}
}
