package com.poolbets.application.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.poolbets.application.PoolBetsApp;
import com.poolbets.application.additions.Utils.TextureData;

import static com.poolbets.application.additions.Codes.CODE_SUCCESS;
import static com.poolbets.application.additions.Utils.getImageTextButton;
import static com.poolbets.application.additions.Utils.setPixmapColor;

/**
 * Created by Mashenkin Roman on 07.07.16.
 */
public class BetsMenuScreen extends BaseScreen {

    private BitmapFont smallFont, bigFont;
    private Pixmap linePixmap;
    private Texture lineTexture;

    private TextureData buttonStyle;

    public BetsMenuScreen(final PoolBetsApp app) {
        super(app);

        smallFont = app.getManager().get("PFSSP_40_bac0ce.ttf", BitmapFont.class);
        bigFont = app.getManager().get("PFSSP_60_bac0ce.ttf", BitmapFont.class);

        linePixmap = setPixmapColor(1, 1, "#61656e");
        lineTexture = new Texture(linePixmap);
        lineTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        buttonStyle = getImageTextButton(
                1, 1,
                "#61656e", "#e9e8e6",
                "#fbfbf9", "#2f2c30",
                bigFont
                );

        createBetsMenu();

        header.getCashButton().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                app.getClient().disconnect();
                if (app.getClient().getCode().equals(CODE_SUCCESS)) {
                    dispose();
                    app.setScreen(new AuthorizationScreen(app));
                }
            }
        });
    }

    private void createBetsMenu() {

        Table sections = new Table();

        int n = 50;
        for (int i = 0; i < n; i++) {

            Array<Label> labels = new Array<Label>();

            labels.add(new Label("Manchester United FC - Manchester City FC",
                    new Label.LabelStyle(bigFont, Color.valueOf("#fbfbf9"))));

            labels.get(0).setAlignment(Align.center);
            labels.get(0).setWrap(true);

            labels.add(new Label(
                    "English Premier League" + ", " +
                    "Manchester" + "\n" +
                    "10.07.2016 19:00",
                    new Label.LabelStyle(smallFont, Color.valueOf("#fbfbf9"))));

            labels.get(1).setAlignment(Align.center);

            labels.add(new Label("1",
                    new Label.LabelStyle(smallFont, Color.valueOf("#fbfbf9"))));
            labels.add(new Label("X",
                    new Label.LabelStyle(smallFont, Color.valueOf("#fbfbf9"))));
            labels.add(new Label("2",
                    new Label.LabelStyle(smallFont, Color.valueOf("#fbfbf9"))));

            Array<ImageTextButton> buttons = new Array<ImageTextButton>();

            buttons.add(new ImageTextButton("1.86", buttonStyle.style));
            buttons.add(new ImageTextButton("3.19", buttonStyle.style));
            buttons.add(new ImageTextButton("2.52", buttonStyle.style));

            buttons.get(0).addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    System.out.println("Button 1");
                }
            });

            buttons.get(1).addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    System.out.println("Button 2");
                }
            });

            buttons.get(2).addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    System.out.println("Button 3");
                }
            });

            Table matchInfoTable = new Table();

            matchInfoTable.add(labels.get(0)).width(stage.getWidth()).
                    height(stage.getHeight() / 8f).
                    colspan(3).expand().row();
            matchInfoTable.add(labels.get(1)).
                    height(stage.getHeight() / 8f).
                    colspan(3).expand().row();

            Button betInfoButton = new Button(matchInfoTable,
                    new Button.ButtonStyle(null, null, null));
            final int finalI = i;
            betInfoButton.addCaptureListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    System.out.println(finalI + "");
                }
            });

            Table betInfoTable = new Table();

            betInfoTable.add(betInfoButton).height(stage.getHeight() / 4f).
                    colspan(3).expandY().row();
            betInfoTable.add(labels.get(2));
            betInfoTable.add(labels.get(3));
            betInfoTable.add(labels.get(4)).row();
            betInfoTable.add(buttons.get(0)).width(stage.getWidth() / 4f).
                    height(stage.getHeight() / 15f).
                    expand();
            betInfoTable.add(buttons.get(1)).width(stage.getWidth() / 4f).
                    height(stage.getHeight() / 15f).
                    expand();
            betInfoTable.add(buttons.get(2)).width(stage.getWidth() / 4f).
                    height(stage.getHeight() / 15f).
                    expand().row();

            if (i != n - 1) {

                Image line = new Image(lineTexture);

                betInfoTable.add(line).width(stage.getWidth() * 7 / 8f).
                        height(4).colspan(3);
            }

            sections.add(betInfoTable).width(stage.getWidth()).
                    height(stage.getHeight() * 3 / 5f).row();
        }

        ScrollPane scrollPane = new ScrollPane(sections);
        scrollPane.setPosition(0, 0);
        scrollPane.setWidth(stage.getWidth());
        scrollPane.setHeight(stage.getHeight() * 9 / 10f);
        scrollPane.setupOverscroll(stage.getHeight() / 5f, 500, 500);

        stage.addActor(scrollPane);
        stage.setScrollFocus(scrollPane);
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public void dispose() {

        super.dispose();
        buttonStyle.texture1.getRegion().getTexture().dispose();
        buttonStyle.texture2.getRegion().getTexture().dispose();
        buttonStyle.pixmap1.dispose();
        buttonStyle.pixmap2.dispose();
        linePixmap.dispose();
        lineTexture.dispose();
    }
}
