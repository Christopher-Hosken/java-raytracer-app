package io.cjhosken.javaraytracerapp.jrs.datatypes;

import org.json.JSONObject;

import io.cjhosken.javaraytracerapp.core.Vector3d;
import io.cjhosken.javaraytracerapp.jrs.JRSFile;

public class JRSCamera {
    private Vector3d location;
    private Vector3d direction;
    private double aperture;
    private double fov;
    private boolean dof;
    private double focusDistance;

    public JRSCamera() {
        location = new Vector3d();
        rotation = new Vector3d();
        aperture = 2.0;
        fov = 60.0;
        dof = false;
        focusDistance = 1;
    }
    
    public Vector3d location() {
        return location;
    }
    
    public Vector3d direction() {
        return direction;
    }
    
    public double aperture() {
        return aperture;
    }
    
    public double fov() {
        return fov;
    }
    
    public boolean dof() {
        return dof;
    }
    
    public double focusDistance() {
        return focusDistance;
    }
    
    public void setLocation(Vector3d location) {
        this.location = location;
    }
   
    public void setDirection(Vector3d direction) {
        this.direction = direction;
    }
    
    public void setAperture(double aperture) {
        this.aperture = aperture;
    }
    
    public void setFOV(double fov) {
        this.fov = fov;
    }
    
    public void setDOF(boolean dof) {
        this.dof = dof;
    }
    
    public void setFocusDistance(double focusDistance) {
        this.focusDistance = focusDistance;
    }
    
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
