package com.game.game.lib.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
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
import com.game.game.lib.actors.BallActor;
import com.game.game.lib.actors.BlueActor;
import com.game.game.lib.actors.RedActor;
import com.game.game.lib.box2d.B2Screen;
import com.game.game.lib.box2d.ListenerBox2d;
import com.game.game.lib.tools.GameState;

public class GameScreen extends ScreenAdapter {

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
    private Image pauseButton;
    private Image settingButton;
    private Label scoreBlueLabel;
    private Label scoreRedLabel;

    //Box2D
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private Viewport viewportBox2D;
    private float worldScale = 0.01f;
    private B2Screen b2Screen;
    private ListenerBox2d listenerBox2d;

    //Actors
    private BlueActor blueActor;
    private RedActor redActor;
    private BallActor ballActor;

    //Game
    private boolean isWinRound = true;
    private int scoreBlue = 0;
    private int scoreRed = 0;
    private Label pauseLabel;
    private int lastState = 0;


    public GameScreen(MainGame main) {
        this.main = main;
        GameState.setState(GameState.START_ROUND);
        GameState.printState();
    }

    public void show() {
        showCameraAndStage();
        showBox2D();

        skin = new Skin(Gdx.files.internal("skin.json"));

        mainTable = new Table();
        mainTable.setFillParent(true);

        table = new Table();
        table.setBackground(skin.getDrawable("back2"));

        pauseButton = new Image(skin, "pause_b");
        table.add(pauseButton).padLeft(19.0f).padTop(16.0f).align(Align.topLeft);

        table.add().expandX();

        table.add();

        table.add().expandX();

        settingButton = new Image(skin, "setting_b");
        table.add(settingButton).padRight(21.0f).padTop(16.0f).align(Align.topRight);

        table.row();
        scoreBlueLabel = new Label("0", skin, "f_blue");
        scoreBlueLabel.setAlignment(Align.right);
        table.add(scoreBlueLabel).align(Align.topRight).colspan(2);

        Label labelT = new Label(":", skin, "ff");
        labelT.setAlignment(Align.center);
        labelT.setColor(skin.getColor("RGBA_0_0_0_255"));
        table.add(labelT).minWidth(50.0f).maxWidth(50.0f);

        scoreRedLabel = new Label("0", skin, "f_red");
        scoreRedLabel.setAlignment(Align.right);
        table.add(scoreRedLabel).align(Align.topLeft).colspan(2);

        table.row();
        table.add().expandY().colspan(5);
        mainTable.add(table).minWidth(360.0f).minHeight(800.0f).maxWidth(360.0f).maxHeight(800.0f);
        stage.addActor(mainTable);

        setClickListeners();
        addMyActors();
        initInputAdapter();
    }

    private void setClickListeners() {
        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (GameState.getState() != GameState.PAUSE) {
                    lastState = GameState.getState();
                    pauseLabel.setVisible(true);
                    ballActor.pauseMovement();
                    GameState.setState(GameState.PAUSE);
                } else {
                    pauseLabel.setVisible(false);
                    ballActor.resumeMovement();
                    GameState.setState(lastState);
                    System.out.println(lastState + " " + GameState.getState());
                }
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
        renderCameraForBox2D();

        update(delta);
    }

    private void update(float delta) {
        updateStartRound();
        redActor.updatePositionX(ballActor);
        updateRound();
        endGame();
    }

    @Override
    public void resize(int width, int height) {
        resizeCamera(width, height);
        resizeCameraForBox2D(width, height);
    }

    /////Camera
    private void showCameraAndStage() {
        viewport = new StretchViewport(SCREEN_WIDTH, SCREEN_HEIGHT);
        stage = new Stage(viewport);
//        Gdx.input.setInputProcessor(stage);
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

    ///// Box2D
    private void showBox2D() {
        //init Box2D
        Box2D.init();
        world = new World(new Vector2(0, 0), true);
        debugRenderer = new Box2DDebugRenderer();
        viewportBox2D = new StretchViewport(SCREEN_WIDTH * worldScale, SCREEN_HEIGHT * worldScale); //FitViewport or ExtendViewport
        World.setVelocityThreshold(0.1f);
        listenerBox2d = new ListenerBox2d();
        world.setContactListener(listenerBox2d);
        addBodyBox2D();
    }

    private void renderCameraForBox2D() {
        viewportBox2D.apply();
        world.step(1 / 60f, 6, 2);
//        debugRenderer.render(world, viewportBox2D.getCamera().combined);
    }

    private void resizeCameraForBox2D(int width, int height) {
        viewportBox2D.update(width, height, true);
    }

    private void addBodyBox2D() {
        b2Screen = new B2Screen(world, worldScale);
    }
    ////////


    private void addMyActors() {
        Image blueActorImage = new Image(skin, "r_b");
        blueActor = new BlueActor(blueActorImage, 114, 84, blueActorImage.getWidth(), blueActorImage.getHeight(), world, worldScale);

        Image redActorImage = new Image(skin, "r_r");
        redActor = new RedActor(redActorImage, 114, 505, redActorImage.getWidth(), redActorImage.getHeight(), world, worldScale);

        Image ballActorImage = new Image(skin, "ball");
        ballActor = new BallActor(ballActorImage, SCREEN_WIDTH / 2 - ballActorImage.getWidth() / 2, 146, ballActorImage.getWidth()/2, world, worldScale);

        pauseLabel = new Label("PAUSE", skin);
        pauseLabel.setAlignment(Align.center);
        pauseLabel.setPosition(SCREEN_WIDTH / 2 - pauseLabel.getWidth() / 2, SCREEN_HEIGHT / 2 - pauseLabel.getHeight() / 2);
        pauseLabel.setVisible(false);

        stage.addActor(blueActor);
        stage.addActor(redActor);
        stage.addActor(ballActor);
        stage.addActor(pauseLabel);
    }

    private void initInputAdapter() {
        InputMultiplexer inputMultiplexer = new InputMultiplexer(stage, new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                Vector2 touch = stage.screenToStageCoordinates(new Vector2(screenX, screenY));
                if (GameState.getState() == GameState.MOVING_BALL && touch.y <= 320) {
                    if (touch.x <= blueActor.width / 2)  touch.x = blueActor.width / 2;
                    if (touch.x >= SCREEN_WIDTH - blueActor.width / 2) touch.x = SCREEN_WIDTH - blueActor.width / 2;
                    blueActor.setPositionB2(touch.x - blueActor.width / 2, blueActor.getY());
                }
                if (GameState.getState() == GameState.START_ROUND && touch.y <= 320) {
                    ballActor.move(isWinRound);
                }
                return true;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                Vector2 touch = stage.screenToStageCoordinates(new Vector2(screenX, screenY));
                if (GameState.getState() == GameState.MOVING_BALL && touch.y <= 320) {
                    if (touch.x <= blueActor.width / 2)  touch.x = blueActor.width / 2;
                    if (touch.x >= SCREEN_WIDTH - blueActor.width / 2) touch.x = SCREEN_WIDTH - blueActor.width / 2;
                    blueActor.setPositionB2(touch.x - blueActor.width / 2, blueActor.getY());
                }
                return true;
            }
        });
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    private void updateStartRound() {
        if (GameState.getState() == GameState.END_ROUND) {
            blueActor.setPositionB2(114, 84);
            redActor.setPositionB2(114, 505);
            if (isWinRound) {
                ballActor.setPositionB2(SCREEN_WIDTH / 2 - ballActor.getWidth() / 2, 146);
            } else {
                ballActor.setPositionB2(SCREEN_WIDTH / 2 - ballActor.getWidth() / 2, 472);
            }
            GameState.setState(GameState.START_ROUND);
        }
    }

    private void updateRound() {
        if (ballActor.getX() <= -10 || ballActor.getX() >= SCREEN_WIDTH + 10) {
            isWinRound = false;
            scoreRed++;
            scoreRedLabel.setText(scoreRed);
            GameState.setState(GameState.END_ROUND);
        } else if (ballActor.getY() < 55) {
            isWinRound = false;
            scoreRed++;
            scoreRedLabel.setText(scoreRed);
            GameState.setState(GameState.END_ROUND);
        } else if (ballActor.getY() > 570) {
            isWinRound = true;
            scoreBlue++;
            scoreBlueLabel.setText(scoreBlue);
            GameState.setState(GameState.END_ROUND);
        }
    }

    private void endGame() {
        if (scoreBlue >= 10 || scoreRed >= 10) {
            String result = scoreBlue + " : " + scoreRed;
            main.setScreen(new GameOverScreen(main, result));
        }
    }
}
