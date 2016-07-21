package com.poolbets.application.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.poolbets.application.PoolBetsApp;
import com.poolbets.application.actors.BetsMenu;
import com.poolbets.application.actors.Header;
import com.poolbets.application.actors.NavigationDrawer;
import com.poolbets.application.additions.Utils;

import static com.poolbets.application.additions.Codes.CODE_AUTHORIZATION;
import static com.poolbets.application.additions.Constants.WORLD_HEIGHT;
import static com.poolbets.application.additions.Constants.WORLD_WIDTH;
import static com.poolbets.application.additions.Constants.RATIO;
import static com.poolbets.application.additions.Utils.getColorRGB;
import static com.poolbets.application.additions.Utils.getImageTextButton;
import static com.poolbets.application.additions.Utils.setGLBackgroundColor;
import static com.poolbets.application.additions.Utils.setPixmapColor;

/**
 * Created by Mashenkin Roman on 07.07.16.
 */
class BaseScreen implements Screen {

    private PoolBetsApp app;
    private Color glBackgroundColor;
    private Stage stage;

    private Header header;
    private NavigationDrawer navigationDrawer;
    private BetsMenu sections;

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

        createBetsMenu();

        header.getCashButton().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        app.getClient().disconnect();
                    }
                });
                thread.setDaemon(true);
                thread.start();

                dispose();
                app.setScreen(new AuthorizationScreen(app));
            }
        });
    }

    private void createBetsMenu() {

        sections = new BetsMenu(app, stage.getWidth(), stage.getHeight());

        ScrollPane scrollPane = new ScrollPane(sections);
        scrollPane.setPosition(0, 0);
        scrollPane.setWidth(stage.getWidth());
        scrollPane.setHeight(stage.getHeight() * 9 / 10f);
        scrollPane.setupOverscroll(stage.getHeight() / 5f, 500, 500);

        stage.addActor(scrollPane);
        stage.setScrollFocus(scrollPane);
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
        header.getCashButton().setText(app.getClient().getCash() + "\nCUB");

        stage.addActor(header);
    }

    private void createNavigationDrawerMenu() {

        navigationDrawer = new NavigationDrawer(app,
                stage.getWidth(), stage.getHeight());
        navigationDrawer.getCloseButton().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (!navigationDrawer.getFlag()[0])
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
        app.getClient().restartConnection();
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

        stage.dispose();
        header.dispose();
        navigationDrawer.dispose();
        sections.dispose();
    }
}
