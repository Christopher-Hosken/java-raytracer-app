package io.cjhosken.javaraytracerapp.rendering.paver.shaders;

import io.cjhosken.javaraytracerapp.rendering.paver.base.*;

public class glossy extends shader {
    private double roughness = 0;

    public glossy() {super();}

    public glossy(double roughness) {
        this.roughness = roughness;
    }

    public glossy(vec3 color, double roughness) {
        super(color);
        this.roughness = roughness;
    }

    public glossy(vec3 color, double roughness, double spec) {
        super(color, spec);
        this.roughness = roughness;
    }

    public ray scatter(object ob, ray ray) {
        return new ray(ray.at(ob.t()), vec3.reflect(ray.direction(), ob.normal(), roughness));
    }
}