package io.cjhosken.javaraytracerapp.rendering.paver.objects.primitives;

import io.cjhosken.javaraytracerapp.rendering.paver.base.*;
import io.cjhosken.javaraytracerapp.rendering.paver.shaders.*;

public class sphere extends object {
    protected double radius = 0.5;

    public sphere(String name) {
        super(name);
    }

    public sphere(String name, shader shader) {
        super(name, shader);
    }

    public sphere(String name, double radius) {
        super(name);
        this.radius = radius;
    }

    public sphere(String name, double radius, shader shader) {
        super(name, shader);
        this.radius = radius;
    }

    public sphere(String name, vec3 center, double radius) {
        super(name, center);
        this.radius = radius;
    }

    public sphere(String name, vec3 center, double radius, shader shader) {
        super(name, center, shader);
        this.radius = radius;
    }
    
    public double radius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double intersect(ray ray) {
        vec3 oc = vec3.sub(ray.origin(), center);
        double a = ray.direction().lengthSquared();
        double half_b = vec3.dot(oc, ray.direction());
        double c = oc.lengthSquared() - radius*radius;
        double disc = half_b*half_b - a*c;
        
        if (disc < 0) return -1;
        disc = Math.sqrt(disc);

        double t0 = (-half_b - disc) / a;
        double t1 = (-half_b + disc) / a;

        if (t0 < 0) {
            if (t1 < 0) return -1;
            t = t1;
        } else if (t1 < 0) {
            t = t0;
        } else {
            t = t1;
            if (t0 < t1) t = t0;
        }

        this.normal = vec3.div(vec3.sub(ray.at(t), center), radius).unitVector();
        return t;
    }
}