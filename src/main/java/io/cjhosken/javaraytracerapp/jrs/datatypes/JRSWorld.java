package io.cjhosken.javaraytracerapp.jrs.datatypes;

import org.json.JSONArray;
import org.json.JSONObject;

import io.cjhosken.javaraytracerapp.rendering.fx3d.FX3DObject;
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

    public void fromFX3D(Group world) {
        Group fxObjects = (Group) world.getChildren().get(0);
        objects = new JRSObject[fxObjects.getChildren().size()]; 
        for (int idx = 0; idx < fxObjects.getChildren().size(); idx++) {
            FX3DObject fxObj =  (FX3DObject) fxObjects.getChildren().get(idx);
            objects[idx] = fxObj.toJRS();
        }
    }

    public JSONObject toJSON() {
        JSONObject world = new JSONObject();
        world.put("camera", camera.toJSON());
        JSONObject[] jsonObjects = new JSONObject[objects.length];

        for (int obx = 0; obx < jsonObjects.length; obx++) {
            jsonObjects[obx] = objects[obx].toJSON();
        }
        world.put("objects", new JSONArray(jsonObjects));

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
