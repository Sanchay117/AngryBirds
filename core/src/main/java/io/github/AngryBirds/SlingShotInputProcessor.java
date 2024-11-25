package io.github.AngryBirds;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

public class SlingShotInputProcessor extends InputAdapter {
    private final SlingShot slingShot;
    private final OrthographicCamera camera;

    public SlingShotInputProcessor(SlingShot slingShot, OrthographicCamera camera) {
        this.slingShot = slingShot;
        this.camera = camera;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 worldCoordinates = camera.unproject(new Vector3(screenX, screenY, 0));
        slingShot.startDrag(worldCoordinates.x, worldCoordinates.y);
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        Vector3 worldCoordinates = camera.unproject(new Vector3(screenX, screenY, 0));
        slingShot.updateDrag(worldCoordinates.x, worldCoordinates.y);
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        slingShot.endDrag();
        return true;
    }
}
