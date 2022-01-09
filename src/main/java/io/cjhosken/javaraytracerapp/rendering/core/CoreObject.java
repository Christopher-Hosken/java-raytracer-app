package io.cjhosken.javaraytracerapp.rendering.core;

import com.jogamp.opengl.GL2;

import io.cjhosken.javaraytracerapp.rendering.paver.base.*;

public class CoreObject {
    protected String name;
    protected int id;
    protected vec3 center;
    protected float[] verts;
    protected int[] indices;
    protected float[] colors;

    public CoreObject(String name, int id, vec3 center) {
        this.name = name;
        this.id = id;
        this.center = center;
    }

    public void draw(GL2 gl) {}

    public void render() {}

    public float[] verts() {return verts;}

    public int[] indices() {return indices;}

    public float[] colors() {return colors;}

    public int ID() {return this.id;}
}