package com.poolbets.application;

import com.badlogic.gdx.Game;
import com.poolbets.application.screens.MainMenuScreen;

/**
 * Created by Mashenkin Roman on 07.07.16.
 */
public class PoolBetsApp extends Game {
	
	@Override
	public void create () {

		setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {

		super.render();
	}
	
	@Override
	public void dispose () {

		super.dispose();
	}
}
