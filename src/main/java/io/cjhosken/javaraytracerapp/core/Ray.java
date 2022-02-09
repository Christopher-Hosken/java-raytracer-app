package io.cjhosken.javaraytracerapp.core;

public class Ray {
    private Vector3d origin;
    private Vector3d direction;

    public Ray() {
        origin = direction = new Vector3d();
    }

    public Ray(Vector3d origin, Vector3d direction) {
        this.origin = origin;
        this.direction = direction;
    }

    public Vector3d origin() {
        return origin;
    }

    public Vector3d direction() {
        return direction;
    }

    public Vector3d at(double t) {
        return Vector3d.add(origin, Vector3d.mult(direction, t));
    }
}
