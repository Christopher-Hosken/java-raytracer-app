package io.cjhosken.javaraytracerapp.rendering.fx3d;

import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class Line3D extends Cylinder {
    public Line3D(Point3D o, Point3D t, double width, Color color) {
        super();
        Point3D yAxis = new Point3D(0, 1, 0);
        Point3D diff = t.subtract(o);
        double height = diff.magnitude();

        Point3D mid = t.midpoint(o);
        Translate moveToMidpoint = new Translate(mid.getX(), mid.getY(), mid.getZ());

        Point3D axisOfRotation = diff.crossProduct(yAxis);
        double angle = Math.acos(diff.normalize().dotProduct(yAxis));
        Rotate rotateAroundCenter = new Rotate(-Math.toDegrees(angle), axisOfRotation);

        setHeight(height);
        setRadius(width);
        materialProperty().set(new PhongMaterial(color));
        getTransforms().addAll(moveToMidpoint, rotateAroundCenter);
    }

    public Line3D(Point3D t, double width, Color color) {
        super();
        Point3D o = new Point3D(0, 0, 0);
        Point3D yAxis = new Point3D(0, 1, 0);
        Point3D diff = t.subtract(o);
        double height = diff.magnitude();

        Point3D mid = t.midpoint(o);
        Translate moveToMidpoint = new Translate(mid.getX(), mid.getY(), mid.getZ());

        Point3D axisOfRotation = diff.crossProduct(yAxis);
        double angle = Math.acos(diff.normalize().dotProduct(yAxis));
        Rotate rotateAroundCenter = new Rotate(-Math.toDegrees(angle), axisOfRotation);

        setHeight(height);
        setRadius(width);
        materialProperty().set(new PhongMaterial(color));
        getTransforms().addAll(moveToMidpoint, rotateAroundCenter);
    }
}
