package com.game.game.lib.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.game.game.lib.box2d.B2BoxStatic_Block;

public class BlueActor extends Actor {

    private Image image;
    public float width;
    public float height;
    public B2BoxStatic_Block b2Block;
    public World world;
    public float worldScale;

    public BlueActor(Image image,float x, float y, float width, float height, World world, float worldScale) {
        this.image = image;
        this.width = width;
        this.height = height;
        this.world = world;
        this.worldScale = worldScale;
//        b2Block = new B2BoxStatic_Block(world, x , y, width, height, worldScale);
        b2Block = new B2BoxStatic_Block(world, x,y + 30, width, 1, worldScale, this);

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
    }

//    public void setPositionB2(float x, float y) {
//        super.setPosition(x, y);
//        b2Block.setPosition(x, y);
//    }

    public void setPositionB2(float x, float y) {
        super.setPosition(x, y);
        b2Block.setPosition(x,y + 30);
    }
}
