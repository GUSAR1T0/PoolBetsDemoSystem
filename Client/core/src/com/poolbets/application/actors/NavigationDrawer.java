package com.poolbets.application.actors;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.poolbets.application.additions.Utils.TextureData;

import static com.poolbets.application.additions.Utils.getFont;
import static com.poolbets.application.additions.Utils.getImageTextButton;
import static com.poolbets.application.additions.Utils.setPixmapColor;

/**
 * Created by Mashenkin Roman on 10.07.16.
 */
public class NavigationDrawer extends Table {

    private Pixmap backgroundPixmap;
    private TextureRegionDrawable backgroundTexture;
    private BitmapFont buttonFont;
    private Table menuTable;
    private Button closeButton;

    private TextureData buttonStyle;
    private Brand brand;

    private boolean[] flag = new boolean[]{false, false};
    private float flingSpeed = 1000;
    private float width, height;
    private float positionX;
    private float dx;

    public NavigationDrawer(float width, float height) {

        backgroundPixmap = setPixmapColor(1, 1, "#2f2c30");
        backgroundTexture = new TextureRegionDrawable(new TextureRegion(
                new Texture(backgroundPixmap)));

        buttonFont = getFont("DINProBold.otf", 60, "#bac0ce", 1.75f);

        buttonStyle = getImageTextButton(
                1, 1,
                "#61656e", "#e9e8e6",
                "#fbfbf9", "#61656e",
                buttonFont
        );

        brand = new Brand(width * 3 / 4f, height * 2 / 5f);

        this.width = width * 3 / 4f;
        this.height = height;
        positionX = - width;
        dx = 50;

        menuTable = new Table();
        menuTable.align(Align.top);
        menuTable.setBackground(backgroundTexture);
        addElementsOnTableMenu();

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

    private void addElementsOnTableMenu() {

        Array<ImageTextButton> buttons = new Array<ImageTextButton>();

        buttons.add(new ImageTextButton("LINE", buttonStyle.style));
        buttons.add(new ImageTextButton("TOTO", buttonStyle.style));
        buttons.add(new ImageTextButton("TWO-WAY BET", buttonStyle.style));

        menuTable.add(brand).width(width).height(height * 2 / 5f).row();
        menuTable.add(buttons.get(0)).width(width).height(height / 5f).row();
        menuTable.add(buttons.get(1)).width(width).height(height / 5f).row();
        menuTable.add(buttons.get(2)).width(width).height(height / 5f).row();
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
        buttonFont.dispose();
        buttonStyle.pixmap1.dispose();
        buttonStyle.pixmap2.dispose();
        buttonStyle.texture1.getRegion().getTexture().dispose();
        buttonStyle.texture2.getRegion().getTexture().dispose();
        brand.dispose();
    }
}
