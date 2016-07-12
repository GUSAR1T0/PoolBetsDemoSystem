package com.poolbets.application.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.poolbets.application.additions.Utils.TextureData;

import static com.poolbets.application.additions.Utils.getFont;
import static com.poolbets.application.additions.Utils.getImageTextButton;
import static com.poolbets.application.additions.Utils.setPixmapColor;

/**
 * Created by Mashenkin Roman on 10.07.16.
 */
public class Header extends Table {

    private Pixmap backgroundPixmap;
    private TextureRegionDrawable backgroundTexture;
    private BitmapFont buttonFont, brandFont;
    private ImageTextButton menuButton, cashButton;

    private TextureData buttonStyle;

    public Header(float width, float height) {

        backgroundPixmap = setPixmapColor(1, 1, "#61656e");
        backgroundTexture = new TextureRegionDrawable(new TextureRegion(
                new Texture(backgroundPixmap)));

        buttonFont = getFont("DINProBold.otf", 40, "#bac0ce", 1.5f);
        brandFont = getFont("BigOrange.otf", 80, "#bac0ce", 1.75f);

        Label brandLabel = new Label("PoolBets",
                new Label.LabelStyle(brandFont, Color.valueOf("#fbfbf9")));
        brandLabel.setAlignment(Align.center);

        buttonStyle = getImageTextButton(
                1, 1,
                "#61656e", "#e9e8e6",
                "#fbfbf9", "#61656e",
                buttonFont
        );

        menuButton = new ImageTextButton("<<<", buttonStyle.style);
        cashButton = new ImageTextButton("", buttonStyle.style);

        this.setPosition(0, height * 9 / 10f);
        this.setSize(width, height / 10f);
        this.setBackground(backgroundTexture);
        this.add(menuButton).width(width / 4f).height(height / 10f).expand();
        this.add(brandLabel).width(width / 2f).height(height / 10f).expand();
        this.add(cashButton).width(width / 4f).height(height / 10f).expand();
    }

    public ImageTextButton getMenuButton() {
        return menuButton;
    }

    public ImageTextButton getCashButton() {
        return cashButton;
    }

    public void dispose() {

        backgroundPixmap.dispose();
        backgroundTexture.getRegion().getTexture().dispose();
        buttonFont.dispose();
        brandFont.dispose();
        buttonStyle.pixmap1.dispose();
        buttonStyle.pixmap2.dispose();
        buttonStyle.texture1.getRegion().getTexture().dispose();
        buttonStyle.texture2.getRegion().getTexture().dispose();
    }
}
