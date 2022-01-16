package io.cjhosken.javaraytracerapp.rendering.core;

public class Vec3 {
    public double x,y,z;

    public Vec3() {
        x=y=z=0;
    }

    public Vec3(double i, double j, double k) {
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

    public Vec3 unitVector() {
        return div(this, length());
    }

    public Vec3 sqrt() {
        return new Vec3(
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

    public Vec3 invert() {
        return Vec3.mult(-1, this);
    }

    public static Vec3 add(Vec3 v, Vec3 u) {
        return new Vec3(
            v.x + u.x,
            v.y + u.y,
            v.z + u.z
        );
    }

    public static Vec3 sub(Vec3 v, Vec3 u) {
        return new Vec3(
            v.x - u.x,
            v.y - u.y,
            v.z - u.z
        );
    }

    public static Vec3 mult(Vec3 v, Vec3 u) {
        return new Vec3(
            v.x * u.x,
            v.y * u.y,
            v.z * u.z
        );
    }

    public static Vec3 mult(Vec3 v, double d) {
        return new Vec3(
            v.x * d,
            v.y * d,
            v.z * d
        );
    }

    public static Vec3 mult(double d, Vec3 v) {
        return mult(v, d);
    }

    public static Vec3 div(Vec3 v, double d) {
        return new Vec3(
            v.x / d,
            v.y / d,
            v.z / d
        );
    }

    public static double dot(Vec3 v, Vec3 u) {
        return v.x * u.x + v.y * u.y + v.z * u.z;
    }

    public static Vec3 cross(Vec3 v, Vec3 u) {
        return new Vec3(
            v.y * u.z - v.z * u.y,
            v.z * u.x - v.x * u.z,
            v.x * u.y - v.y * u.x
        );
    }

    public static double distance(Vec3 v, Vec3 u) {
        return Vec3.sub(v, u).length();
    }

    public static double random(double min, double max) {
        return min + Math.random() * (max - min);
    }

    public static Vec3 randomVec() {
        return new Vec3(
            Math.random(),
            Math.random(),
            Math.random()
        );
    }

    public static Vec3 randomVec(double min, double max) {
        return new Vec3(
            random(min, max),
            random(min, max),
            random(min, max)
        );
    }

    public static Vec3 randomInUnitSphere() {
        while (true) {
            Vec3 v = randomVec(1, -1);
            if (v.lengthSquared() < 1) return v;
        }
    }

    public static Vec3 randomInUnitDisc() {
        while(true) {
            Vec3 v = new Vec3(random(-1, 1), random(-1, 1), 0);
            if (v.lengthSquared() < 1) return v;
        }
    }

    public static Vec3 scatter(Vec3 point, Vec3 normal) {
        return Vec3.add(
            Vec3.add(point, normal),
            randomInUnitSphere()
        );
    }

    public static Vec3 reflect(Vec3 direction, Vec3 normal, double roughness) {
        return Vec3.add(Vec3.sub(
            direction,
            Vec3.mult(2 * Vec3.dot(direction, normal), normal)
        ),
        Vec3.mult(randomInUnitSphere(), roughness)
        );
    }
}