package com.game.game.lib.box2d;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.game.game.lib.MainGame;

public class B2Screen {
    private float worldWidth;
    private float worldHeight;
    private float worldScale;
    private float screenScale;


    public B2Screen(World world, float worldScale) {
        worldWidth = MainGame.SCREEN_WIDTH * worldScale;
        worldHeight = MainGame.SCREEN_HEIGHT * worldScale;
        this.worldScale = worldScale;
        screenScale = 1 / worldScale;

        BodyDef screenBodyDef = new BodyDef();
        screenBodyDef.type = BodyDef.BodyType.StaticBody;
        screenBodyDef.position.set(0, 0);
        Body screenBody = world.createBody(screenBodyDef);

        EdgeShape screenShape = new EdgeShape();
        FixtureDef screenFixtureDef = new FixtureDef();
        screenFixtureDef.friction = 0;
        screenFixtureDef.restitution = 1;

        screenShape.set(0, 0, worldWidth, 0);
        screenFixtureDef.shape = screenShape;
        screenBody.createFixture(screenFixtureDef);

        screenShape.set(worldWidth, 0, worldWidth, worldHeight);
        screenFixtureDef.shape = screenShape;
        screenBody.createFixture(screenFixtureDef);

        screenShape.set(0, worldHeight, worldWidth, worldHeight);
        screenFixtureDef.shape = screenShape;
        screenBody.createFixture(screenFixtureDef);

        screenShape.set(0, 0, 0, worldHeight);
        screenFixtureDef.shape = screenShape;
        screenBody.createFixture(screenFixtureDef);

        screenShape.dispose();
    }
}
