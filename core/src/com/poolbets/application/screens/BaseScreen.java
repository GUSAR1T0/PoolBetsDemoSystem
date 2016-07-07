package com.poolbets.application.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.poolbets.application.PoolBetsApp;

import static com.poolbets.application.additions.Constants.*;
import static com.poolbets.application.additions.Utils.getColorRGB;
import static com.poolbets.application.additions.Utils.getFont;
import static com.poolbets.application.additions.Utils.setGLBackgroundColor;
import static com.poolbets.application.additions.Utils.setPixmapColor;

/**
 * Created by Mashenkin Roman on 07.07.16.
 */
class BaseScreen implements Screen {

    private PoolBetsApp app;
    private Color backgroundColor;
    private Stage stage;
    private Pixmap pTableBackground;
    private TextureRegionDrawable tTableBackground;
    private BitmapFont font;
    private Label labelCom, labelCash;

    BaseScreen(final PoolBetsApp app) {

        this.app = app;

        backgroundColor = getColorRGB("#5a545e");

        stage = new Stage(new StretchViewport(WORLD_WIDTH,
                WORLD_HEIGHT * RATIO));
        Gdx.input.setInputProcessor(stage);

        font = getFont("BigOrange.otf", "#bdbfc7", 100);

        pTableBackground = setPixmapColor(1, 1, "#938380");
        tTableBackground =
                new TextureRegionDrawable(new TextureRegion(new Texture(pTableBackground)));

        createMenu();
    }

    private void createMenu() {

        Table table = new Table();
        table.setPosition(0, stage.getHeight() * 9 / 10f);
        table.setSize(stage.getWidth(), stage.getHeight() / 10f);
        table.setBackground(tTableBackground);

        labelCom = new Label("PoolBets", new Label.LabelStyle(font, Color.valueOf("#f4f5fb")));
        labelCom.setAlignment(Align.center);
        labelCash = new Label("0", new Label.LabelStyle(font, Color.valueOf("#f4f5fb")));
        labelCash.setAlignment(Align.center);

        table.add(labelCom).width(stage.getWidth() * 2 / 3f).expand();
        table.add(labelCash).width(stage.getWidth() / 3f).expand();

        stage.addActor(table);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        setGLBackgroundColor(
                backgroundColor.r,
                backgroundColor.g,
                backgroundColor.b,
                backgroundColor.a
        );

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
        font.dispose();
        pTableBackground.dispose();
        tTableBackground.getRegion().getTexture().dispose();
    }
}
