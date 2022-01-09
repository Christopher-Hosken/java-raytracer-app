package io.cjhosken.javaraytracerapp.rendering.paver.objects.primitives;

import io.cjhosken.javaraytracerapp.rendering.paver.base.*;
import io.cjhosken.javaraytracerapp.rendering.paver.shaders.*;

public class plane extends meshobject {
    protected double width = 1;
    protected double length = 1;

    public plane(String name) {
        super(name);
        updateMesh();
    }

    public plane(String name, double l) {
        super(name);
        this.width = l;
        this.length = l;
        updateMesh();
    }

    public plane(String name, double l, shader shader) {
        super(name, shader);
        this.width = l;
        this.length = l;
        updateMesh();
    }

    public plane(String name, vec3 center, double w, double l) {
        super(name, center);
        this.width = w;
        this.length = l;
        updateMesh();
    }

    public plane(String name, vec3 center, double w, double l, shader shader) {
        super(name, center, shader);
        this.width = w;
        this.length = l;
        updateMesh();
    }
    
    public double width() {
        return this.width;
    }

    public void setWidth(double w) {
        this.width = w;
        updateMesh();
    }

    public double length() {
        return this.length;
    }

    public void setLength(double l) {
        this.length = l;
        updateMesh();
    }

    public void updateMesh() {
        face[] b = {
            new face("f0", 
                new vec3(-1, 0, 1),
                new vec3(-1, 0, -1),
                new vec3(1, 0, -1),
                new vec3(1, 0, 1)
            )
        };

        for (tri tri : b[0].tris()) {
            for (vec3 pnt : tri.points()) {
                pnt.x *= width;
                pnt.z *= length;
            }
        }

        mesh = b;
    }
}