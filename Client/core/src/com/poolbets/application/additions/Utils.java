package com.poolbets.application.additions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import static com.poolbets.application.additions.Constants.FONT_CHARS;

/**
 * Created by Mashenkin Roman on 07.07.16.
 */
public class Utils {

    public static class TextureData {

        public TextureRegionDrawable texture1;
        public TextureRegionDrawable texture2;
        public Pixmap pixmap1;
        public Pixmap pixmap2;
        public ImageTextButton.ImageTextButtonStyle style;
    }

    public static Color getColorRGB(String color) {
        return new Color(Color.valueOf(color));
    }

    public static void setGLBackgroundColor(float r, float g, float b, float a) {

        Gdx.gl.glClearColor(r, g, b, a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    public static Pixmap setPixmapColor(int width, int height, String color) {

        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.valueOf(color));
        pixmap.fill();

        return pixmap;
    }

    public static BitmapFont getFont(String fontName, int fontSize,
                                     String colorBorder, float borderWidth) {

        FreeTypeFontGenerator generator =
                new FreeTypeFontGenerator(Gdx.files.internal("data/fonts/" + fontName));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter =
                new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.characters = FONT_CHARS;
        parameter.size = fontSize;
        parameter.borderColor = Color.valueOf(colorBorder);
        parameter.borderWidth = borderWidth;
        parameter.minFilter = Texture.TextureFilter.Linear;
        parameter.magFilter = Texture.TextureFilter.Linear;

        BitmapFont font = generator.generateFont(parameter);

        generator.dispose();

        return font;
    }

    public static TextureData getImageTextButton
            (int width, int height,
             String upButtonColor, String downButtonColor,
             String upTextColor, String downTextColor,
             BitmapFont font) {

        TextureData temp = new TextureData();

        temp.pixmap1 = setPixmapColor(width, height, upButtonColor);
        temp.pixmap2 = setPixmapColor(width, height, downButtonColor);

        TextureRegionDrawable texture1 = new TextureRegionDrawable(new TextureRegion(
                new Texture(temp.pixmap1)));
        TextureRegionDrawable texture2 = new TextureRegionDrawable(new TextureRegion(
                new Texture(temp.pixmap2)));

        temp.texture1 = texture1;
        temp.texture2 = texture2;

        temp.style = new ImageTextButton.ImageTextButtonStyle(
                texture1, texture2, texture1, font);

        temp.style.fontColor = Color.valueOf(upTextColor);
        temp.style.downFontColor = Color.valueOf(downTextColor);

        return temp;
    }

    public static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("win");
    }

    public static boolean isUnix() {
        return (System.getProperty("os.name").toLowerCase().contains("nux") ||
                System.getProperty("os.name").toLowerCase().contains("nix"));
    }

    public static boolean isMac() {
        return System.getProperty("os.name").toLowerCase().contains("mac");
    }
}
