package io.cjhosken.javaraytracerapp.rendering.paver.data;

import io.cjhosken.javaraytracerapp.core.Ray;
import io.cjhosken.javaraytracerapp.core.Vector3d;

public class PaverShader {
    protected String name;
    protected Vector3d color;
    protected double roughness;

    public PaverShader() {
    }

    public String name() {
        return name;
    }

    public Vector3d color() {
        return color;
    }

    public double roughness() {
        return roughness;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(Vector3d color) {
        this.color = color;
    }

    public void setRoughness(double roughness) {
        this.roughness = roughness;
    }

    public Ray scatter(PaverObject object, Ray ray) {
        return new Ray(ray.at(object.tmpT()), object.tmpNormal());
    }
}
