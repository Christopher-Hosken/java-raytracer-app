package io.cjhosken.javaraytracerapp.rendering.core;

import io.cjhosken.javaraytracerapp.rendering.paver.base.vec3;

public class CoreCube extends CoreObject {

    public CoreCube(String name, int id, vec3 center) {
        super(name, id, center);
        this.verts = new float[] { /* Front face */
            0.5f, 0.5f, 0.5f,  -0.5f, 0.5f, 0.5f,  -0.5f,-0.5f, 0.5f,  0.5f,-0.5f, 0.5f, // v0,v1,v2,v3 (front)
            0.5f, 0.5f, 0.5f,   0.5f,-0.5f, 0.5f,   0.5f,-0.5f,-0.5f,  0.5f, 0.5f,-0.5f, // v0,v3,v4,v5 (right)
            0.5f, 0.5f, 0.5f,   0.5f, 0.5f,-0.5f,  -0.5f, 0.5f,-0.5f, -0.5f, 0.5f, 0.5f, // v0,v5,v6,v1 (top)
           -0.5f, 0.5f, 0.5f,  -0.5f, 0.5f,-0.5f,  -0.5f,-0.5f,-0.5f, -0.5f,-0.5f, 0.5f, // v1,v6,v7,v2 (left)
           -0.5f,-0.5f,-0.5f,   0.5f,-0.5f,-0.5f,   0.5f,-0.5f, 0.5f, -0.5f,-0.5f, 0.5f, // v7,v4,v3,v2 (bottom)
            0.5f,-0.5f,-0.5f,  -0.5f,-0.5f,-0.5f,  -0.5f, 0.5f,-0.5f,  0.5f, 0.5f,-0.5f  // 
        };

        this.indices = new int[] { /* Front face */
            0, 1, 2,   2, 3, 0,    // v0-v1-v2, v2-v3-v0 (front)
            4, 5, 6,   6, 7, 4,    // v0-v3-v4, v4-v5-v0 (right)
            8, 9,10,  10,11, 8,    // v0-v5-v6, v6-v1-v0 (top)
           12,13,14,  14,15,12,    // v1-v6-v7, v7-v2-v1 (left)
           16,17,18,  18,19,16,    // v7-v4-v3, v3-v2-v7 (bottom)
           20,21,22,  22,23,20
        };

        this.colors = new float[] { /* Front face */
                1.0f, 0.0f, 0.0f,
                1.0f, 0.0f, 0.0f,
                1.0f, 0.0f, 0.0f,
                1.0f, 0.0f, 0.0f,
                /* Left face */
                0.0f, 1.0f, 0.0f,
                0.0f, 1.0f, 0.0f,
                0.0f, 1.0f, 0.0f,
                0.0f, 1.0f, 0.0f,
                /* Back face */
                0.0f, 0.0f, 1.0f,
                0.0f, 0.0f, 1.0f,
                0.0f, 0.0f, 1.0f,
                0.0f, 0.0f, 1.0f,
                /* Right face */
                1.0f, 0.647f, 0.0f,
                1.0f, 0.647f, 0.0f,
                1.0f, 0.647f, 0.0f,
                1.0f, 0.647f, 0.0f,
                /* Top face */
                1.0f, 1.0f, 1.0f,
                1.0f, 1.0f, 1.0f,
                1.0f, 1.0f, 1.0f,
                1.0f, 1.0f, 1.0f,
                /* Bottom face */
                1.0f, 1.0f, 0.0f,
                1.0f, 1.0f, 0.0f,
                1.0f, 1.0f, 0.0f,
                1.0f, 1.0f, 0.0f
        };
    }
}