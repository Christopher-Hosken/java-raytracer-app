package io.cjhosken.javaraytracerapp.rendering.paver.data.objects.primitives;

import io.cjhosken.javaraytracerapp.rendering.core.Vec3;
import io.cjhosken.javaraytracerapp.rendering.paver.data.base.Face;
import io.cjhosken.javaraytracerapp.rendering.paver.data.base.PaverMeshObject;
import io.cjhosken.javaraytracerapp.rendering.paver.data.base.PaverObject;
import io.cjhosken.javaraytracerapp.rendering.paver.data.base.Tri;

public class PaverPlane extends PaverMeshObject {
    protected double width = 2;
    protected double length = 2;

    public PaverPlane(String name) {
        super(name);
        updateMesh();
    }

    public PaverPlane(String name, double l) {
        super(name);
        this.width = l;
        this.length = l;
        updateMesh();
    }

    public PaverPlane(String name, Vec3 center, double w, double l) {
        super(name, center);
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
        Face[] b = {
            new Face("f0", 
                new Vec3(-1, 0, 1),
                new Vec3(-1, 0, -1),
                new Vec3(1, 0, -1),
                new Vec3(1, 0, 1)
            )
        };

        for (Tri Tri : b[0].tris()) {
            for (Vec3 pnt : Tri.points()) {
                pnt.x *= width;
                pnt.z *= length;
            }
        }

        mesh = b;
    }
}