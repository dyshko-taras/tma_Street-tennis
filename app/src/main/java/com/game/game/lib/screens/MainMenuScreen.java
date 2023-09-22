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

public class MainMenuScreen extends ScreenAdapter {

    public static final float SCREEN_WIDTH = MainGame.SCREEN_WIDTH;
    public static final float SCREEN_HEIGHT = MainGame.SCREEN_HEIGHT;

    //Viewports
    private final MainGame main;
    private Viewport viewport;
    private Skin skin;
    private Stage stage;
    private Table mainTable;
    private Table table;

    //table
    private Image playButton;
    private Image settingButton;

    public MainMenuScreen(MainGame main) {
        this.main = main;
    }

    public void show() {
        showCameraAndStage();
        skin = new Skin(Gdx.files.internal("skin.json"));

        mainTable = new Table();
        mainTable.setFillParent(true);

        table = new Table();
        table.setBackground(skin.getDrawable("back1"));

        Label label = new Label("STREET TENNIS", skin, "f40");
        table.add(label).padTop(53.0f).expandX().align(Align.top);

        table.row();
        playButton = new Image(skin, "play_b");
        table.add(playButton).padTop(311.0f).expandX().align(Align.top);

        table.row();
        settingButton = new Image(skin, "setting_b");
        table.add(settingButton).padRight(25.0f).padBottom(30.0f).expandY().align(Align.bottomRight);
        mainTable.add(table).minWidth(360.0f).minHeight(800.0f).maxWidth(360.0f).maxHeight(800.0f);
        stage.addActor(mainTable);

        setClickListeners();
    }

    private void setClickListeners() {
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.setScreen(new GameScreen(main));
            }
        });

        settingButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.setScreen(new SettingScreen(main));
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
