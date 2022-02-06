package io.cjhosken.javaraytracerapp.rendering.paver.data.shaders;

import io.cjhosken.javaraytracerapp.rendering.core.Vec3;
import io.cjhosken.javaraytracerapp.rendering.paver.data.base.PaverObject;
import io.cjhosken.javaraytracerapp.rendering.paver.data.base.Ray;

public class PaverDiffuse extends PaverShader {
    public PaverDiffuse() {super();}

    public PaverDiffuse(Vec3 color) {
        super(color);
    }

    public PaverDiffuse(Vec3 color, double spec) {
        super(color, spec);
    }

    public Ray scatter(PaverObject obj, Ray ray) {
        Vec3 p = ray.at(obj.t());
        return new Ray(p, Vec3.scatter(p, obj.normal()));
    }
}