package com.poolbets.application.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.poolbets.application.PoolBetsApp;
import com.poolbets.application.additions.Utils;

import static com.poolbets.application.additions.Constants.*;
import static com.poolbets.application.additions.Utils.getColorRGB;
import static com.poolbets.application.additions.Utils.getFont;
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
    private Pixmap pHeaderTableBackground;
    private TextureRegionDrawable tHeaderTableBackground;
    private BitmapFont fontHeaderBrand, fontHeaderButton;

    private Utils.TextureData buttonHeaderStyle;

    BaseScreen(final PoolBetsApp app) {

        this.app = app;

        glBackgroundColor = getColorRGB("#5a545e");

        stage = new Stage(new StretchViewport(WORLD_WIDTH,
                WORLD_HEIGHT * RATIO));
        Gdx.input.setInputProcessor(stage);

        fontHeaderBrand = getFont("BigOrange.otf", 80, "#bdbfc7", 2);
        fontHeaderButton = getFont("BigOrange.otf", 40, "#bdbfc7", 1.75f);

        pHeaderTableBackground = setPixmapColor(1, 1, "#938380");
        tHeaderTableBackground =
                new TextureRegionDrawable(new TextureRegion(new Texture(pHeaderTableBackground)));

        buttonHeaderStyle = getImageTextButton(
                1, 1,
                "#938380", "#b4b1bc",
                "#f4f5fb", "#f4f5fb",
                fontHeaderButton
        );

        createInfoTable();
    }

    private void createInfoTable() {

        Table table = new Table();
        table.setPosition(0, stage.getHeight() * 9 / 10f);
        table.setSize(stage.getWidth(), stage.getHeight() / 10f);
        table.setBackground(tHeaderTableBackground);

        Label labelHeaderBrand = new Label("PoolBets",
                new Label.LabelStyle(fontHeaderBrand, Color.valueOf("#f4f5fb")));
        labelHeaderBrand.setAlignment(Align.center);

        ImageTextButton buttonMenu = new ImageTextButton("<<<", buttonHeaderStyle.style);
        ImageTextButton buttonCash = new ImageTextButton("0\nCUB", buttonHeaderStyle.style);

        table.add(buttonMenu).width(stage.getWidth() / 4f).
                height(stage.getHeight() / 10f).
                expand();
        table.add(labelHeaderBrand).width(stage.getWidth() / 2f).
                height(stage.getHeight() / 10f).
                expand();
        table.add(buttonCash).width(stage.getWidth() / 4f).
                height(stage.getHeight() / 10f).
                expand();

        stage.addActor(table);
    }

    public PoolBetsApp getApp() {
        return app;
    }

    public Stage getStage() {
        return stage;
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
        fontHeaderBrand.dispose();
        fontHeaderButton.dispose();
        pHeaderTableBackground.dispose();
        tHeaderTableBackground.getRegion().getTexture().dispose();
        buttonHeaderStyle.pixmap1.dispose();
        buttonHeaderStyle.pixmap2.dispose();
        buttonHeaderStyle.texture1.getRegion().getTexture().dispose();
        buttonHeaderStyle.texture2.getRegion().getTexture().dispose();
    }
}
