package com.game.game.lib.box2d;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.game.game.lib.MainGame;
import com.game.game.lib.actors.BlueActor;
import com.game.game.lib.actors.RedActor;

public class B2BoxStatic_Block {
    private float worldWidth;
    private float worldHeight;
    private float worldScale;
    private float screenScale;

    private BodyDef bodyDef;
    private PolygonShape shape;
    private Body body;
    private FixtureDef fixtureDef;
    private float halfWidthBox;
    private float halfHeightBox;

    public B2BoxStatic_Block(World world, float x, float y, float width, float height, float worldScale, BlueActor blueActor) {
        worldWidth = MainGame.SCREEN_WIDTH * worldScale;
        worldHeight = MainGame.SCREEN_HEIGHT * worldScale;
        this.worldScale = worldScale;
        screenScale = 1 / worldScale;

        halfWidthBox = width * worldScale / 2;
        halfHeightBox = height * worldScale / 2;

        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set((x + width / 2) * worldScale, (y + height / 2) * worldScale);

        shape = new PolygonShape();
        shape.setAsBox(halfWidthBox, halfHeightBox);// boxShape.setAsBox(width * worldScale / 2, height * worldScale / 2);

        fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 1;

        body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);

        shape.dispose();

        body.setUserData(blueActor);
    }

    public B2BoxStatic_Block(World world, float x, float y, float width, float height, float worldScale, RedActor redActor) {
        worldWidth = MainGame.SCREEN_WIDTH * worldScale;
        worldHeight = MainGame.SCREEN_HEIGHT * worldScale;
        this.worldScale = worldScale;
        screenScale = 1 / worldScale;

        halfWidthBox = width * worldScale / 2;
        halfHeightBox = height * worldScale / 2;

        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set((x + width / 2) * worldScale, (y + height / 2) * worldScale);

        shape = new PolygonShape();
        shape.setAsBox(halfWidthBox, halfHeightBox);// boxShape.setAsBox(width * worldScale / 2, height * worldScale / 2);

        fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 1;

        body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);

        shape.dispose();

        body.setUserData(redActor);
    }

    public float getX() {
        return (body.getPosition().x - halfWidthBox) * screenScale;
    }

    public float getY() {
        return (body.getPosition().y - halfHeightBox) * screenScale;
    }

    public void setPosition(float x, float y) {
        body.setTransform(x * worldScale + halfWidthBox, y * worldScale + halfHeightBox,  0);
        body.setAwake(true);
    }

    public Body getBody() {
        return body;
    }

}

