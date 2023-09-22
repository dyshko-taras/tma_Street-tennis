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

public class GameOverScreen extends ScreenAdapter {

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
    private Image settingButton;
    private Image restartButton;
    private Label scoreLabel;

    //Actors
    private Image returnButton;

    //Game
    private String result;


    public GameOverScreen(MainGame main, String result) {
        this.main = main;
        this.result = result;
    }

    public void show() {
        showCameraAndStage();
        skin = new Skin(Gdx.files.internal("skin.json"));

        mainTable = new Table();
        mainTable.setFillParent(true);

        table = new Table();
        table.setBackground(skin.getDrawable("back1"));

        settingButton = new Image(skin, "setting_b");
        table.add(settingButton).padRight(22.0f).padTop(21.0f).align(Align.topRight);

        table.row();
        scoreLabel = new Label("YOUR SCORE: " + result, skin, "f32");
        table.add(scoreLabel).padTop(19.0f).expandX().align(Align.top);

        table.row();
        Label label = new Label("GAME OVER", skin, "f40");
        table.add(label).padTop(171.0f).expandX().align(Align.top);

        table.row();
        restartButton = new Image(skin, "restart_b");
        table.add(restartButton).padTop(67.0f).expand().align(Align.top);
        mainTable.add(table).minWidth(360.0f).minHeight(800.0f).maxWidth(360.0f).maxHeight(800.0f);
        stage.addActor(mainTable);

        returnButton = new Image(skin, "return_b");
        returnButton.setPosition(22,SCREEN_HEIGHT - returnButton.getHeight() - 22);
        stage.addActor(returnButton);

        setClickListeners();
    }

    private void setClickListeners() {
        restartButton.addListener(new ClickListener() {
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

        returnButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.setScreen(new MainMenuScreen(main));
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
