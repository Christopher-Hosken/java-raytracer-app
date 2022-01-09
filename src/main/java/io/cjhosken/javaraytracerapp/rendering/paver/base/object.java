package io.cjhosken.javaraytracerapp.rendering.paver.base;

import io.cjhosken.javaraytracerapp.rendering.paver.shaders.*;

public class object {
    protected String name;
    protected vec3 center;
    protected vec3 rotation;
    protected vec3 scale;
    protected shader shader;
    protected vec3 normal;
    protected double t;

    public object(String name) {
        this.name = name;
        this.center = new vec3();
        this.rotation = new vec3();
        this.scale = new vec3(1, 1, 1);
        this.shader = new shader();
    }

    public object(String name, shader shader) {
        this.name = name;
        this.shader = shader;
        this.center = new vec3();
        this.rotation = new vec3();
        this.scale = new vec3(1, 1, 1);
    }

    public object(String name, vec3 center) {
        this.name = name;
        this.center = center;
        this.rotation = new vec3();
        this.scale = new vec3(1, 1, 1);
        this.shader = new shader();
    }

    public object(String name, vec3 center, shader shader) {
        this.name = name;
        this.center = center;
        this.rotation = new vec3();
        this.scale = new vec3(1, 1, 1);
        this.shader = shader;
    }

    public String name() {
        return name;
    }

    public vec3 center() {
        return center;
    }

    public vec3 rotation() {
        return rotation;
    }

    public vec3 scale() {
        return scale;
    }

    public shader shader() {
        return shader;
    }

    public vec3 normal() {
        return normal;
    }

    public double t() {
        return t;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setShader(shader shader) {
        this.shader = shader;
    }

    public void setCenter(vec3 center) {
        this.center = center;
    }

    public void setRotation(vec3 rotation) {
        this.rotation = rotation;
    }

    public void setScale(vec3 scale) {
        this.scale = scale;
    }

    public void translate(vec3 t) {
        this.center = vec3.add(this.center, t);
    }

    public void rotate(vec3 r) {
        this.rotation = vec3.add(this.rotation, r);
    }

    public void scale(vec3 s) {
        this.scale = vec3.mult(this.scale, s);
    }

    public double intersect(ray ray) {
        return -1;
    }
}