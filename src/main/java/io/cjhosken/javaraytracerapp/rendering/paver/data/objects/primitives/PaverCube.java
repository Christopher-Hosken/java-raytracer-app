package io.cjhosken.javaraytracerapp.rendering.paver.data.objects.primitives;

import io.cjhosken.javaraytracerapp.rendering.core.Vec3;
import io.cjhosken.javaraytracerapp.rendering.paver.data.base.Face;
import io.cjhosken.javaraytracerapp.rendering.paver.data.base.PaverMeshObject;
import io.cjhosken.javaraytracerapp.rendering.paver.data.base.PaverObject;
import io.cjhosken.javaraytracerapp.rendering.paver.data.base.Tri;

public class PaverCube extends PaverMeshObject {
    protected double width = 2;
    protected double length = 2;
    protected double height = 2;

    public PaverCube(String name) {
        super(name);
        updateMesh();
    }

    public PaverCube(String name, double l) {
        super(name);
        this.width = l;
        this.length = l;
        this.height = l;
        updateMesh();
    }


    public PaverCube(String name, Vec3 center, double w, double l, double h) {
        super(name, center);
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
        Face[] b = {
            new Face("f_top", 
                new Vec3(-1, 1, 1),
                new Vec3(-1, 1, -1),
                new Vec3(1, 1, -1),
                new Vec3(1, 1, 1)
            ),
            new Face("f_bottom", 
                new Vec3(-1, -1, 1),
                new Vec3(-1, -1, -1),
                new Vec3(1, -1, -1),
                new Vec3(1, -1, 1)
            ),
            new Face("f_back", 
                new Vec3(-1, 1, -1),
                new Vec3(1, 1, -1),
                new Vec3(1, -1, -1),
                new Vec3(-1, -1, -1)
            ),
            new Face("f_front", 
                new Vec3(-1, 1, 1),
                new Vec3(1, 1, 1),
                new Vec3(1, -1, 1),
                new Vec3(-1, -1, 1)
            ),
            new Face("f_left", 
                new Vec3(-1, 1, -1),
                new Vec3(-1, 1, 1),
                new Vec3(-1, -1, 1),
                new Vec3(-1, -1, -1)
            ),
            new Face("f_right", 
                new Vec3(1, 1, -1),
                new Vec3(1, 1, 1),
                new Vec3(1, -1, 1),
                new Vec3(1, -1, -1)
            )
        };

        for (Face fac : b) {
            for (Tri Tri : fac.tris()) {
                for (Vec3 pnt : Tri.points()) {
                    pnt.x *= width;
                    pnt.y *= height;
                    pnt.z *= length; 
                }
            }
        }

        mesh = b;
    }
}