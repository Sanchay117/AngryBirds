package io.github.AngryBirds;

import com.badlogic.gdx.physics.box2d.*;
import io.github.AngryBirds.Pig;

public class MyContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        // Get the two bodies involved in the collision
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();

        // Check if the collision involves a Pig
        if (bodyA.getUserData() instanceof Pig) {
            Pig pig = (Pig) bodyA.getUserData();
            pig.setHp(pig.getHp()-5); // Reduce Pig's health
            System.out.println("Pig HP: " + pig.getHp());
        }

        if (bodyB.getUserData() instanceof Pig) {
            Pig pig = (Pig) bodyB.getUserData();
            pig.setHp(pig.getHp()-5); // Reduce Pig's health
            System.out.println("Pig HP: " + pig.getHp());
        }
    }

    @Override
    public void endContact(Contact contact) {
        // Called when two bodies stop colliding
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        // Called before the physics engine resolves the collision
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        // Called after the physics engine resolves the collision
    }
}
