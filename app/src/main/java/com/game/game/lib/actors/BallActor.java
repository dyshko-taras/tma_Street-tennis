package com.game.game.lib.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.game.game.lib.box2d.B2CircleDynamic_Ball;
import com.game.game.lib.tools.GameState;
import com.game.game.lib.utils.VectorFromAngleAndLength;

public class BallActor extends Actor {
    private Image image;
    private float speed = 100;
    public float radius;
    public B2CircleDynamic_Ball b2Ball;
    private float worldScale;
    public float degrees = 0;
    private float lastX = 0;
    private float lastY = 0;


    public BallActor(Image image, float x, float y, float radius, World world, float worldScale) {
        this.image = image;
        this.radius = radius;
        this.worldScale = worldScale;
        b2Ball = new B2CircleDynamic_Ball(world, x, y, radius, worldScale, this);
        setBounds(x, y, radius * 2, radius * 2);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        b2Ball.checkVelocity();
        setPosition(b2Ball.getX(), b2Ball.getY());

        super.draw(batch, parentAlpha);
        image.setBounds(getX(), getY(), getWidth(), getHeight());
        image.setOrigin(getOriginX(), getOriginY());
        image.setRotation(getRotation());
        image.setScale(getScaleX(), getScaleY());
        image.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (GameState.getState() == GameState.MAKE_CLICK) {
            b2Ball.setLinearVelocity(VectorFromAngleAndLength.getVector(degrees, 400));
            GameState.setState(GameState.MOVING_BALL);
        } else if (GameState.getState() == GameState.END_ROUND) {
            b2Ball.setLinearVelocity(0, 0);
        }
    }

    public void setPositionB2(float x, float y) {
        super.setPosition(x, y);
        b2Ball.setPosition(x, y);
    }

    public void move(boolean isWinRound) {
        if (isWinRound) {
            degrees = MathUtils.random(45, 135);
        } else {
            degrees = MathUtils.random(225, 315);
        }
        GameState.setState(GameState.MAKE_CLICK);
    }

    public void pauseMovement() {
        // Зупинити рух м'яча, наприклад, встановивши лінійну швидкість на нуль

        lastY = b2Ball.getBody().getLinearVelocity().y / worldScale;
        lastX = b2Ball.getBody().getLinearVelocity().x / worldScale;
        b2Ball.setLinearVelocity(0, 0);

    }

    public void resumeMovement() {
        // Відновити рух м'яча, встановивши потрібну лінійну швидкість
        b2Ball.setLinearVelocity(lastX, lastY);
    }






}
