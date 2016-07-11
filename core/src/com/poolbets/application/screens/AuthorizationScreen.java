package com.poolbets.application.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.poolbets.application.PoolBetsApp;
import com.poolbets.application.actors.Brand;
import com.poolbets.application.additions.Utils.TextureData;
import com.poolbets.application.models.User;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.poolbets.application.additions.Constants.WORLD_HEIGHT;
import static com.poolbets.application.additions.Constants.WORLD_WIDTH;
import static com.poolbets.application.additions.Constants.RATIO;
import static com.poolbets.application.additions.Utils.getColorRGB;
import static com.poolbets.application.additions.Utils.getFont;
import static com.poolbets.application.additions.Utils.getImageTextButton;
import static com.poolbets.application.additions.Utils.setGLBackgroundColor;
import static com.poolbets.application.additions.Utils.setPixmapColor;

/**
 * Created by Mashenkin Roman on 10.07.16.
 */
public class AuthorizationScreen implements Screen {

    private PoolBetsApp app;
    private Color glBackgroundColor;
    private Stage stage;
    private BitmapFont font;
    private Pixmap cursorPixmap, selectionPixmap, backgroundPixmap;
    private Pixmap checkBoxOffPixmap, checkBoxOnPixmap;
    private TextureRegionDrawable cursorTexture, selectionTexture, backgroundTexture;
    private TextureRegionDrawable checkBoxOffTexture, checkBoxOnTexture;
    private TextField.TextFieldStyle fieldStyle;
    private CheckBox.CheckBoxStyle checkBoxStyle;
    private Table menuTable;

    private TextureData buttonStyle;
    private Brand brand;
    private User user;

    public AuthorizationScreen(final PoolBetsApp app) {

        this.app = app;

        glBackgroundColor = getColorRGB("#2f2c30");

        stage = new Stage(new StretchViewport(WORLD_WIDTH,
                WORLD_HEIGHT * RATIO));
        Gdx.input.setInputProcessor(stage);

        brand = new Brand(stage.getWidth(), stage.getHeight() / 8f);

        user = new User("person_data");

        font = getFont("PFSquareSansPro-Regular.ttf", 40, "#61656e", 1f);

        cursorPixmap = setPixmapColor(1, 1, "#2f2c30");
        selectionPixmap = setPixmapColor(1, 1, "#61656e");
        backgroundPixmap = setPixmapColor(1, 1, "#e9e8e6");

        cursorTexture = new TextureRegionDrawable(
                new TextureRegion(new Texture(cursorPixmap)));
        selectionTexture = new TextureRegionDrawable(
                new TextureRegion(new Texture(selectionPixmap)));
        backgroundTexture = new TextureRegionDrawable(
                new TextureRegion(new Texture(backgroundPixmap)));
        
        fieldStyle = new TextField.TextFieldStyle(font, Color.valueOf("#2f2c30"),
                cursorTexture, selectionTexture, backgroundTexture);

        checkBoxOffPixmap = setPixmapColor((int) (stage.getHeight() / 25f),
                (int) (stage.getHeight() / 25f), "#61656e");
        checkBoxOffTexture = new TextureRegionDrawable(
                new TextureRegion(new Texture(checkBoxOffPixmap)));

        checkBoxOnPixmap = setPixmapColor((int) (stage.getHeight() / 25f),
                (int) (stage.getHeight() / 25f), "#e9e8e6");
        checkBoxOnTexture = new TextureRegionDrawable(
                new TextureRegion(new Texture(checkBoxOnPixmap)));

        checkBoxStyle = new CheckBox.CheckBoxStyle(checkBoxOffTexture, checkBoxOnTexture,
                font, Color.valueOf("#fbfbf9"));

        buttonStyle = getImageTextButton(
                1, 1,
                "#61656e", "#e9e8e6",
                "#fbfbf9", "#61656e",
                font
        );

        menuTable = new Table();

        createScreen();

        stage.getRoot().addCaptureListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                if (!(event.getTarget() instanceof TextField)) {
                    stage.setKeyboardFocus(null);
                    Gdx.input.setOnscreenKeyboardVisible(false);
                    menuTable.addAction(moveTo(0, 0, 0.2f));
                }
                return false;
            }
        });
    }

    private void createScreen() {

        final TextField loginField = new TextField("", fieldStyle);
        loginField.setAlignment(Align.center);
        if (user != null) loginField.setText(user.getLogin());

        final TextField passwordField = new TextField("", fieldStyle);
        passwordField.setAlignment(Align.center);
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');
        if (user != null) passwordField.setText(user.getPassword());

        Label loginLabel = new Label("Login",
                new Label.LabelStyle(font, Color.valueOf("#fbfbf9")));
        loginLabel.setAlignment(Align.center);
        loginField.setMaxLength(20);

        Label passwordLabel = new Label("Password",
                new Label.LabelStyle(font, Color.valueOf("#fbfbf9")));
        passwordLabel.setAlignment(Align.center);
        passwordField.setMaxLength(20);

        loginField.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                menuTable.addAction(moveTo(0, stage.getHeight() * 2 / 5f, 0.2f));
                return false;
            }
        });

        passwordField.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                menuTable.addAction(moveTo(0, stage.getHeight() * 2 / 5f, 0.2f));
                return false;
            }
        });

        final CheckBox checkBox = new CheckBox("Remember me", checkBoxStyle);
        checkBox.getImageCell().padRight(25);
        if (user.isExist()) checkBox.setChecked(true);

        ImageTextButton signInButton = new ImageTextButton("Sign In", buttonStyle.style);
        if (!user.isExist()) {
            signInButton.getColor().a = 0.5f;
            signInButton.setDisabled(true);
        }
        signInButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if ((loginField.getText().equals(user.getLogin())) &&
                        (passwordField.getText().equals(user.getPassword()))) {
                    dispose();
                    if (!checkBox.isChecked()) user.deleteData();
                    app.setScreen(new BetsMenuScreen(app));
                }
            }
        });

        ImageTextButton signUpButton = new ImageTextButton("Sign Up", buttonStyle.style);
        if (user.isExist()) {
            signUpButton.getColor().a = 0.5f;
            signUpButton.setDisabled(true);
        }
        signUpButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                user.setLogin(loginField.getText());
                user.setPassword(passwordField.getText());
                user.saveData();
                dispose();
                if (!checkBox.isChecked()) user.deleteData();
                app.setScreen(new BetsMenuScreen(app));
            }
        });

        Table table = new Table();
        table.setSize(stage.getWidth(), stage.getHeight() * 4 / 5f);
        table.add(loginLabel).width(stage.getWidth() / 2f).height(stage.getHeight() / 10f).
                expand().colspan(2).row();
        table.add(loginField).width(stage.getWidth() / 2f).height(stage.getHeight() / 15f).
                expand().colspan(2).row();
        table.add(passwordLabel).width(stage.getWidth() / 2f).height(stage.getHeight() / 10f).
                expand().colspan(2).row();
        table.add(passwordField).width(stage.getWidth() / 2f).height(stage.getHeight() / 15f).
                expand().colspan(2).row();
        table.add(checkBox).width(stage.getWidth() / 2f).height(stage.getHeight() / 10f).
                expand().colspan(2).row();
        table.add(signInButton).width(stage.getWidth() / 5f).height(stage.getHeight() / 15f).center().expand();
        table.add(signUpButton).width(stage.getWidth() / 5f).height(stage.getHeight() / 15f).center().expand();

        menuTable.setPosition(0, 0);
        menuTable.setSize(stage.getWidth(), stage.getHeight());
        menuTable.add(brand).expand().row();
        menuTable.add(table).expand();

        stage.addActor(menuTable);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        setGLBackgroundColor(
                glBackgroundColor.r,
                glBackgroundColor.g,
                glBackgroundColor.b,
                glBackgroundColor.a
        );

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

        stage.dispose();
        brand.dispose();
        cursorPixmap.dispose();
        selectionPixmap.dispose();
        backgroundPixmap.dispose();
        checkBoxOffPixmap.dispose();
        checkBoxOnPixmap.dispose();
        cursorTexture.getRegion().getTexture().dispose();
        selectionTexture.getRegion().getTexture().dispose();
        backgroundTexture.getRegion().getTexture().dispose();
        checkBoxOffTexture.getRegion().getTexture().dispose();
        checkBoxOnTexture.getRegion().getTexture().dispose();
        font.dispose();
        buttonStyle.pixmap1.dispose();
        buttonStyle.pixmap2.dispose();
        buttonStyle.texture1.getRegion().getTexture().dispose();
        buttonStyle.texture2.getRegion().getTexture().dispose();
    }
}
