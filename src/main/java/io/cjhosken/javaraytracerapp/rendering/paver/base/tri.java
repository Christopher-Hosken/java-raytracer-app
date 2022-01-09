package io.cjhosken.javaraytracerapp.rendering.paver.base;

public class tri {
    private vec3 A,B,C;
    private vec3 normal;
    private double t;

    public tri() {}

    public tri(vec3 i, vec3 j, vec3 k) {
        A=i; B=j; C=k;
        vec3 BC = vec3.sub(B, C);
        vec3 CA = vec3.sub(C, A);
        normal = vec3.cross(BC, CA).unitVector();
    }

    public vec3[] points() {
        return new vec3[] {A, B, C};
    }

    public vec3 normal() {
        return normal;
    }

    public double t() {
        return t;
    }

    public double intersect(ray ray) {
        vec3 AB = vec3.sub(B, A);
        vec3 BC = vec3.sub(B, C);
        vec3 AC = vec3.sub(C, A);
        vec3 P = vec3.cross(ray.direction(), AC);
        double det = vec3.dot(AB, P);

        if (Math.abs(det) < 0.0000001) return -1;

        double invDet = 1 / det;

        vec3 tuv = vec3.sub(ray.origin(), A);
        double u = vec3.dot(tuv, P) * invDet;
        if (u < 0 || u > 1) return -1;

        vec3 q = vec3.cross(tuv, AB);
        double v = vec3.dot(ray.direction(), q) * invDet;
        if (v < 0 || u + v > 1) return -1;

        t = vec3.dot(AC, q) * invDet;

        normal = vec3.cross(BC, AC).unitVector();
        
        if (vec3.dot(normal, ray.direction()) > 0) {
            normal = normal.invert().unitVector();
        }
        
        return t;
    }
}