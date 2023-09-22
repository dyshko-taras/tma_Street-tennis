package com.game.game.lib.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.game.game.lib.MainGame;
import com.game.game.lib.box2d.B2BoxStatic_Block;
import com.game.game.lib.tools.GameState;

public class RedActor extends Actor {

    public static final float SCREEN_WIDTH = MainGame.SCREEN_WIDTH;

    private Image image;
    public float width;
    public float height;
    public float speed = 100;
    public B2BoxStatic_Block b2Block;
    public World world;
    public float worldScale;
    public float posX = 0;


    public RedActor(Image image, float x, float y, float width, float height, World world, float worldScale) {
        this.image = image;
        this.width = width;
        this.height = height;
        this.world = world;
        this.worldScale = worldScale;
//        b2Block = new B2BoxStatic_Block(world, x, y, width, height, worldScale);

        b2Block = new B2BoxStatic_Block(world, x, y + 30, width, 1, worldScale, this);

        setBounds(x, y, width, height);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
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
        if (GameState.getState() == GameState.MOVING_BALL) {
//            if (posX >= SCREEN_WIDTH / 2) {
//                moveBy(speed * delta, 0);
//                updateB2Position();
//            } else {
//                moveBy(-speed * delta, 0);
//                updateB2Position();
//            }
            float deltaX = posX - getX();

            if (Math.abs(deltaX) < speed * delta) {
                // Досягли цільової координати X
                setPositionB2(posX, getY());

            } else {
                // Продовжуємо рухатися до цільової координати X
                float direction = deltaX > 0 ? 1 : -1;
                moveBy(speed * direction * delta, 0);
                updateB2Position();
            }
        }
    }

//    public void setPositionB2(float x, float y) {
//        super.setPosition(x, y);
//        b2Block.setPosition(x, y);
//    }

//    public void updateB2Position() {
//        b2Block.setPosition(getX(), getY());
//    }

    public void setPositionB2(float x, float y) {
        super.setPosition(x, y);
        b2Block.setPosition(x, y + 30);
    }

    public void updateB2Position() {
        b2Block.setPosition(getX(), getY() + 30);
    }

    public void updatePositionX(BallActor ballActor) {
        posX = ballActor.getX() + ballActor.getWidth() / 2 - getWidth() / 2;
    }
}
