package com.poolbets.application.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.poolbets.application.PoolBetsApp;

import static com.poolbets.application.additions.Constants.*;

public class DesktopLauncher {

	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = WIDTH;
		config.height = HEIGHT;
		config.title = TITLE;
		config.resizable = false;

			config.addIcon(ICON_FOR_WINDOWS, Files.FileType.Internal);
			config.addIcon(ICON_FOR_UNIX, Files.FileType.Internal);
			config.addIcon(ICON_FOR_MAC, Files.FileType.Internal);

		new LwjglApplication(new PoolBetsApp(), config);
	}
}
