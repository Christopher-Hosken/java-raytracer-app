package io.cjhosken.javaraytracerapp.rendering.paver.data.base;

import io.cjhosken.javaraytracerapp.rendering.core.Vec3;

public class Ray {
    private Vec3 origin, direction;

    public Ray() {
        origin=direction=new Vec3();
    }

    public Ray(Vec3 origin, Vec3 direction) {
        this.origin = origin;
        this.direction = direction;
    }

    public Vec3 origin() {
        return origin;
    }

    public Vec3 direction() {
        return direction;
    }
    
    public Vec3 at(double t) {
        return Vec3.add(origin, Vec3.mult(direction, t));
    }
}