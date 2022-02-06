package io.cjhosken.javaraytracerapp.rendering.paver.data.base;

import io.cjhosken.javaraytracerapp.rendering.core.Vec3;

public class PaverMeshObject extends PaverObject {
    protected Face[] mesh;

    public PaverMeshObject(String name) {
        super(name);
    }

    public PaverMeshObject(String name, Vec3 center) {
        super(name, center);
    }


    public Face[] mesh() {
        return mesh;
    }

    public void setMesh(Face[] mesh) {
        this.mesh = mesh;
    }

    public double intersect(Ray ray) {
        Tri tc = new Tri();
        boolean hit = false;
        t = Double.POSITIVE_INFINITY;

        if (mesh != null && mesh.length > 0) {
            for (Face f : mesh) {
                for (Tri tri : f.tris()) {
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