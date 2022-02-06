package io.cjhosken.javaraytracerapp.rendering.paver.data.objects.primitives;

import io.cjhosken.javaraytracerapp.rendering.core.Vec3;
import io.cjhosken.javaraytracerapp.rendering.paver.data.base.PaverObject;
import io.cjhosken.javaraytracerapp.rendering.paver.data.base.Ray;

public class PaverSphere extends PaverObject {
    protected double radius = 0.5;

    public PaverSphere(String name) {
        super(name);
    }

    public PaverSphere(String name, double radius) {
        super(name);
        this.radius = radius;
    }

    public PaverSphere(String name, Vec3 center, double radius) {
        super(name, center);
        this.radius = radius;
    }
    
    public double radius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double intersect(Ray ray) {
        Vec3 oc = Vec3.sub(ray.origin(), center);
        double a = ray.direction().lengthSquared();
        double half_b = Vec3.dot(oc, ray.direction());
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

        this.normal = Vec3.div(Vec3.sub(ray.at(t), center), radius).unitVector();
        return t;
    }
}