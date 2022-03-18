package io.cjhosken.javaraytracerapp.rendering.paver.data.objects;

import io.cjhosken.javaraytracerapp.core.Ray;
import io.cjhosken.javaraytracerapp.core.Vector3d;
import io.cjhosken.javaraytracerapp.jrs.datatypes.JRSObject;
import io.cjhosken.javaraytracerapp.rendering.paver.data.PaverObject;
import io.cjhosken.javaraytracerapp.rendering.paver.data.PaverShader;

public class PaverSphere extends PaverObject {
    private double radius = 1;

    public PaverSphere() {
        super("Sphere");
        this.radius = 1;
    }

    public PaverSphere(double radius) {
        super("Sphere");
        this.radius = radius;
    }

    public double radius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double intersect(Ray ray) {
        Vector3d oc = Vector3d.sub(ray.origin(), location);
        double a = ray.direction().lengthSquared();
        double halfB = Vector3d.dot(oc, ray.direction());
        double c = oc.lengthSquared() - radius * radius;
        double disc = halfB * halfB - a * c;

        if (disc < 0) return -1.0;
        disc = Math.sqrt(disc);

        double t0 = (-halfB - disc) / a;
        double t1 = (-halfB + disc) / a;

        if (t0 < 0) {
            if (t1 < 0) return -1;
            tmpT = t1;
        }
        else if (t1 < 0){
            tmpT = t0;
        }
        else {
            tmpT = t1;
            if (t0 < t1) tmpT = t0;
        }

        tmpNormal = Vector3d.div(Vector3d.sub(ray.at(tmpT), location), radius);

        boolean isFrontFacing = Vector3d.dot(ray.direction(), tmpNormal) > 0;
        if (isFrontFacing) {
            tmpNormal = tmpNormal.invert();
        }

        return tmpT;
    }

    public static PaverSphere fromJRS(JRSObject object) {
        PaverSphere paverObject = new PaverSphere();
    
        paverObject.setName(object.name());
    
        paverObject.setLocation(object.location());
        paverObject.setRotation(object.rotation());
        paverObject.setLocation(object.scale());

        paverObject.setRadius(object.radius());
    
        paverObject.setShader(PaverShader.fromJRS(object.shader()));
    
        return paverObject;
      }
}
