package com.poolbets.application;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.poolbets.application.models.Client;
import com.poolbets.application.screens.LoadingScreen;

import static com.poolbets.application.additions.Codes.CODE_CONNECTED;
import static com.poolbets.application.additions.Utils.getFontParameters;

/**
 * Created by Mashenkin Roman on 07.07.16.
 */
public class PoolBetsApp extends Game {

	private AssetManager manager;
	private Client client = null;

	public String getVersion() {
		return "1.0.1";
	}

	public AssetManager getManager() {
		return manager;
	}

	public void setManager() {
		manager = initManager();
	}

	private AssetManager initManager() {

		AssetManager manager = new AssetManager();

		FileHandleResolver resolver = new InternalFileHandleResolver();
		manager.setLoader(FreeTypeFontGenerator.class,
				new FreeTypeFontGeneratorLoader(resolver));
		manager.setLoader(BitmapFont.class, ".otf",
				new FreetypeFontLoader(resolver));
		manager.setLoader(BitmapFont.class, ".ttf",
				new FreetypeFontLoader(resolver));

		FreetypeFontLoader.FreeTypeFontLoaderParameter parameter;

		parameter = getFontParameters("BigOrange.otf", 80, 1.75f, "#bac0ce");
		manager.load("BigOrange_80_bac0ce.ttf", BitmapFont.class, parameter);

		parameter = getFontParameters("PFSquareSansPro-Regular.ttf", 30, 1f, "#bac0ce");
		manager.load("PFSSP_30_bac0ce.ttf", BitmapFont.class, parameter);

		parameter = getFontParameters("PFSquareSansPro-Regular.ttf", 40, 1f, "#bac0ce");
		manager.load("PFSSP_40_bac0ce.ttf", BitmapFont.class, parameter);

		parameter = getFontParameters("PFSquareSansPro-Regular.ttf", 40, 1f, "#61656e");
		manager.load("PFSSP_40_61656e.ttf", BitmapFont.class, parameter);

		parameter = getFontParameters("PFSquareSansPro-Regular.ttf", 60, 1f, "#bac0ce");
		manager.load("PFSSP_60_bac0ce.ttf", BitmapFont.class, parameter);

		parameter = getFontParameters("DINProBold.otf", 40, 1.5f, "#bac0ce");
		manager.load("DINPro_40_bac0ce.ttf", BitmapFont.class, parameter);

		parameter = getFontParameters("DINProBold.otf", 60, 1.75f, "#bac0ce");
		manager.load("DINPro_60_bac0ce.ttf", BitmapFont.class, parameter);

		return manager;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	@Override
	public void create () {

		setScreen(new LoadingScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {

		super.dispose();
		manager.dispose();

		if (client.getCode().equals(CODE_CONNECTED)) {
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					client.disconnect();
				}
			});
			thread.start();
		}
	}
}
