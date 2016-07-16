package com.poolbets.application.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.poolbets.application.PoolBetsApp;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.poolbets.application.additions.Constants.*;
import static com.poolbets.application.additions.Utils.getColorRGB;
import static com.poolbets.application.additions.Utils.setGLBackgroundColor;

/**
 * Created by Mashenkin Roman on 16.07.16.
 */
public class LoadingScreen implements Screen {

    private Color glBackgroundColor;
    private PoolBetsApp app;
    private Stage stage;
    private Texture icon;

    private float stateTime = 0;

    public LoadingScreen(PoolBetsApp app) {

        this.app = app;

        glBackgroundColor = getColorRGB("#2f2c30");

        stage = new Stage(new StretchViewport(WORLD_WIDTH,
                WORLD_WIDTH * RATIO));
        Gdx.input.setInputProcessor(stage);
        stage.getRoot().getColor().a = 0f;
        stage.addAction(fadeIn(2f));

        icon = new Texture(Gdx.files.internal(TEXTURE_ICON));
        icon.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        createScreen();
        app.setManager();
    }

    private void createScreen() {

        Image iconImage = new Image(icon);
        iconImage.setPosition(stage.getWidth() / 2f - iconImage.getWidth() / 2f,
                stage.getHeight() / 2f - iconImage.getHeight() / 2f);

        stage.addActor(iconImage);
    }

    private void loadingProcess(float delta) {

        stateTime += delta;

        if (app.getManager().update() && stateTime >= 3f) {

            dispose();
            app.setScreen(new AuthorizationScreen(app));
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        setGLBackgroundColor(
                glBackgroundColor.r,
                glBackgroundColor.g,
                glBackgroundColor.b,
                glBackgroundColor.a
        );

        loadingProcess(delta);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

        stage.dispose();
        icon.dispose();
    }
}
