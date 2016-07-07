package com.poolbets.application.additions;

import com.badlogic.gdx.Gdx;

/**
 * Created by Mashenkin Roman on 07.07.16.
 */
public class Constants {

   public static final String TITLE = "PoolBets Application";

   public static final int WIDHT = 600;
   public static final int HEIGHT = 800;

   public static final int WORLD_WIDTH = 1000;
   public static final int WORLD_HEIGHT = 1000;

   public static final float RATIO = (float) Gdx.graphics.getHeight() / Gdx.graphics.getWidth();

   public static final String FONT_CHARS = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя" +
           "abcdefghijklmnopqrstuvwxyz" + "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ" +
           "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"´`'<>";
}