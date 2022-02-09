package io.cjhosken.javaraytracerapp.core;

public class Face {
    private Tri[] tris;
    private Vector3d normal;

    public Face() {
    }

    public Face(String id, Vector3d a, Vector3d b, Vector3d c) {
        Tri A = new Tri(a, b, c);
        tris = new Tri[] { A };
    }

    public Face(Vector3d a, Vector3d b, Vector3d c, Vector3d d) {
        Tri A = new Tri(a, b, c);
        Tri B = new Tri(a, c, d);

        tris = new Tri[] { B, A };
    }

    public Tri[] tris() {
        return tris;
    }

    public Vector3d normal() {
        return normal;
    }

    public void setNormal(Vector3d n) {
        this.normal = n;
    }

    public double intersect(Ray ray) {
        boolean hit = false;
        double t = Double.POSITIVE_INFINITY;
        if (tris == null || tris.length == 0)
            return -1.0;

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