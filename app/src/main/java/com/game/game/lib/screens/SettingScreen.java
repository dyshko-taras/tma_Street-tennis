package com.game.game.lib.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.game.lib.MainGame;
import com.game.game.lib.tools.GameSettings;

public class SettingScreen extends ScreenAdapter {

    public static final float SCREEN_WIDTH = MainGame.SCREEN_WIDTH;
    public static final float SCREEN_HEIGHT = MainGame.SCREEN_HEIGHT;

    //Viewports
    private final MainGame main;
    private Viewport viewport;
    private Skin skin;
    private Stage stage;
    private Table mainTable;
    private Table table;

    //Table
    private Image returnButton;
    private Image musicOn;
    private Image musicOff;
    private Image sfxOn;
    private Image sfxOff;

    public SettingScreen(MainGame main) {
        this.main = main;
    }

    public void show() {
        showCameraAndStage();
        skin = new Skin(Gdx.files.internal("skin.json"));

        mainTable = new Table();
        mainTable.setFillParent(true);

        table = new Table();
        table.setBackground(skin.getDrawable("back1"));

        returnButton = new Image(skin, "return_b");
        table.add(returnButton).padLeft(27.0f).padTop(31.0f).expandX().align(Align.topLeft).colspan(3);

        table.row();
        Label label = new Label("SETTING", skin);
        table.add(label).padTop(-20.0f).expandX().align(Align.top).colspan(3);

        table.row();
        label = new Label("MUSIC", skin, "f32");
        table.add(label).padLeft(34.0f).padTop(228.0f).align(Align.topLeft);

        musicOn  = new Image(skin, "music_on");
        table.add(musicOn).padLeft(33.0f).padTop(209.0f).align(Align.topLeft);

        musicOff = new Image(skin, "music_off");
        table.add(musicOff).padLeft(13.0f).padTop(209.0f).expandX().align(Align.topLeft);

        table.row();
        label = new Label("SFX", skin, "f32");
        table.add(label).padLeft(56.0f).padTop(80.0f).align(Align.topLeft);

        sfxOn = new Image(skin, "sfx_on");
        table.add(sfxOn).padLeft(33.0f).padTop(61.0f).align(Align.topLeft);

        sfxOff = new Image(skin, "sfx_off");
        table.add(sfxOff).padLeft(13.0f).padTop(61.0f).expandX().align(Align.topLeft);

        table.row();
        table.add().expandY().colspan(3);
        mainTable.add(table).minWidth(360.0f).minHeight(800.0f).maxWidth(360.0f).maxHeight(800.0f);
        stage.addActor(mainTable);


        setClickListeners();
    }

    private void setClickListeners() {
        returnButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.setScreen(new MainMenuScreen(main));
            }
        });

        musicOn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameSettings.setMusicOn(true);
                main.playMusic(GameSettings.getMusicOn());
            }
        });

        musicOff.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameSettings.setMusicOn(false);
                main.playMusic(GameSettings.getMusicOn());
            }
        });

        sfxOn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameSettings.setSfxOn(true);

            }
        });

        sfxOff.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameSettings.setSfxOn(false);
            }
        });
    }

    @Override
    public void render(float delta) {
        renderCamera();
    }

    @Override
    public void resize(int width, int height) {
        resizeCamera(width, height);
    }

    /////Camera
    private void showCameraAndStage() {
        viewport = new StretchViewport(SCREEN_WIDTH, SCREEN_HEIGHT);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
    }

    private void renderCamera() {
        ScreenUtils.clear(Color.BLACK);

        viewport.apply();

        stage.act();
        stage.draw();
    }

    private void resizeCamera(int width, int height) {
        viewport.update(width, height, true);
    }
    ////////
}
