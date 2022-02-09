package io.cjhosken.javaraytracerapp.jrs.datatypes;

import org.json.JSONArray;
import org.json.JSONObject;

import io.cjhosken.javaraytracerapp.rendering.paver.data.PaverWorld;

import javafx.scene.Group;

public class JRSWorld {
    private JRSObject[] objects;
    private JRSCamera camera;

    public JRSWorld() {
        objects = new JRSObject[0];
        camera = new JRSCamera();
    }

    public JRSCamera camera() {
        return camera;
    }

    public JRSObject[] objects() {
        return objects;
    }

    public void setCamera(JRSCamera camera) {
        this.camera = camera;
    }

    public void setObjects(JRSObject[] objects) {
        this.objects = objects;
    }

    public Group toFX3D() {
        return new Group();
    }

    public PaverWorld toPaverWorld() {
        PaverWorld world = new PaverWorld();
        world.setCamera(camera.toPaverCamera());
        world.objectsFromJRSArray(objects);
        return world;
    }

    /* TODO */
    public void fromFX3D(Group world) {
    }

    /* TODO */
    public void fromPaverWorld(PaverWorld world) {
    }

    public JSONObject toJSON() {
        JSONObject world = new JSONObject();
        world.put("camera", camera.toJSON());
        world.put("objects", new JSONArray(objects));

        return world;
    }

    public static JRSWorld fromJSON(JSONObject jrs) {
        JRSWorld world = new JRSWorld();
        world.setCamera(JRSCamera.fromJSON(jrs.getJSONObject("camera")));
        JSONArray objectsArray = jrs.getJSONArray("objects");

        JRSObject[] objects = new JRSObject[objectsArray.length()];
        for (int idx = 0; idx < objectsArray.length(); idx++) {
            objects[idx] = JRSObject.fromJSON(objectsArray.getJSONObject(idx));
        }

        world.setObjects(objects);

        return world;
    }
}
