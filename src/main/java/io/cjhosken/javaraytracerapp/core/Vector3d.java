package io.cjhosken.javaraytracerapp.core;

import org.json.JSONArray;
import org.json.JSONObject;

public class Vector3d {
    public double x,y,z;

    public Vector3d() {
        x=y=z=0;
    }

    public Vector3d(double i, double j, double k) {
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

    public Vector3d unitVector() {
        return div(this, length());
    }

    public Vector3d sqrt() {
        return new Vector3d(
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

    public Vector3d invert() {
        return Vector3d.mult(-1, this);
    }

    public static Vector3d add(Vector3d v, Vector3d u) {
        return new Vector3d(
            v.x + u.x,
            v.y + u.y,
            v.z + u.z
        );
    }

    public static Vector3d sub(Vector3d v, Vector3d u) {
        return new Vector3d(
            v.x - u.x,
            v.y - u.y,
            v.z - u.z
        );
    }

    public static Vector3d mult(Vector3d v, Vector3d u) {
        return new Vector3d(
            v.x * u.x,
            v.y * u.y,
            v.z * u.z
        );
    }

    public static Vector3d mult(Vector3d v, double d) {
        return new Vector3d(
            v.x * d,
            v.y * d,
            v.z * d
        );
    }

    public static Vector3d mult(double d, Vector3d v) {
        return mult(v, d);
    }

    public static Vector3d div(Vector3d v, double d) {
        return new Vector3d(
            v.x / d,
            v.y / d,
            v.z / d
        );
    }

    public static double dot(Vector3d v, Vector3d u) {
        return v.x * u.x + v.y * u.y + v.z * u.z;
    }

    public static Vector3d cross(Vector3d v, Vector3d u) {
        return new Vector3d(
            v.y * u.z - v.z * u.y,
            v.z * u.x - v.x * u.z,
            v.x * u.y - v.y * u.x
        );
    }

    public static double distance(Vector3d v, Vector3d u) {
        return Vector3d.sub(v, u).length();
    }

    public static double random(double min, double max) {
        return min + Math.random() * (max - min);
    }

    public static Vector3d randomVec() {
        return new Vector3d(
            Math.random(),
            Math.random(),
            Math.random()
        );
    }

    public static Vector3d randomVec(double min, double max) {
        return new Vector3d(
            random(min, max),
            random(min, max),
            random(min, max)
        );
    }

    public static Vector3d randomInUnitSphere() {
        while (true) {
            Vector3d v = randomVec(1, -1);
            if (v.lengthSquared() < 1) return v;
        }
    }

    public static Vector3d randomInUnitDisc() {
        while(true) {
            Vector3d v = new Vector3d(random(-1, 1), random(-1, 1), 0);
            if (v.lengthSquared() < 1) return v;
        }
    }

    public static Vector3d scatter(Vector3d point, Vector3d normal) {
        return Vector3d.add(
            Vector3d.add(point, normal),
            randomInUnitSphere()
        );
    }

    public static Vector3d reflect(Vector3d direction, Vector3d normal, double roughness) {
        return Vector3d.add(Vector3d.sub(
            direction,
            Vector3d.mult(2 * Vector3d.dot(direction, normal), normal)
        ),
        Vector3d.mult(randomInUnitSphere(), roughness)
        );
    }

    public void fromJSONArray(JSONArray jrsa) {
        x = jrsa.getDouble(0);
        y = jrsa.getDouble(1);
        z = jrsa.getDouble(2);
    }

    public JSONArray toJSONArray() {
        return new JSONArray(new double[] {x, y, z});
    }
}