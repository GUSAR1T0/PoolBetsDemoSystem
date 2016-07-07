package com.poolbets.application.additions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import static com.poolbets.application.additions.Constants.FONT_CHARS;

/**
 * Created by Mashenkin Roman on 07.07.16.
 */
public class Utils {

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

    public static BitmapFont getFont(String fontName, String colorBorder, int size) {

        FreeTypeFontGenerator generator =
                new FreeTypeFontGenerator(Gdx.files.internal("data/fonts/" + fontName));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter =
                new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.characters = FONT_CHARS;
        parameter.size = size;
        parameter.borderColor = Color.valueOf(colorBorder);
        parameter.borderWidth = 2;
        parameter.minFilter = Texture.TextureFilter.Linear;
        parameter.magFilter = Texture.TextureFilter.Linear;

        BitmapFont font = generator.generateFont(parameter);

        generator.dispose();

        return font;
    }

    public static TextButton.TextButtonStyle getTextButton
            (String upTextColor, String downTextColor,
             BitmapFont font) {

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle(
                null, null, null, font);

        style.fontColor = Color.valueOf(upTextColor);
        style.downFontColor = Color.valueOf(downTextColor);

        return style;
    }
}
