package io.cjhosken.javaraytracerapp.rendering.paver.data.base;

import io.cjhosken.javaraytracerapp.rendering.core.Vec3;

public class PaverTriMeshObject extends PaverObject {
    protected Tri[] mesh;

    public PaverTriMeshObject(String name) {
        super(name);
    }

    public PaverTriMeshObject(String name, Vec3 center) {
        super(name, center);
    }

    public Tri[] mesh() {
        return mesh;
    }

    public void setMesh(Tri[] mesh) {
        this.mesh = mesh;
    }

    public double intersect(Ray ray) {
        Tri tc = new Tri();
        boolean hit = false;
        t = Double.POSITIVE_INFINITY;

        if (mesh != null && mesh.length > 0) {
            for (Tri tri : mesh) {
                double t0 = tri.intersect(ray);
                if (t0 > 0.0001 && t0 < t) {
                    t = t0;
                    tc = tri;
                    hit = true;
                }
            }
        }

        if (hit) {
            this.normal = tc.normal();
            // System.out.println(this.normal);
            return t;
        }

        return -1;
    }
}