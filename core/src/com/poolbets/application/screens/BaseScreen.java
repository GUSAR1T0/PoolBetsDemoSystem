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
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.poolbets.application.PoolBetsApp;

import static com.poolbets.application.additions.Constants.*;
import static com.poolbets.application.additions.Utils.getColorRGB;
import static com.poolbets.application.additions.Utils.getFont;
import static com.poolbets.application.additions.Utils.getTextButton;
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
    private BitmapFont fontBrend, fontButton;
    private Label labelBrend;
    private TextButton buttonMenu, buttonCash;

    BaseScreen(final PoolBetsApp app) {

        this.app = app;

        backgroundColor = getColorRGB("#5a545e");

        stage = new Stage(new StretchViewport(WORLD_WIDTH,
                WORLD_HEIGHT * RATIO));
        Gdx.input.setInputProcessor(stage);

        fontBrend = getFont("BigOrange.otf", "#bdbfc7", 80);
        fontButton = getFont("BigOrange.otf", "#bdbfc7", 40);

        pTableBackground = setPixmapColor(1, 1, "#938380");
        tTableBackground =
                new TextureRegionDrawable(new TextureRegion(new Texture(pTableBackground)));

        createInfoTable();
        createScroll();
    }

    private void createInfoTable() {

        Table table = new Table();
        table.setPosition(0, stage.getHeight() * 9 / 10f);
        table.setSize(stage.getWidth(), stage.getHeight() / 10f);
        table.setBackground(tTableBackground);

        labelBrend = new Label("PoolBets",
                new Label.LabelStyle(fontBrend, Color.valueOf("#f4f5fb")));
        labelBrend.setAlignment(Align.center);

        TextButton.TextButtonStyle style = getTextButton("#f4f5fb", "#938380", fontButton);
        buttonMenu = new TextButton("<<<", style);
        buttonCash = new TextButton("0\nCUB", style);

        table.add(buttonMenu).width(stage.getWidth() / 4f).height(stage.getHeight() / 10f).expand();
        table.add(labelBrend).width(stage.getWidth() / 2f).height(stage.getHeight() / 10f).expand();
        table.add(buttonCash).width(stage.getWidth() / 4f).height(stage.getHeight() / 10f).expand();

        stage.addActor(table);
    }

    private void createScroll() {

        Table sections = new Table();

        Array<Label> labels = new Array<Label>();

        for (int i = 0; i < 50; i++) {
            labels.add(new Label(i + "",
                    new Label.LabelStyle(fontBrend, Color.valueOf("#f4f5fb"))));
            labels.get(i).setAlignment(Align.center);
            sections.add(labels.get(i)).width(stage.getWidth()).height(stage.getHeight() / 10f).row();
        }

        ScrollPane scrollTable = new ScrollPane(sections);
        scrollTable.setPosition(0, 0);
        scrollTable.setWidth(stage.getWidth());
        scrollTable.setHeight(stage.getHeight() * 9 / 10f);
        scrollTable.setupOverscroll(300, 500, 500);

        stage.addActor(scrollTable);
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
        fontBrend.dispose();
        fontButton.dispose();
        pTableBackground.dispose();
        tTableBackground.getRegion().getTexture().dispose();
    }
}
