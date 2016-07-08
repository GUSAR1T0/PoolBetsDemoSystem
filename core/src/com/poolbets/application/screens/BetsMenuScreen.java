package com.poolbets.application.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
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
import com.poolbets.application.additions.Utils;

import static com.poolbets.application.additions.Utils.getFont;
import static com.poolbets.application.additions.Utils.getImageTextButton;
import static com.poolbets.application.additions.Utils.setPixmapColor;

/**
 * Created by Mashenkin Roman on 07.07.16.
 */
public class BetsMenuScreen extends BaseScreen {

    private PoolBetsApp app;
    private Stage stage;
    private BitmapFont fontMainS, fontMainB;
    private Pixmap pLine;
    private Texture tLine;

    private Utils.TextureData buttonMainStyle;

    public BetsMenuScreen(PoolBetsApp app) {
        super(app);

        this.app = getApp();
        stage = getStage();

        fontMainS = getFont("PFSquareSansPro-Regular.ttf", 30, "#bdbfc7", 1);
        fontMainB = getFont("PFSquareSansPro-Regular.ttf", 50, "#bdbfc7", 1);

        pLine = setPixmapColor(1, 1, "#938380");
        tLine = new Texture(pLine);
        tLine.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        buttonMainStyle = getImageTextButton(
                1, 1,
                "#938380", "#b4b1bc",
                "#f4f5fb", "#f4f5fb",
                fontMainB
                );

        createBetsMenu();
    }

    private void createBetsMenu() {

        Table sections = new Table();

        int n = 50;
        for (int i = 0; i < n; i++) {

            Array<Label> labels = new Array<Label>();

            labels.add(new Label("Manchester United FC - Manchester City FC",
                    new Label.LabelStyle(fontMainB, Color.valueOf("#f4f5fb"))));

            labels.get(0).setAlignment(Align.center);
            labels.get(0).setWrap(true);

            labels.add(new Label("English Premier League" + ", " + "10.07.2016 19:00",
                    new Label.LabelStyle(fontMainS, Color.valueOf("#f4f5fb"))));

            labels.add(new Label("1",
                    new Label.LabelStyle(fontMainS, Color.valueOf("#f4f5fb"))));
            labels.add(new Label("X",
                    new Label.LabelStyle(fontMainS, Color.valueOf("#f4f5fb"))));
            labels.add(new Label("2",
                    new Label.LabelStyle(fontMainS, Color.valueOf("#f4f5fb"))));

            Array<ImageTextButton> buttons = new Array<ImageTextButton>();

            buttons.add(new ImageTextButton("1.86", buttonMainStyle.style));
            buttons.add(new ImageTextButton("3.19", buttonMainStyle.style));
            buttons.add(new ImageTextButton("2.52", buttonMainStyle.style));

            buttons.get(0).addCaptureListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    System.out.println("Button 1");
                }
            });

            buttons.get(1).addCaptureListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    System.out.println("Button 2");
                }
            });

            buttons.get(2).addCaptureListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    System.out.println("Button 3");
                }
            });

            Table tableMatchInfo = new Table();

            tableMatchInfo.add(labels.get(0)).width(stage.getWidth()).
                    colspan(3).expand().row();
            tableMatchInfo.add(labels.get(1)).colspan(3).expand().row();

            Button buttonMoreAboutBet = new Button(tableMatchInfo,
                    new Button.ButtonStyle(null, null, null));
            final int finalI = i;
            buttonMoreAboutBet.addCaptureListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    System.out.println(finalI + "");
                }
            });

            Table tableBetInfo = new Table();

            tableBetInfo.add(buttonMoreAboutBet).height(stage.getHeight() / 6f).
                    colspan(3).row();
            tableBetInfo.add(labels.get(2));
            tableBetInfo.add(labels.get(3));
            tableBetInfo.add(labels.get(4)).row();
            tableBetInfo.add(buttons.get(0)).width(stage.getWidth() / 4f).
                    height(stage.getHeight() / 15f).
                    expand();
            tableBetInfo.add(buttons.get(1)).width(stage.getWidth() / 4f).
                    height(stage.getHeight() / 15f).
                    expand();
            tableBetInfo.add(buttons.get(2)).width(stage.getWidth() / 4f).
                    height(stage.getHeight() / 15f).
                    expand().row();

            if (i != n - 1) {

                Image line = new Image(tLine);

                tableBetInfo.add(line).width(stage.getWidth() * 7 / 8f).
                        height(4).colspan(3).expand();
            }

            sections.add(tableBetInfo).width(stage.getWidth()).
                    height(stage.getHeight() / 3f).row();
        }

        ScrollPane scrollTable = new ScrollPane(sections);
        scrollTable.setPosition(0, 0);
        scrollTable.setWidth(stage.getWidth());
        scrollTable.setHeight(stage.getHeight() * 9 / 10f);
        scrollTable.setupOverscroll(stage.getHeight() / 5f, 500, 500);

        stage.addActor(scrollTable);
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

        fontMainS.dispose();
        fontMainB.dispose();
        buttonMainStyle.texture1.getRegion().getTexture().dispose();
        buttonMainStyle.texture2.getRegion().getTexture().dispose();
        buttonMainStyle.pixmap1.dispose();
        buttonMainStyle.pixmap2.dispose();
        pLine.dispose();
        tLine.dispose();
    }
}
