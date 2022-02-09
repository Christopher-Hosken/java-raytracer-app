package io.cjhosken.javaraytracerapp.rendering.paver.data;
import java.awt.image.BufferedImage;

import io.cjhosken.javaraytracerapp.core.Ray;
import io.cjhosken.javaraytracerapp.core.Vector3d;
import io.cjhosken.javaraytracerapp.jrs.datatypes.JRSCamera;
import java.awt.Color;

public class PaverCamera {
    private Vector3d location;
    private Vector3d direction;
    private double aperture;
    private double fov;
    private boolean dof;
    private double focusDistance;

    private Vector3d u, v, hori, vert, llc;
    
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

    public Ray getRay(double s, double t) {
        Vector3d rd = Vector3d.mult(aperture / 2.0, Vector3d.randomInUnitDisc());
        if (!dof) {
            rd = new Vector3d(1, 1, 1);
        }
        Vector3d offset = Vector3d.add(Vector3d.mult(u, rd.x), Vector3d.mult(v, rd.y));
        Vector3d target = Vector3d.sub(Vector3d.sub(Vector3d.add(Vector3d.add(llc, Vector3d.mult(s, hori)), Vector3d.mult(t, vert)), location), offset);
        
        return new Ray(Vector3d.add(location, offset), target);
    }

    public Vector3d solveRay(PaverWorld world, Ray ray, int d, int b) {
        PaverObject obj = world.hit(ray);

        if (obj != null) {
            return Vector3d.mult(obj.shader().color(), solveRay(world, obj.shader().scatter(obj, ray), d - 1, b));
        }

        double u = ray.direction().unitVector().y;
        return Vector3d.add(
            Vector3d.mult(new Vector3d(0.3, 0.3, 0.5), (1 - u)),
            Vector3d.mult(new Vector3d(0.25, 0.5, 1), u)
        );
    }

    public int convertRGB(Vector3d color, int samples) {
        double scale = 1.0 / samples;
        color = Vector3d.mult(color, scale);
        color = color.sqrt();
        color.clamp(0, 1);

        int r = (int) (color.x * 255.999);
        int g = (int) (color.y * 255.999);
        int b = (int) (color.z * 255.999);

        return new Color(r, g, b).getRGB();
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
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                Vector3d color = new Vector3d();
                for (int s = 0; s < samples; s++) {
                    double u = ((double) x + Math.random()) / image.getWidth();
                    double v = ((double) y + Math.random()) / image.getHeight();

                    Ray ray = getRay(u, v);
                    color = Vector3d.add(color, solveRay(world, ray, bounces, bounces));
                }

                int rgb = convertRGB(color, samples);
                image.setRGB(x, (image.getHeight() - 1) - y, rgb);
            }

            System.out.println(y + 1 + "/" + image.getHeight() + " Lines Rendered.");
        }

        return image;
    }
}
