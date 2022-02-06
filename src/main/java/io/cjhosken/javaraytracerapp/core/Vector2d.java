package io.cjhosken.javaraytracerapp.core;

import org.json.JSONArray;
import org.json.JSONObject;

public class Vector2d {
    public double x,y;

    public Vector2d() {
        x=y=0;
    }

    public Vector2d(double i, double j) {
        x=i; y=j;
    }

    public double lengthSquared() {
        return x*x + y*y;
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public String toString() {
        return String.format("(%f, %f)", x, y);
    }

    public Vector2d unitVector() {
        return div(this, length());
    }

    public Vector2d sqrt() {
        return new Vector2d(
            Math.sqrt(x),
            Math.sqrt(y)
        );
    }

    public void clamp(double min, double max) {
        if (x < min) x = min; if (x > max) x = max;
        if (y < min) y = min; if (y > max) y = max;
    }

    public Vector2d invert() {
        return Vector2d.mult(-1, this);
    }

    public static Vector2d add(Vector2d v, Vector2d u) {
        return new Vector2d(
            v.x + u.x,
            v.y + u.y
        );
    }

    public static Vector2d sub(Vector2d v, Vector2d u) {
        return new Vector2d(
            v.x - u.x,
            v.y - u.y
        );
    }

    public static Vector3d mult(Vector3d v, Vector3d u) {
        return new Vector3d(
            v.x * u.x,
            v.y * u.y,
            v.z * u.z
        );
    }

    public static Vector2d mult(Vector2d v, double d) {
        return new Vector2d(
            v.x * d,
            v.y * d
        );
    }

    public static Vector2d mult(double d, Vector2d v) {
        return mult(v, d);
    }

    public static Vector2d div(Vector2d v, double d) {
        return new Vector2d(
            v.x / d,
            v.y / d
        );
    }

    public static double dot(Vector2d v, Vector2d u) {
        return v.x * u.x + v.y * u.y;
    }

    public static double distance(Vector2d v, Vector2d u) {
        return Vector2d.sub(v, u).length();
    }

    public static double random(double min, double max) {
        return min + Math.random() * (max - min);
    }

    public static Vector2d randomVec() {
        return new Vector2d(
            Math.random(),
            Math.random()
        );
    }

    public static Vector2d randomVec(double min, double max) {
        return new Vector2d(
            random(min, max),
            random(min, max)
        );
    }

    public void fromJSONArray(JSONArray jrsa) {
        x = jrsa.getDouble(0);
        y = jrsa.getDouble(1);
    }

    public JSONArray toJSONArray() {
        return new JSONArray(new double[] {x, y});
    }
}