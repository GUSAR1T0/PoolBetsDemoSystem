package com.poolbets.application.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.poolbets.application.PoolBetsApp;
import com.poolbets.application.actors.Header;
import com.poolbets.application.actors.NavigationDrawer;

import static com.poolbets.application.additions.Codes.CODE_AUTHORIZATION;
import static com.poolbets.application.additions.Constants.WORLD_HEIGHT;
import static com.poolbets.application.additions.Constants.WORLD_WIDTH;
import static com.poolbets.application.additions.Constants.RATIO;
import static com.poolbets.application.additions.Utils.getColorRGB;
import static com.poolbets.application.additions.Utils.setGLBackgroundColor;

/**
 * Created by Mashenkin Roman on 07.07.16.
 */
class BaseScreen implements Screen {

    protected PoolBetsApp app;
    protected Color glBackgroundColor;
    protected Stage stage;

    protected Header header;
    protected NavigationDrawer navigationDrawer;

    BaseScreen(final PoolBetsApp app) {

        this.app = app;

        glBackgroundColor = getColorRGB("#2f2c30");

        stage = new Stage(new StretchViewport(WORLD_WIDTH,
                WORLD_HEIGHT * RATIO));

        createHeaderMenu();
        createNavigationDrawerMenu();

        InputMultiplexer inputMultiplexer =
                new InputMultiplexer(navigationDrawer.addGestureDetector(stage), stage);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    private void createHeaderMenu() {

        header = new Header(app.getManager(), stage.getWidth(), stage.getHeight());
        header.getMenuButton().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (!navigationDrawer.getFlag()[1]) {
                    stage.addActor(navigationDrawer);
                    navigationDrawer.getFlag()[0] = true;
                }
            }
        });
        header.getCashButton().setText("63794\nCUB");

        stage.addActor(header);
    }

    private void createNavigationDrawerMenu() {

        navigationDrawer = new NavigationDrawer(app.getManager(),
                stage.getWidth(), stage.getHeight());
        navigationDrawer.getCloseButton().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (!navigationDrawer.getFlag()[0] )
                    navigationDrawer.getFlag()[1] = true;
            }
        });
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

        if (navigationDrawer.getFlag()[0] || navigationDrawer.getFlag()[1])
            navigationDrawer.showNavigationDrawer(stage);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        app.getClient().disconnect();
    }

    @Override
    public void resume() {
        app.getClient().connect(CODE_AUTHORIZATION);
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

        stage.dispose();
        header.dispose();
        navigationDrawer.dispose();
    }
}
