package io.cjhosken.javaraytracerapp.rendering.paver.data.base;

import io.cjhosken.javaraytracerapp.rendering.core.Vec3;
import io.cjhosken.javaraytracerapp.rendering.paver.data.shaders.PaverDiffuse;
import io.cjhosken.javaraytracerapp.rendering.paver.data.shaders.PaverShader;

public class PaverObject {
    protected String name;
    protected Vec3 center;
    protected Vec3 rotation;
    protected Vec3 scale;
    protected Vec3 normal;
    protected double t;
    protected PaverShader shader = new PaverDiffuse();

    public PaverObject(String name) {
        this.name = name;
        this.center = new Vec3();
        this.rotation = new Vec3();
        this.scale = new Vec3(1, 1, 1);
    }

    public PaverObject(String name, Vec3 center) {
        this.name = name;
        this.center = center;
        this.rotation = new Vec3();
        this.scale = new Vec3(1, 1, 1);
    }

    public PaverShader shader() {
        return shader;
    }

    public String name() {
        return name;
    }

    public Vec3 center() {
        return center;
    }

    public Vec3 rotation() {
        return rotation;
    }

    public Vec3 scale() {
        return scale;
    }

    public Vec3 normal() {
        return normal;
    }

    public double t() {
        return t;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setCenter(Vec3 center) {
        this.center = center;
    }

    public void setRotation(Vec3 rotation) {
        this.rotation = rotation;
    }

    public void setScale(Vec3 scale) {
        this.scale = scale;
    }

    public void translate(Vec3 t) {
        this.center = Vec3.add(this.center, t);
    }

    public void rotate(Vec3 r) {
        this.rotation = Vec3.add(this.rotation, r);
    }

    public void scale(Vec3 s) {
        this.scale = Vec3.mult(this.scale, s);
    }

    public double intersect(Ray ray) {
        return -1;
    }
}