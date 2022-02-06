package io.cjhosken.javaraytracerapp.rendering.paver.data.base;

import io.cjhosken.javaraytracerapp.rendering.core.Vec3;

public class Face {
    private String id;
    private Tri[] tris;
    private Vec3 normal;

    public Face(String id) {
        this.id = id;
    }

    public Face(String id, Vec3 a, Vec3 b, Vec3 c) {
        this.id = id;
        Tri A = new Tri(a, b, c);
        tris = new Tri[] {A};
    }

    public Face(String id, Vec3 a, Vec3 b, Vec3 c, Vec3 d) {
        this.id = id;
        Tri A = new Tri(a, b, c);
        Tri B = new Tri(a, c, d);

        tris = new Tri[] {B, A};
    }

    public Tri[] tris() {
        return tris;
    }

    public String id() {
        return id;
    }

    public Vec3 normal() {
        return normal;
    }

    public void setNormal(Vec3 n) {
        this.normal = n;
    }

    public double intersect(Ray ray) {
        boolean hit = false;
        double t = Double.POSITIVE_INFINITY;
        if (tris == null || tris.length == 0) return -1.0;

        for (Tri Tri : tris) {
            double t0 = Tri.intersect(ray);
            if (t0 < t && t0 > 0) {
                t = t0;
                this.normal = Tri.normal();
                hit = true;
            }
        }

        if (hit) {
            return t;
        }

        return -1;
    }
}