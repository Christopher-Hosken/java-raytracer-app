package io.cjhosken.javaraytracerapp.rendering.paver.data.objects;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import java.awt.Color;

import io.cjhosken.javaraytracerapp.rendering.core.Vec3;
import io.cjhosken.javaraytracerapp.rendering.paver.data.base.PaverObject;
import io.cjhosken.javaraytracerapp.rendering.paver.data.base.PaverWorld;
import io.cjhosken.javaraytracerapp.rendering.paver.data.base.Ray;
import java.awt.image.BufferedImage;

public class PaverCamera extends PaverObject {
    private int width;
    private int height;
    private double lensRadius;
    private Vec3 u, v, w, hori, vert, llc;

    public PaverCamera(int w, int h, double fov, double aperture, double aspectRatio, double focus) {
        super("PaverCamera");
        width = w;
        height = h;
        solvePosition(new Vec3(0, 0, 1), fov, aperture, aspectRatio, focus);
    }

    public PaverCamera(int w, int h, Vec3 c, Vec3 direction, double fov, double aperture, double aspectRatio, double focus) {
        super("PaverCamera", c);
        width = w;
        height = h;
        solvePosition(direction, fov, aperture, aspectRatio, focus);
    }

    private void solvePosition(Vec3 dir, double fov, double ap, double ratio, double focus) {
        double h = Math.tan(Math.toRadians(fov) / 2);
        double vh = 2 * h;
        double vw = ratio * vh;

        w = Vec3.sub(center, dir).unitVector();
        u = Vec3.cross(new Vec3(0, 1, 0), w).unitVector();
        v = Vec3.cross(w, u);

        hori = Vec3.mult((focus * vw), u);
        vert = Vec3.mult((focus * vh), v);
        llc = Vec3.sub(Vec3.sub(Vec3.sub(center, Vec3.div(hori, 2)), Vec3.div(vert, 2)), Vec3.mult(focus, w));
        lensRadius = ap / 2;
    }

    public Ray getRay(double s, double t) {
        Vec3 rd = Vec3.mult(lensRadius, Vec3.randomInUnitDisc());
        rd = new Vec3(1, 1, 1);
        Vec3 offset = Vec3.add(Vec3.mult(u, rd.x), Vec3.mult(v, rd.y));

        Vec3 target = Vec3.sub(Vec3.sub(Vec3.add(Vec3.add(llc, Vec3.mult(s, hori)), Vec3.mult(t, vert)), center), offset);
        return new Ray(Vec3.add(center, offset), target);
    }

    public BufferedImage render(BufferedImage image, PaverWorld world, int samples, int bounces) throws IOException {

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Vec3 col = new Vec3(0, 0, 0);
                for (int s = 0; s < samples; s++) {
                    double u = ((double) x + Math.random()) / width;
                    double v = ((double) y + Math.random()) / height;
                    Ray ray = getRay(u, v);

                    col = Vec3.add(col, solveRay(world, ray, bounces, bounces));
                }
                int rgb = convertRGB(col, samples);
                image.setRGB(x, (height - 1) - y, rgb);
            }
            System.out.println(y + 1 + "/" + height + " Lines Rendered.");
        }

        return image;
    }

    public Vec3 solveRay(PaverWorld world, Ray ray, int d, int b) {
        PaverObject obj = world.hit(ray);

        if (obj != null) {
            return Vec3.mult(Vec3.mult(obj.shader().color(), obj.shader().spec()), solveRay(world, obj.shader().scatter(obj, ray), d - 1, b));
        }

        double u = ray.direction().unitVector().y;
        return Vec3.add(
            Vec3.mult(new Vec3(.3, .3, .5), (1 - u)),
            Vec3.mult(new Vec3(0.25, 0.5, 1), u)
        );
    }

    public int convertRGB(Vec3 col, int samples) {
        double scale = 1.0 / samples;
        col = Vec3.mult(col, scale);
        col.sqrt();
        col.clamp(0, 1);

        int r = (int) (col.x * 255.999);
        int g = (int) (col.y * 255.999);
        int b = (int) (col.z * 255.999);

        return new Color(r, g, b).getRGB();
    }
}