package io.cjhosken.javaraytracerapp.jrs.datatypes;

import org.json.JSONObject;

import io.cjhosken.javaraytracerapp.core.Vector3d;
import io.cjhosken.javaraytracerapp.jrs.JRSFile;

public class JRSCamera {
    private Vector3d location = new Vector3d();
    private Vector3d direction = new Vector3d();
    private double aperture = 2.0;
    private double fov = 60.0;
    private boolean dof = false;
    private double focusDistance = 1;

    public JRSCamera() {}

    public static JRSCamera fromJRS(JRSFile file) {
        return file.world().camera();
    }

    public JSONObject toJSON() {
        JSONObject camera = new JSONObject();
        camera.put("location", location.toJSONArray());
        camera.put("direction", direction.toJSONArray());
        camera.put("aperture", aperture);
        camera.put("fov", fov);
        camera.put("dof", dof);
        camera.put("focusDistance", focusDistance);

        return camera;
    }

    public void fromJSON(JSONObject jrs) {
        aperture = jrs.getDouble("aperture");
        fov = jrs.getDouble("fov");
        dof = jrs.getBoolean("dof");
        focusDistance = jrs.getDouble("focusDistance");

        location.fromJSONArray(jrs.getJSONArray("location"));
        direction.fromJSONArray(jrs.getJSONArray("direction"));
    }
}