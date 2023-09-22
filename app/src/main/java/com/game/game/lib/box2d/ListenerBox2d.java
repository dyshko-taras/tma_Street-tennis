package com.game.game.lib.box2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.game.game.lib.actors.BallActor;
import com.game.game.lib.actors.BlueActor;
import com.game.game.lib.actors.RedActor;
import com.game.game.lib.tools.GameSettings;

public class ListenerBox2d implements ContactListener {
    private Fixture fixtureA;
    private Fixture fixtureB;

    public Sound sound;

    public ListenerBox2d() {
        sound = Gdx.audio.newSound(Gdx.files.internal("ball.mp3"));
        System.out.println("ListenerBox2d");
    }

    @Override
    public void beginContact(Contact contact) {
        fixtureA = contact.getFixtureA();
        fixtureB = contact.getFixtureB();

        if (fixtureA.getBody().getUserData() instanceof BallActor && fixtureB.getBody().getUserData() instanceof BlueActor) {
            playSound();
        }
         if (fixtureB.getBody().getUserData() instanceof BallActor && fixtureA.getBody().getUserData() instanceof BlueActor) {
            playSound();
        }


        if (fixtureA.getBody().getUserData() instanceof BallActor && fixtureB.getBody().getUserData() instanceof RedActor) {
            playSound();
        } else if (fixtureB.getBody().getUserData() instanceof BallActor && fixtureA.getBody().getUserData() instanceof RedActor) {
            playSound();
        }

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    private void playSound() {
        if (GameSettings.getSfxOn()) {
            sound.play();
        }
    }
}

