package io.cjhosken.javaraytracerapp.rendering.core;

import io.cjhosken.javaraytracerapp.rendering.paver.base.vec3;

public class CorePlane extends CoreObject {
    public CorePlane(String name, int id, vec3 center) {
        super(name, id, center);
        this.verts = new float[] {
            0.5f, 0.0f, 0.5f,   0.5f, 0.0f,-0.5f,  -0.5f, 0.0f,-0.5f, -0.5f, 0.0f, 0.5f, // v7,v4,v3,v2 (bottom)
        };

        this.indices = new int[] {
            0, 1, 2,
            2, 3, 0
        };

        this.colors = new float[] {
            1.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            0.0f, 1.0f, 0.0f
        };
    }
}