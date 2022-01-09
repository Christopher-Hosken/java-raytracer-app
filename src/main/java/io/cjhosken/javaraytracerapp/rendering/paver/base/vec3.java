package io.cjhosken.javaraytracerapp.rendering.paver.base;

public class vec3 {
    public double x,y,z;

    public vec3() {
        x=y=z=0;
    }

    public vec3(double i, double j, double k) {
        x=i; y=j; z=k;
    }

    public double lengthSquared() {
        return x*x + y*y + z*z;
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public String toString() {
        return String.format("(%f, %f, %f)", x, y, z);
    }

    public vec3 unitVector() {
        return div(this, length());
    }

    public vec3 sqrt() {
        return new vec3(
            Math.sqrt(x),
            Math.sqrt(y),
            Math.sqrt(z)
        );
    }

    public void clamp(double min, double max) {
        if (x < min) x = min; if (x > max) x = max;
        if (y < min) y = min; if (y > max) y = max;
        if (z < min) z = min; if (z > max) z = max;
    }

    public vec3 invert() {
        return vec3.mult(-1, this);
    }

    public static vec3 add(vec3 v, vec3 u) {
        return new vec3(
            v.x + u.x,
            v.y + u.y,
            v.z + u.z
        );
    }

    public static vec3 sub(vec3 v, vec3 u) {
        return new vec3(
            v.x - u.x,
            v.y - u.y,
            v.z - u.z
        );
    }

    public static vec3 mult(vec3 v, vec3 u) {
        return new vec3(
            v.x * u.x,
            v.y * u.y,
            v.z * u.z
        );
    }

    public static vec3 mult(vec3 v, double d) {
        return new vec3(
            v.x * d,
            v.y * d,
            v.z * d
        );
    }

    public static vec3 mult(double d, vec3 v) {
        return mult(v, d);
    }

    public static vec3 div(vec3 v, double d) {
        return new vec3(
            v.x / d,
            v.y / d,
            v.z / d
        );
    }

    public static double dot(vec3 v, vec3 u) {
        return v.x * u.x + v.y * u.y + v.z * u.z;
    }

    public static vec3 cross(vec3 v, vec3 u) {
        return new vec3(
            v.y * u.z - v.z * u.y,
            v.z * u.x - v.x * u.z,
            v.x * u.y - v.y * u.x
        );
    }

    public static double distance(vec3 v, vec3 u) {
        return vec3.sub(v, u).length();
    }

    public static double random(double min, double max) {
        return min + Math.random() * (max - min);
    }

    public static vec3 randomVec() {
        return new vec3(
            Math.random(),
            Math.random(),
            Math.random()
        );
    }

    public static vec3 randomVec(double min, double max) {
        return new vec3(
            random(min, max),
            random(min, max),
            random(min, max)
        );
    }

    public static vec3 randomInUnitSphere() {
        while (true) {
            vec3 v = randomVec(1, -1);
            if (v.lengthSquared() < 1) return v;
        }
    }

    public static vec3 randomInUnitDisc() {
        while(true) {
            vec3 v = new vec3(random(-1, 1), random(-1, 1), 0);
            if (v.lengthSquared() < 1) return v;
        }
    }

    public static vec3 scatter(vec3 point, vec3 normal) {
        return vec3.add(
            vec3.add(point, normal),
            randomInUnitSphere()
        );
    }

    public static vec3 reflect(vec3 direction, vec3 normal, double roughness) {
        return vec3.add(vec3.sub(
            direction,
            vec3.mult(2 * vec3.dot(direction, normal), normal)
        ),
        vec3.mult(randomInUnitSphere(), roughness)
        );
    }
}