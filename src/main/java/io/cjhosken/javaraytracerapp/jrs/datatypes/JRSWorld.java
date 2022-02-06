package io.cjhosken.javaraytracerapp.jrs.datatypes;

import org.json.JSONArray;
import org.json.JSONObject;

public class JRSWorld {
    private JRSObject[] objects = new JRSObject[0];
    private JRSCamera camera = new JRSCamera();

    public JRSCamera camera() {
        return camera;
    }

    public JRSObject[] objects() {
        return objects;
    }

    public JSONObject toJSON() {
        JSONObject world = new JSONObject();
        world.put("camera", camera.toJSON());
        world.put("objects", new JSONArray(objects));

        return world;
    }

    public void fromJSON(JSONObject jrs) {
        camera.fromJSON(jrs.getJSONObject("camera"));
        JSONArray objectsArray = jrs.getJSONArray("objects");

        objects = new JRSObject[objectsArray.length()];
        for (int idx = 0; idx < objectsArray.length(); idx++) {
            objects[idx].fromJSON(objectsArray.getJSONObject(idx));
        }
    }
}