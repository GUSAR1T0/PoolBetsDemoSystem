package com.poolbets.application.actors;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

/**
 * Created by Mashenkin Roman on 10.07.16.
 */
public class Brand extends Table {

    public Brand(AssetManager manager, float width, float height) {

        Label brandLabel = new Label("PoolBets",
                new Label.LabelStyle(manager.get("BigOrange_80_bac0ce.ttf", BitmapFont.class),
                        Color.valueOf("#fbfbf9")));
        brandLabel.setAlignment(Align.center);

        Label taglineLabel = new Label("Easy betting, life is moneymaking",
                new Label.LabelStyle(manager.get("PFSSP_30_bac0ce.ttf", BitmapFont.class),
                        Color.valueOf("#fbfbf9")));
        taglineLabel.setAlignment(Align.center);

        add(brandLabel).width(width).height(height / 2f).expand().row();
        add(taglineLabel).width(width).height(height / 2f).expand().row();
    }
}
