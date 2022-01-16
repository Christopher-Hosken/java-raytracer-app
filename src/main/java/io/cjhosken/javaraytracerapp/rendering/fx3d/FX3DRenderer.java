package io.cjhosken.javaraytracerapp.rendering.fx3d;

import java.io.IOException;

import io.cjhosken.javaraytracerapp.rendering.core.Delta;
import io.cjhosken.javaraytracerapp.ui.PropertiesPanel;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.*;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;

public class FX3DRenderer {
    private PropertiesPanel propertiesPanel;
    private Group world = new Group();
    private Group objects = new Group();
    private SubScene scene = new SubScene(world, 960, 685, true, SceneAntialiasing.DISABLED);
    private Camera camera = new PerspectiveCamera();
    private boolean hasCamera = false;

    private Delta last = new Delta();
    private Delta lastAngle = new Delta();
    private final DoubleProperty angleX = new SimpleDoubleProperty(0);
    private final DoubleProperty angleY = new SimpleDoubleProperty(0);

    public FX3DRenderer() {
        camera = new PerspectiveCamera(true);
        camera.setFarClip(10000);
        camera.setNearClip(0.01);
        camera.setTranslateX(0);
        camera.setTranslateY(0);
        camera.setTranslateZ(-25);

        scene.setCamera(camera);
        scene.setFill(Color.BLACK);
        world.getChildren().addAll(objects);
        initMouseControl();
    }

    public void addToWorld(RendererObject rendererObject) {
        objects.getChildren().add(rendererObject);
        rendererObject.setOnMouseClicked(event -> {
            clearSelections();
            rendererObject.setSelected(true);
            propertiesPanel.setObjectTab(rendererObject);
        });
    }

    public void addCamera(RendererObject ob) throws IOException {
        addToWorld(ob);
        hasCamera = true;
    }

    public void setPropertiesPanel(TabPane pane) {
        propertiesPanel = new PropertiesPanel(pane);
    }

    public PropertiesPanel propertiesPanel() {
        return propertiesPanel;
    }

    public void clearSelections() {
        for (int idx = 0; idx < objects.getChildren().size(); idx++) {
            RendererObject obj = (RendererObject) objects.getChildren().get(idx);
            obj.setSelected(false);
        }
    }

    public SubScene scene() {
        return this.scene;
    }

    public Camera camera() {
        return this.camera;
    }

    public DoubleProperty angleX() {
        return this.angleX;
    }

    public DoubleProperty angleY() {
        return this.angleY;
    }

    public boolean hasCamera() {
        return hasCamera;
    }

    private void initMouseControl() {
        Rotate xRotate;
        Rotate yRotate;

        world.getTransforms().addAll(
            xRotate = new Rotate(0, Rotate.X_AXIS),
            yRotate = new Rotate(0, Rotate.Y_AXIS)
        );

        xRotate.angleProperty().bind(angleX);
        yRotate.angleProperty().bind(angleY);

        scene.setOnScroll(event -> {
            camera.translateZProperty().set(camera.getTranslateZ() + event.getDeltaY() * 0.05);
        });
        
        scene.setOnMouseMoved(event -> {
            last.x = event.getSceneX();
            last.y = event.getSceneY();
            lastAngle.x = angleX.get();
            lastAngle.y = angleY.get();
        });

        scene.setOnMouseDragged(event -> {
            if (event.isPrimaryButtonDown()) {
                if (event.isShiftDown()) {
                    camera.translateXProperty().set(camera.getTranslateX() + (event.getSceneX() - last.x) * 0.1);
                    camera.translateYProperty().set(camera.getTranslateY() + (event.getSceneY() - last.y) * 0.1);
                } else {
                    angleX.set(lastAngle.x - (last.y - event.getSceneY()) * 0.5);
                    angleY.set(lastAngle.y + (last.x - event.getSceneX()) * 0.5);
                }   
            }

            else if (event.isMiddleButtonDown()) {
                camera.translateXProperty().set(camera.getTranslateX() + (event.getSceneX() - last.x) * 0.05);
                camera.translateYProperty().set(camera.getTranslateY() + (event.getSceneY() - last.y) * 0.05);
            }

            else if (event.isSecondaryButtonDown()) {
                if (event.isAltDown()) {
                    camera.translateZProperty().set(camera.getTranslateZ() - (event.getSceneY() - last.y) * 0.1);
                }
            }

            last.x = event.getSceneX();
            last.y = event.getSceneY();
            lastAngle.x = angleX.get();
            lastAngle.y = angleY.get();
        });
    }

    public void deleteSelected() {
        for (int idx = 0; idx < objects.getChildren().size(); idx++) {
            RendererObject obj = (RendererObject) objects.getChildren().get(idx);
            if (obj.isSelected()) {
                objects.getChildren().remove(obj);

            }

        }
        propertiesPanel.removeObjectTab();
    }
}