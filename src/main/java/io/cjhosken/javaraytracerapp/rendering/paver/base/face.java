package io.cjhosken.javaraytracerapp.rendering.paver.base;

public class face {
    private String id;
    private tri[] tris;
    private vec3 normal;

    public face(String id) {
        this.id = id;
    }

    public face(String id, vec3 a, vec3 b, vec3 c) {
        this.id = id;
        tri A = new tri(a, b, c);
        tris = new tri[] {A};
    }

    public face(String id, vec3 a, vec3 b, vec3 c, vec3 d) {
        this.id = id;
        tri A = new tri(a, b, c);
        tri B = new tri(a, c, d);

        tris = new tri[] {B, A};
    }

    public tri[] tris() {
        return tris;
    }

    public String id() {
        return id;
    }

    public vec3 normal() {
        return normal;
    }

    public void setNormal(vec3 n) {
        this.normal = n;
    }

    public double intersect(ray ray) {
        boolean hit = false;
        double t = Double.POSITIVE_INFINITY;
        if (tris == null || tris.length == 0) return -1.0;

        for (tri tri : tris) {
            double t0 = tri.intersect(ray);
            if (t0 < t && t0 > 0) {
                t = t0;
                this.normal = tri.normal();
                hit = true;
            }
        }

        if (hit) {
            return t;
        }

        return -1;
    }
}