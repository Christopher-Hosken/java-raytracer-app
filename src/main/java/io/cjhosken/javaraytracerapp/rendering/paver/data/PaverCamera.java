package io.cjhosken.javaraytracerapp.rendering.paver.data;
import java.awt.image.BufferedImage;

import io.cjhosken.javaraytracerapp.core.Vector3d;
import io.cjhosken.javaraytracerapp.jrs.datatypes.JRSCamera;

public class PaverCamera {
    private Vector3d location;
    private Vector3d direction;
    private double aperture;
    private double fov;
    private boolean dof;
    private double focusDistance;
    
    public PaverCamera() {}

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

    public static PaverCamera fromJRS(JRSCamera camera) {
        PaverCamera paverCamera = new PaverCamera();
        paverCamera.setLocation(camera.location());
        paverCamera.setDirection(camera.direction());
        paverCamera.setAperture(camera.aperture());
        paverCamera.setFOV(camera.fov());
        paverCamera.setDOF(camera.dof());
        paverCamera.setFocusDistance(camera.focusDistance());

        return paverCamera;
    }

    public BufferedImage render(BufferedImage image, PaverWorld world, int samples, int bounces) {
        return image;
    }
}
