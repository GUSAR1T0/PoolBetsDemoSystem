package com.poolbets.application.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.poolbets.application.PoolBetsApp;
import com.poolbets.application.additions.Utils.TextureData;

import static com.poolbets.application.additions.Utils.getImageTextButton;
import static com.poolbets.application.additions.Utils.setPixmapColor;

/**
 * Created by Mashenkin Roman on 10.07.16.
 */
public class NavigationDrawer extends Table {

    private Stage stage;
    private Pixmap backgroundPixmap;
    private TextureRegionDrawable backgroundTexture;
    private Table menuTable;
    private Button closeButton;

    private TextureData buttonStyle;

    private boolean[] flag = new boolean[]{false, false};
    private float flingSpeed = 1000;
    private float width, height;
    private float positionX;
    private float dx;

    public NavigationDrawer(final PoolBetsApp app, float width, float height) {

        backgroundPixmap = setPixmapColor(1, 1, "#2f2c30");
        backgroundTexture = new TextureRegionDrawable(new TextureRegion(
                new Texture(backgroundPixmap)));

        buttonStyle = getImageTextButton(
                1, 1,
                "#2f2c30", "#61656e",
                "#fbfbf9", "#fbfbf9",
                app.getManager().get("DINPro_60_bac0ce.ttf", BitmapFont.class)
        );

        this.width = width * 3 / 4f;
        this.height = height;
        positionX = - width;
        dx = 50;

        menuTable = new Table();
        menuTable.align(Align.top);
        menuTable.setBackground(backgroundTexture);
        addElementsOnTableMenu(app);

        closeButton = new Button(new Button.ButtonStyle(null, null, null));

        Table closeButtonTable = new Table();
        closeButtonTable.add(closeButton).width(width).
                height(height * 9 / 10f);

        this.setPosition(- width * 3 / 4f, 0);
        this.setSize(width * 7 / 4f, height);
        this.add(menuTable).width(width * 3 / 4f).height(height);
        this.add(closeButtonTable).width(width).height(height * 9 / 10f).align(Align.bottom);
    }

    public GestureDetector addGestureDetector(final Stage stage) {

        this.stage = stage;

        return new GestureDetector(new GestureDetector.GestureListener() {
                    @Override
                    public boolean touchDown(float x, float y, int pointer, int button) {
                        return false;
                    }

                    @Override
                    public boolean tap(float x, float y, int count, int button) {
                        return false;
                    }

                    @Override
                    public boolean longPress(float x, float y) {
                        return false;
                    }

                    @Override
                    public boolean fling(float velocityX, float velocityY, int button) {

                        if (Math.abs(velocityX) > flingSpeed)
                            if (velocityX > 0) {
                                if (!flag[1]) {
                                    stage.addActor(NavigationDrawer.this);
                                    flag[0] = true;
                                }
                            } else if (velocityX < 0) {
                                if (!flag[0]) {
                                    flag[1] = true;
                                }
                            }

                        return false;
                    }

                    @Override
                    public boolean pan(float x, float y, float deltaX, float deltaY) {
                        return false;
                    }

                    @Override
                    public boolean panStop(float x, float y, int pointer, int button) {
                        return false;
                    }

                    @Override
                    public boolean zoom(float initialDistance, float distance) {
                        return false;
                    }

                    @Override
                    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
                                         Vector2 pointer1, Vector2 pointer2) {
                        return false;
                    }

                    @Override
                    public void pinchStop() {
                    }
                });
    }

    private void addElementsOnTableMenu(final PoolBetsApp app) {

        Array<ImageTextButton> buttons = new Array<ImageTextButton>();

        buttons.add(new ImageTextButton("LINE", buttonStyle.style));
        buttons.add(new ImageTextButton("TOTO", buttonStyle.style));
        buttons.add(new ImageTextButton("HELP", buttonStyle.style));
        buttons.add(new ImageTextButton("EXIT", buttonStyle.style));

        for (ImageTextButton i : buttons)
            i.align(Align.left).padLeft(50);

        buttons.get(0).addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (!flag[0])
                    flag[1] = true;
            }
        });

        buttons.get(3).addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        Label client = new Label("", new Label.LabelStyle(
                app.getManager().get("PFSSP_60_bac0ce.ttf", BitmapFont.class),
                Color.valueOf("#fbfbf9")));
        client.setText("Login: " + app.getClient().getLogin() + "\n" +
                "ID: " + app.getClient().getID());
        client.setAlignment(Align.center);

        Label copyright = new Label("", new Label.LabelStyle(
                app.getManager().get("PFSSP_30_bac0ce.ttf", BitmapFont.class),
                Color.valueOf("#fbfbf9")));
        copyright.setText("2016, PoolBets Application, ver. " + app.getVersion());
        copyright.setAlignment(Align.right);

        Image collision = new Image(backgroundTexture);

        menuTable.add(client).width(width).height(height * 3 / 10f).expand().row();
        menuTable.add(buttons.get(0)).width(width - 30).height(height / 8f).
                center().row();
//        menuTable.add(buttons.get(1)).width(width - 30).height(height / 8f).
//                center().row();
        menuTable.add(collision).width(width).height(height * 3 / 8f).
                center().expand().row();
//        menuTable.add(buttons.get(2)).width(width - 30).height(height / 8f).
//                center().row();
        menuTable.add(buttons.get(3)).width(width - 30).height(height / 8f).
                center().row();
        menuTable.add(copyright).width(width - 30).height(height / 20f).
                center().row();
    }

    public Button getCloseButton() {
        return closeButton;
    }

    public boolean[] getFlag() {
        return flag;
    }

    private float getPositionX() {
        return positionX;
    }

    private float getPositionX(boolean flag) {
        return flag ? positionX + dx : positionX - dx;
    }

    private void setPositionX(float positionX) {
        this.positionX = positionX;
    }

    public void showNavigationDrawer(Stage stage) {

        if (flag[0]) {
            if (getPositionX(true) < 0) {
                setPositionX(getPositionX(true));
                setPosition(getPositionX(), 0);
            } else {
                setPositionX(0);
                setPosition(getPositionX(), 0);
                flag[0] = false;
            }
        }

        if (flag[1]) {
            if (getPositionX(false) > - stage.getWidth()) {
                setPositionX(getPositionX(false));
                setPosition(getPositionX(), 0);
            } else {
                setPositionX(- stage.getWidth());
                setPosition(getPositionX(), 0);
                stage.getRoot().removeActor(this);
                flag[1] = false;
            }
        }
    }

    public void dispose() {

        backgroundPixmap.dispose();
        backgroundTexture.getRegion().getTexture().dispose();
        buttonStyle.pixmap1.dispose();
        buttonStyle.pixmap2.dispose();
        buttonStyle.texture1.getRegion().getTexture().dispose();
        buttonStyle.texture2.getRegion().getTexture().dispose();
    }
}
