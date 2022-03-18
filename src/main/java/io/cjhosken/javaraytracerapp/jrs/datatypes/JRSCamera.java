package io.cjhosken.javaraytracerapp.jrs.datatypes;

import org.json.JSONObject;

import io.cjhosken.javaraytracerapp.core.Vector3d;
import io.cjhosken.javaraytracerapp.rendering.paver.data.PaverCamera;
import javafx.scene.Camera;
import javafx.scene.PerspectiveCamera;

public class JRSCamera {
    private Vector3d location;
    private Vector3d direction;
    private double aperture;
    private double fov;
    private boolean dof;
    private double focusDistance;

    public JRSCamera() {
        location = new Vector3d();
        direction = new Vector3d();
        aperture = 2.0;
        fov = 30.0;
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

    public PaverCamera toPaverCamera() {
        PaverCamera camera = new PaverCamera();
        camera.setLocation(location);
        camera.setDirection(direction);
        camera.setAperture(aperture);
        camera.setFOV(fov);
        camera.setDOF(dof);
        camera.setFocusDistance(focusDistance);
        return camera;
    }

    /* TODO: JRSCamera to FX3D*/
    public PerspectiveCamera toFX3D() {
        PerspectiveCamera camera = new PerspectiveCamera(true);
        return camera;
    }

    /* TODO: JRSCamera from FX3D */
    public static JRSCamera fromFX3D(Camera camera) {
        JRSCamera jrsCamera = new JRSCamera();
        jrsCamera.setLocation(new Vector3d(camera.getTranslateX(), camera.getTranslateY(), camera.getTranslateZ()));
        jrsCamera.setDirection(new Vector3d());

        return jrsCamera;
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

    public static JRSCamera fromJSON(JSONObject jrs) {
        JRSCamera jrsCamera = new JRSCamera();
        jrsCamera.setAperture(jrs.getDouble("aperture"));
        jrsCamera.setFOV(jrs.getDouble("fov"));
        jrsCamera.setDOF(jrs.getBoolean("dof"));
        jrsCamera.setFocusDistance(jrs.getDouble("focusDistance"));

        jrsCamera.setLocation(Vector3d.fromJSONArray(jrs.getJSONArray("location")));
        jrsCamera.setDirection(Vector3d.fromJSONArray(jrs.getJSONArray("direction")));

        return jrsCamera;
    }
}
