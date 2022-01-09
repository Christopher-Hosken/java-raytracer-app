package io.cjhosken.javaraytracerapp.rendering.paver.shaders;

import java.util.ArrayList;

import io.cjhosken.javaraytracerapp.rendering.paver.base.object;
import io.cjhosken.javaraytracerapp.rendering.paver.base.ray;

public class world {
    private ArrayList<object> scene;

    public world() {
        scene = new ArrayList<object>();
    }

    public void add(object obj) {
        scene.add(obj);
    }

    public object get(int idx) {
        return scene.get(idx);
    }

    public object get(String name) {
        if (scene != null && scene.size() > 0)
            for (object obj : scene) {
                if (obj.name().equals(name)) return obj;
            }
        return null;
    }

    public ArrayList<object> scene() {
        return scene;
    }

    public object hit(ray ray) {
        object hit_obj = null;
        double t = Double.POSITIVE_INFINITY;

        if (scene != null && scene.size() > 0) {
            for (object obj : scene) {
                double t0 = obj.intersect(ray);
                if (t0 > 0.0000001 && t0 < t) {
                    hit_obj = obj;
                    t = t0;
                }
            }
        }
        
        return hit_obj;
    }
}