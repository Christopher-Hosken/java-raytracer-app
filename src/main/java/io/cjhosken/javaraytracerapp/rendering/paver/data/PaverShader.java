package io.cjhosken.javaraytracerapp.rendering.paver.data;

import io.cjhosken.javaraytracerapp.core.Ray;
import io.cjhosken.javaraytracerapp.core.Vector3d;
import io.cjhosken.javaraytracerapp.jrs.datatypes.JRSShader;

public class PaverShader {
    protected String name;
    protected Vector3d color = new Vector3d(1, 1, 1);
    protected double roughness = 0.5;

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

    public static PaverShader fromJRS(JRSShader shader) {
        PaverShader paverShader = new PaverShader();
        paverShader.setName(shader.name());
        paverShader.setColor(shader.color());
        paverShader.setRoughness(shader.roughness());
        return paverShader;
    }
}
