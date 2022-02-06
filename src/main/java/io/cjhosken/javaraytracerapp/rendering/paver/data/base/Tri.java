package io.cjhosken.javaraytracerapp.rendering.paver.data.base;

import io.cjhosken.javaraytracerapp.rendering.core.Vec3;

public class Tri {
    private Vec3 A,B,C;
    private Vec3 normal;
    private double t;

    public Tri() {}

    public Tri(Vec3 i, Vec3 j, Vec3 k) {
        A=i; B=j; C=k;
        Vec3 BC = Vec3.sub(B, C);
        Vec3 CA = Vec3.sub(C, A);
        normal = Vec3.cross(BC, CA).unitVector();
    }

    public Vec3[] points() {
        return new Vec3[] {A, B, C};
    }

    public Vec3 normal() {
        return normal;
    }

    public double t() {
        return t;
    }

    public double intersect(Ray ray) {
        Vec3 AB = Vec3.sub(B, A);
        Vec3 CB = Vec3.sub(B, C);
        Vec3 AC = Vec3.sub(C, A);
        Vec3 P = Vec3.cross(ray.direction(), AC);
        double det = Vec3.dot(AB, P);

        if (Math.abs(det) < 0.0000001) return -1;

        double invDet = 1 / det;

        Vec3 tuv = Vec3.sub(ray.origin(), A);
        double u = Vec3.dot(tuv, P) * invDet;
        if (u < 0 || u > 1) return -1;

        Vec3 q = Vec3.cross(tuv, AB);
        double v = Vec3.dot(ray.direction(), q) * invDet;
        if (v < 0 || u + v > 1) return -1;

        t = Vec3.dot(AC, q) * invDet;

        normal = Vec3.cross(CB, AC).unitVector();
        
        if (Vec3.dot(normal, ray.direction()) > 0) {
            normal = normal.invert().unitVector();
        }
        
        return t;
    }
}