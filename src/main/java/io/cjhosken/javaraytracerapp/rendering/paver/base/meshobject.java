package io.cjhosken.javaraytracerapp.rendering.paver.base;

import io.cjhosken.javaraytracerapp.rendering.paver.shaders.*;

public class meshobject extends object {
    protected face[] mesh;

    public meshobject(String name) {
        super(name);
    }

    public meshobject(String name, shader shader) {
        super(name, shader);
    }

    public meshobject(String name, vec3 center) {
        super(name, center);
    }

    public meshobject(String name, vec3 center, shader shader) {
        super(name, center, shader);
    }

    public face[] mesh() {
        return mesh;
    }

    public void setMesh(face[] mesh) {
        this.mesh = mesh;
    }

    public double intersect(ray ray) {
        tri tc = new tri();
        boolean hit = false;
        t = Double.POSITIVE_INFINITY;

        if (mesh != null && mesh.length > 0) {
            for (face f : mesh) {
                for (tri tri : f.tris()) {
                    double t0 = tri.intersect(ray);
                    if (t0 > 0.0001 && t0 < t) {
                        t = t0;
                        tc = tri;
                        hit = true;
                    }
                }
            }
        }



        if (hit) {
            this.normal = tc.normal();
            //System.out.println(this.normal);
            return t;
        }

        return -1;
    }
}