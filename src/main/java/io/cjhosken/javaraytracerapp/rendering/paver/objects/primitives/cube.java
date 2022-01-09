package io.cjhosken.javaraytracerapp.rendering.paver.objects.primitives;

import io.cjhosken.javaraytracerapp.rendering.paver.base.*;
import io.cjhosken.javaraytracerapp.rendering.paver.shaders.*;

public class cube extends meshobject {
    protected double width = 1;
    protected double length = 1;
    protected double height = 1;

    public cube(String name) {
        super(name);
        updateMesh();
    }

    public cube(String name, double l) {
        super(name);
        this.width = l;
        this.length = l;
        this.height = l;
        updateMesh();
    }

    public cube(String name, double l, shader shader) {
        super(name, shader);
        this.width = l;
        this.length = l;
        this.height = l;
        updateMesh();
    }

    public cube(String name, vec3 center, double w, double l, double h) {
        super(name, center);
        this.width = w;
        this.length = l;
        this.height = h;
        updateMesh();
    }

    public cube(String name, vec3 center, double w, double l, double h, shader shader) {
        super(name, center, shader);
        this.width = w;
        this.length = l;
        this.height = h;
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

    public double height() {
        return this.height;
    }

    public void setHeight(double h) {
        this.height = h;
        updateMesh();
    }

    public void updateMesh() {
        face[] b = {
            new face("f_top", 
                new vec3(-1, 1, 1),
                new vec3(-1, 1, -1),
                new vec3(1, 1, -1),
                new vec3(1, 1, 1)
            ),
            new face("f_bottom", 
                new vec3(-1, -1, 1),
                new vec3(-1, -1, -1),
                new vec3(1, -1, -1),
                new vec3(1, -1, 1)
            ),
            new face("f_back", 
                new vec3(-1, 1, -1),
                new vec3(1, 1, -1),
                new vec3(1, -1, -1),
                new vec3(-1, -1, -1)
            ),
            new face("f_front", 
                new vec3(-1, 1, 1),
                new vec3(1, 1, 1),
                new vec3(1, -1, 1),
                new vec3(-1, -1, 1)
            ),
            new face("f_left", 
                new vec3(-1, 1, -1),
                new vec3(-1, 1, 1),
                new vec3(-1, -1, 1),
                new vec3(-1, -1, -1)
            ),
            new face("f_right", 
                new vec3(1, 1, -1),
                new vec3(1, 1, 1),
                new vec3(1, -1, 1),
                new vec3(1, -1, -1)
            )
        };

        for (face fac : b) {
            for (tri tri : fac.tris()) {
                for (vec3 pnt : tri.points()) {
                    pnt.x *= width;
                    pnt.y *= height;
                    pnt.z *= length; 
                }
            }
        }

        mesh = b;
    }
}