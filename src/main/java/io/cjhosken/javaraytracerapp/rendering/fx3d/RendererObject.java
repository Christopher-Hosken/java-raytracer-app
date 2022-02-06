package io.cjhosken.javaraytracerapp.rendering.fx3d;

import io.cjhosken.javaraytracerapp.rendering.core.ObjectData;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Shape3D;

public class RendererObject extends Group {
    private String type = "object";
    private boolean isMesh = false;
    private boolean isSelected = false;
    private ObjectData objectData;

    public RendererObject(Group ob) {
        super(ob);
    }

    public RendererObject(Node nd) {
        super(nd);
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean sel) {
        isSelected = sel;
        for (int idx = 0; idx < getChildren().size(); idx++) {
            Shape3D obj = (Shape3D) getChildren().get(idx);
            if (isSelected) {
                obj.setMaterial(new PhongMaterial(Color.ROYALBLUE));
            } else {
                obj.setMaterial(new PhongMaterial());
            }

        }
    }

    public void setType(String s) {
        type = s;
    }

    public String type() {
        return type;
    }

    public void setMesh(boolean m) {
        isMesh = m;
    }

    public boolean isMesh() {
        return isMesh;
    }
}
