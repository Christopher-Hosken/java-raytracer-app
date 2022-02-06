package io.cjhosken.javaraytracerapp.rendering.paver.data.base;

import java.util.ArrayList;

public class PaverWorld {
    private ArrayList<PaverObject> scene;

    public PaverWorld() {
        scene = new ArrayList<PaverObject>();
    }

    public void add(PaverObject obj) {
        scene.add(obj);
    }

    public PaverObject get(int idx) {
        return scene.get(idx);
    }

    public PaverObject get(String name) {
        if (scene != null && scene.size() > 0)
            for (PaverObject obj : scene) {
                if (obj.name().equals(name)) return obj;
            }
        return null;
    }

    public ArrayList<PaverObject> scene() {
        return scene;
    }

    public PaverObject hit(Ray ray) {
        PaverObject hit_obj = null;
        double t = Double.POSITIVE_INFINITY;

        if (scene != null && scene.size() > 0) {
            for (PaverObject obj : scene) {
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