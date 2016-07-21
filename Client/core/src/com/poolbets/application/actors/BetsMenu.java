package com.poolbets.application.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.poolbets.application.PoolBetsApp;
import com.poolbets.application.additions.Pair;
import com.poolbets.application.additions.Utils;
import com.poolbets.application.models.Bet;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static com.poolbets.application.additions.Utils.getImageTextButton;
import static com.poolbets.application.additions.Utils.setPixmapColor;

/**
 * Created by Roman_Mashenkin on 19.07.2016.
 */
public class BetsMenu extends Table {

    private PoolBetsApp app;
    private Pixmap linePixmap;
    private Texture lineTexture;
    private BitmapFont smallFont, bigFont;

    private Utils.TextureData buttonStyle;

    private final float width;
    private final float height;

    public BetsMenu(final PoolBetsApp app, float width, float height) {

        this.app = app;
        this.width = width;
        this.height = height;

        linePixmap = setPixmapColor(1, 1, "#61656e");
        lineTexture = new Texture(linePixmap);
        lineTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        smallFont = app.getManager().get("PFSSP_40_bac0ce.ttf", BitmapFont.class);
        bigFont = app.getManager().get("PFSSP_60_bac0ce.ttf", BitmapFont.class);

        buttonStyle = getImageTextButton(
                1, 1,
                "#61656e", "#e9e8e6",
                "#fbfbf9", "#2f2c30",
                bigFont
        );

        createBetsMenu();
    }

    private void createBetsMenu() {

        ArrayList<Bet> events = app.getClient().getEvents();

        int n = events.size();

        for (int i = 0; i < n; i++) {

            Array<Label> labels = new Array<>();

            labels.add(new Label(events.get(i).getFirstTeam() + " - " + events.get(i).getSecondTeam(),
                    new Label.LabelStyle(bigFont, Color.valueOf("#fbfbf9"))));

            labels.get(0).setAlignment(Align.center);
            labels.get(0).setWrap(true);

            labels.add(new Label(
                    events.get(i).getLeague() + ", " +
                            events.get(i).getSeason(),
                    new Label.LabelStyle(smallFont, Color.valueOf("#fbfbf9"))));

            labels.get(1).setAlignment(Align.center);

            labels.add(new Label("1",
                    new Label.LabelStyle(smallFont, Color.valueOf("#fbfbf9"))));
            labels.add(new Label("X",
                    new Label.LabelStyle(smallFont, Color.valueOf("#fbfbf9"))));
            labels.add(new Label("2",
                    new Label.LabelStyle(smallFont, Color.valueOf("#fbfbf9"))));

            Array<ImageTextButton> buttons = new Array<>();

            buttons.add(new ImageTextButton(events.get(i).getWinFirstTeam(), buttonStyle.style));
            buttons.add(new ImageTextButton(events.get(i).getDraw(), buttonStyle.style));
            buttons.add(new ImageTextButton(events.get(i).getWinSecondTeam(), buttonStyle.style));

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

            matchInfoTable.add(labels.get(0)).width(width).
                    height(height / 8f).
                    colspan(3).expand().row();
            matchInfoTable.add(labels.get(1)).
                    height(height / 8f).
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

            betInfoTable.add(betInfoButton).height(height / 4f).
                    colspan(3).expandY().row();
            betInfoTable.add(labels.get(2));
            betInfoTable.add(labels.get(3));
            betInfoTable.add(labels.get(4)).row();
            betInfoTable.add(buttons.get(0)).width(width / 4f).
                    height(height / 15f).
                    expand();
            betInfoTable.add(buttons.get(1)).width(width / 4f).
                    height(height / 15f).
                    expand();
            betInfoTable.add(buttons.get(2)).width(width / 4f).
                    height(height / 15f).
                    expand().row();

            if (i != n - 1) {

                Image line = new Image(lineTexture);

                betInfoTable.add(line).width(width * 7 / 8f).
                        height(4).colspan(3);
            }

            add(betInfoTable).width(width).
                    height(height * 3 / 5f).row();
        }
    }

    public void dispose() {

        buttonStyle.texture1.getRegion().getTexture().dispose();
        buttonStyle.texture2.getRegion().getTexture().dispose();
        buttonStyle.pixmap1.dispose();
        buttonStyle.pixmap2.dispose();
    }
}
