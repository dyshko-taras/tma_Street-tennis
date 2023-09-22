package com.game.game.lib.box2d;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.game.game.lib.MainGame;
import com.game.game.lib.actors.BallActor;

public class B2CircleDynamic_Ball {

    private float worldWidth;
    private float worldHeight;
    private float worldScale;
    private float screenScale;

    private BodyDef bodyDef;
    private CircleShape circleShape;
    private Body body;
    private FixtureDef fixtureDef;
    private float radiusCircle;
    private int check = 0;


    public B2CircleDynamic_Ball(World world, float x, float y, float radius, float worldScale, BallActor ballActor) {
        worldWidth = MainGame.SCREEN_WIDTH * worldScale;
        worldHeight = MainGame.SCREEN_HEIGHT * worldScale;
        this.worldScale = worldScale;
        screenScale = 1 / worldScale;

        radiusCircle = radius * worldScale;

        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x * worldScale + radiusCircle, y * worldScale + radiusCircle);
        circleShape = new CircleShape();
        circleShape.setRadius(radiusCircle);

        fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        fixtureDef.density = 0;
        fixtureDef.friction = 0;
        fixtureDef.restitution = 1f;

        body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        circleShape.dispose();

        body.setUserData(ballActor);
    }

    public float getX() {
        return (body.getPosition().x - radiusCircle) * screenScale;
    }

    public float getY() {
        return (body.getPosition().y - radiusCircle) * screenScale;
    }

    public void setPosition(float x, float y) {
        body.setTransform(x * worldScale + radiusCircle, y * worldScale + radiusCircle, 0);
    }

    public Body getBody() {
        return body;
    }

    public void setLinearVelocity(float velocityX, float velocityY) {
        body.setLinearVelocity(new Vector2(velocityX * worldScale, velocityY * worldScale));
    }

    public void setLinearVelocity(Vector2 velocity) {
        body.setLinearVelocity(new Vector2(velocity.x * worldScale, velocity.y * worldScale));
    }


    public void checkVelocity() {
        if (body.getLinearVelocity().x == 0 && body.getLinearVelocity().y != 0) {
            check++;
            if(check == 400) {
                body.setLinearVelocity(30 * worldScale, body.getLinearVelocity().y);
                check = 0;
            }
        }

        if (body.getLinearVelocity().x != 0 && body.getLinearVelocity().y == 0) {
            check++;
            if(check == 400) {
                body.setLinearVelocity(body.getLinearVelocity().x, 30 * worldScale);
                check = 0;
            }
        }
    }
}

