package io.cjhosken.javaraytracerapp.rendering.paver.objects;

import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

import io.cjhosken.javaraytracerapp.rendering.paver.base.*;
import io.cjhosken.javaraytracerapp.rendering.paver.shaders.world;

import java.awt.Color;

public class camera extends object {
    private int width;
    private int height;
    private double lensRadius;
    private vec3 u, v, w, hori, vert, llc;

    public camera(int w, int h, double fov, double aperture, double aspectRatio, double focus) {
        super("camera");
        width = w;
        height = h;
        solvePosition(new vec3(0, 0, 1), fov, aperture, aspectRatio, focus);
    }

    public camera(int w, int h, vec3 c, vec3 direction, double fov, double aperture, double aspectRatio, double focus) {
        super("camera", c);
        width = w;
        height = h;
        solvePosition(direction, fov, aperture, aspectRatio, focus);
    }

    private void solvePosition(vec3 dir, double fov, double ap, double ratio, double focus) {
        double h = Math.tan(Math.toRadians(fov) / 2);
        double vh = 2 * h;
        double vw = ratio * vh;

        w = vec3.sub(center, dir).unitVector();
        u = vec3.cross(new vec3(0, 1, 0), w).unitVector();
        v = vec3.cross(w, u);

        hori = vec3.mult((focus * vw), u);
        vert = vec3.mult((focus * vh), v);
        llc = vec3.sub(vec3.sub(vec3.sub(center, vec3.div(hori, 2)), vec3.div(vert, 2)), vec3.mult(focus, w));
        lensRadius = ap / 2;
    }

    public ray getRay(double s, double t) {
        vec3 rd = vec3.mult(lensRadius, vec3.randomInUnitDisc());
        vec3 offset = vec3.add(vec3.mult(u, rd.x), vec3.mult(v, rd.y));

        vec3 target = vec3.sub(vec3.sub(vec3.add(vec3.add(llc, vec3.mult(s, hori)), vec3.mult(t, vert)), center), offset);
        return new ray(vec3.add(center, offset), target);
    }

    public void render(world world, int samples, int bounces) throws IOException {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                vec3 col = new vec3(0, 0, 0);
                for (int s = 0; s < samples; s++) {
                    double u = ((double) x + Math.random()) / width;
                    double v = ((double) y + Math.random()) / height;
                    ray ray = getRay(u, v);

                    col = vec3.add(col, solveRay(world, ray, bounces, bounces));
                }
                int rgb = convertRGB(col, samples);
                image.setRGB(x, (height - 1) - y, rgb);
            }
            System.out.println(y + 1 + "/" + height + " Lines Rendered.");
        }

        File outFile = new File("render.png");
        ImageIO.write(image, "png", outFile);
    }

    public vec3 solveRay(world world, ray ray, int d, int b) {
        object obj = world.hit(ray);

        if (obj != null) {
            return vec3.mult(vec3.mult(obj.shader().color(), obj.shader().spec()), solveRay(world, obj.shader().scatter(obj, ray), d - 1, b));
        }

        double u = ray.direction().unitVector().y;
        return vec3.add(
            vec3.mult(new vec3(.3, .3, .5), (1 - u)),
            vec3.mult(new vec3(0.25, 0.5, 1), u)
        );
    }

    public int convertRGB(vec3 col, int samples) {
        double scale = 1.0 / samples;
        col = vec3.mult(col, scale);
        col.sqrt();
        col.clamp(0, 1);

        int r = (int) (col.x * 255.999);
        int g = (int) (col.y * 255.999);
        int b = (int) (col.z * 255.999);

        return new Color(r, g, b).getRGB();
    }
}