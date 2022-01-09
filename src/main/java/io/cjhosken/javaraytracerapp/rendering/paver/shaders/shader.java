package io.cjhosken.javaraytracerapp.rendering.paver.shaders;

import io.cjhosken.javaraytracerapp.rendering.paver.base.*;

public class shader {
    protected vec3 color = new vec3(1, 1, 1);
    protected double spec = 0.5;

    public shader() {}

    public shader(vec3 color) {
        this.color = color;
    }

    public shader(vec3 color, double spec) {
        this.color = color;
        this.spec = spec;
    }

    public vec3 color() {
        return color;
    }

    public double spec() {
        return spec;
    }

    public void setSpec(double s) {
        this.spec = s;
    }

    public void setColor(vec3 c) {
        this.color = c;
    }

    public ray scatter(object obj, ray ray) {
        return new ray(ray.at(obj.t()), obj.normal());
    }
}