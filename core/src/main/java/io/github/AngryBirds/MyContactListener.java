package io.github.AngryBirds;

import com.badlogic.gdx.physics.box2d.*;
import io.github.AngryBirds.Pig;

public class MyContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        // Get the two bodies involved in the collision
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();

        if (bodyA.getUserData() instanceof Pig && !(bodyB.getUserData() instanceof Pig)) {
            Pig pig = (Pig) bodyA.getUserData();
            if(bodyB.getUserData() instanceof Material) pig.hit((Material) bodyB.getUserData());
            else pig.setHp(pig.getHp() - 5);
        }

        if (bodyB.getUserData() instanceof Pig && !(bodyA.getUserData() instanceof Pig)) {
            Pig pig = (Pig) bodyB.getUserData();
            if(bodyA.getUserData() instanceof Material) pig.hit((Material) bodyA.getUserData());
            else pig.setHp(pig.getHp() - 5);
        }

        if(bodyA.getUserData() instanceof Material && !(bodyB.getUserData() instanceof Pig) && !(bodyB.getUserData() instanceof Material) ) {
            Material material = (Material) bodyA.getUserData();
            material.hit();
        }

        if(bodyB.getUserData() instanceof Material && !(bodyA.getUserData() instanceof Pig) && !(bodyA.getUserData() instanceof Material) ) {
            Material material = (Material) bodyB.getUserData();
            material.hit();
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
