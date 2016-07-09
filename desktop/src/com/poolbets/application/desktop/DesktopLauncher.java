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

		if (isWindows()) config.addIcon(ICON_FOR_WINDOWS, Files.FileType.Internal);
		else if (isUnix()) config.addIcon(ICON_FOR_UNIX, Files.FileType.Internal);
		else if (isMac()) config.addIcon(ICON_FOR_MAC, Files.FileType.Internal);

		new LwjglApplication(new PoolBetsApp(), config);
	}

	public static boolean isWindows() {
		return System.getProperty("os.name").toLowerCase().contains("win");
	}

	public static boolean isUnix() {
		return (System.getProperty("os.name").toLowerCase().contains("nux") ||
				System.getProperty("os.name").toLowerCase().contains("nix"));
	}

	public static boolean isMac() {
		return System.getProperty("os.name").toLowerCase().contains("mac");
	}
}
