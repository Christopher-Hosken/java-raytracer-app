package io.cjhosken.javaraytracerapp.rendering.paver.data.shaders;

import io.cjhosken.javaraytracerapp.rendering.core.Vec3;
import io.cjhosken.javaraytracerapp.rendering.paver.data.base.PaverObject;
import io.cjhosken.javaraytracerapp.rendering.paver.data.base.Ray;

public class PaverShader {
    protected Vec3 color = new Vec3(1, 1, 1);
    protected double spec = 0.5;

    public PaverShader() {}

    public PaverShader(Vec3 color) {
        this.color = color;
    }

    public PaverShader(Vec3 color, double spec) {
        this.color = color;
        this.spec = spec;
    }

    public Vec3 color() {
        return color;
    }

    public double spec() {
        return spec;
    }

    public void setSpec(double s) {
        this.spec = s;
    }

    public void setColor(Vec3 c) {
        this.color = c;
    }

    public Ray scatter(PaverObject obj, Ray ray) {
        return new Ray(ray.at(obj.t()), obj.normal());
    }
}