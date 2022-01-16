package io.cjhosken.javaraytracerapp.rendering.fx3d;

import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;

public class Circle3D extends Group {
    public Circle3D (int divs, double radius, double width, Color color, Rotate rotate) {
        for (int i = 0; i < divs; i++) {
            Point3D o = new Point3D(radius * Math.sin(2 * Math.PI * i / divs), 0, radius * Math.cos(2 * Math.PI * i / divs));
            Point3D t = new Point3D(radius * Math.sin(2 * Math.PI * (i + 1) / divs), 0, radius * Math.cos(2 * Math.PI * (i + 1) / divs));
            Line3D segment = new Line3D(o, t, width, color);
            getChildren().add(segment);
        }

        getTransforms().add(rotate);
    }
}
