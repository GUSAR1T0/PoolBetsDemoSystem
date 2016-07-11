package com.poolbets.application.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

import static com.poolbets.application.additions.Utils.getFont;

/**
 * Created by Mashenkin Roman on 10.07.16.
 */
public class Brand extends Table {

    private BitmapFont brandFont;
    private BitmapFont taglineFont;

    public Brand(float width, float height) {

        brandFont = getFont("BigOrange.otf", 80, "#bac0ce", 1.75f);
        taglineFont = getFont("PFSquareSansPro-Regular.ttf", 30, "#bac0ce", 1f);

        Label brandLabel = new Label("PoolBets",
                new Label.LabelStyle(brandFont, Color.valueOf("#fbfbf9")));
        brandLabel.setAlignment(Align.center);

        Label taglineLabel = new Label("Easy betting, life is moneymaking",
                new Label.LabelStyle(taglineFont, Color.valueOf("#fbfbf9")));
        taglineLabel.setAlignment(Align.center);

        setSize(width, height);
        add(brandLabel).width(width).height(height / 2f).expand().row();
        add(taglineLabel).width(width).height(height / 2f).expand().row();
    }

    public void dispose() {

        brandFont.dispose();
        taglineFont.dispose();
    }
}
