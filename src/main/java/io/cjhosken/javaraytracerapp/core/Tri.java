package io.cjhosken.javaraytracerapp.core;

public class Tri {
    private Vector3d A, B, C;
    private Vector3d normal;
    private double t;

    public Tri() {
    }

    public Tri(Vector3d i, Vector3d j, Vector3d k) {
        A = i;
        B = j;
        C = k;
        Vector3d BC = Vector3d.sub(B, C);
        Vector3d CA = Vector3d.sub(C, A);
        normal = Vector3d.cross(BC, CA).unitVector();
    }

    public Vector3d[] points() {
        return new Vector3d[] { A, B, C };
    }

    public Vector3d normal() {
        return normal;
    }

    public double t() {
        return t;
    }

    public double intersect(Ray ray) {
        Vector3d AB = Vector3d.sub(B, A);
        Vector3d CB = Vector3d.sub(B, C);
        Vector3d AC = Vector3d.sub(C, A);
        Vector3d P = Vector3d.cross(ray.direction(), AC);
        double det = Vector3d.dot(AB, P);

        if (Math.abs(det) < 0.0000001)
            return -1;

        double invDet = 1 / det;

        Vector3d tuv = Vector3d.sub(ray.origin(), A);
        double u = Vector3d.dot(tuv, P) * invDet;
        if (u < 0 || u > 1)
            return -1;

        Vector3d q = Vector3d.cross(tuv, AB);
        double v = Vector3d.dot(ray.direction(), q) * invDet;
        if (v < 0 || u + v > 1)
            return -1;

        t = Vector3d.dot(AC, q) * invDet;

        normal = Vector3d.cross(CB, AC).unitVector();

        if (Vector3d.dot(normal, ray.direction()) > 0) {
            normal = normal.invert().unitVector();
        }

        return t;
    }
}