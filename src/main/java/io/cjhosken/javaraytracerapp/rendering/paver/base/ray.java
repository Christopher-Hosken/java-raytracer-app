package io.cjhosken.javaraytracerapp.rendering.paver.base;

public class ray {
    private vec3 origin, direction;

    public ray() {
        origin=direction=new vec3();
    }

    public ray(vec3 origin, vec3 direction) {
        this.origin = origin;
        this.direction = direction;
    }

    public vec3 origin() {
        return origin;
    }

    public vec3 direction() {
        return direction;
    }
    
    public vec3 at(double t) {
        return vec3.add(origin, vec3.mult(direction, t));
    }
}