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
    private Camera renderCamera = new PerspectiveCamera();
    private boolean isRenderCam = false;

    private Delta last = new Delta();
    private Delta lastAngle = new Delta();
    private final DoubleProperty angleX = new SimpleDoubleProperty(0);
    private final DoubleProperty angleY = new SimpleDoubleProperty(0);
    private final DoubleProperty renderAngleX = new SimpleDoubleProperty(0);
    private final DoubleProperty renderAngleY = new SimpleDoubleProperty(0);
    DoubleProperty tmpX = new SimpleDoubleProperty(0);
    DoubleProperty tmpY = new SimpleDoubleProperty(0);

    public FX3DRenderer() {
        camera = new PerspectiveCamera(true);
        renderCamera = new PerspectiveCamera(true);
        camera.setFarClip(10000);
        camera.setNearClip(0.01);
        camera.setTranslateX(0);
        camera.setTranslateY(0);
        camera.setTranslateZ(-25);

        renderCamera.setTranslateX(0);
        renderCamera.setTranslateY(0);
        renderCamera.setTranslateZ(-25);

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

    public void setPropertiesPanel(TabPane pane) {
        propertiesPanel = new PropertiesPanel(pane);
        propertiesPanel.setRenderer(this);
    }

    public PropertiesPanel propertiesPanel() {
        return propertiesPanel;
    }

    public boolean isInRenderCam() {
        return isRenderCam;
    }

    public void clearSelections() {
        for (int idx = 0; idx < objects.getChildren().size(); idx++) {
            RendererObject obj = (RendererObject) objects.getChildren().get(idx);
            obj.setSelected(false);
        }
    }

    public void toggleRenderCamera() {
        scene.setCamera(null);
        if (isRenderCam) {
            scene.setCamera(camera);
            isRenderCam = false;
            tmpX.set(angleX.get());
            tmpY.set(angleY.get());
        }

        else {
            scene.setCamera(renderCamera);
            isRenderCam = true;
            tmpX.set(renderAngleX.get());
            tmpY.set(renderAngleY.get());
        }
    }

    public SubScene scene() {
        return this.scene;
    }

    public Group objects() {
        return this.objects;
    }

    public Camera camera() {
            return this.camera;
    }

    public Camera renderCamera() {
        return this.renderCamera;
}

    public DoubleProperty angleX() {
        return this.angleX;
    }

    public DoubleProperty angleY() {
        return this.angleY;
    }
    
    public DoubleProperty renderAngleX() {
        return this.renderAngleX;
    }

    public DoubleProperty renderAngleY() {
        return this.renderAngleY;
    }

    private void initMouseControl() {
        Rotate xRotate;
        Rotate yRotate;
        
        world.getTransforms().addAll(
            xRotate = new Rotate(0, Rotate.X_AXIS),
            yRotate = new Rotate(0, Rotate.Y_AXIS)
        );

        xRotate.angleProperty().bind(tmpX);
        yRotate.angleProperty().bind(tmpY);

        scene.setOnScroll(event -> {
            if (!isRenderCam) {
                camera.translateZProperty().set(camera.getTranslateZ() + event.getDeltaY() * 0.05);
            } else {
                renderCamera.translateZProperty().set(renderCamera.getTranslateZ() + event.getDeltaY() * 0.05);
            }
        });
        
        scene.setOnMouseMoved(event -> {
            last.x = event.getSceneX();
            last.y = event.getSceneY();
            if (!isRenderCam) {
                tmpX.set(angleX.get());
                tmpY.set(angleY.get());
                
            } else {
                tmpX.set(renderAngleX.get());
                tmpY.set(renderAngleY.get());
            }

            lastAngle.x = tmpX.get();
            lastAngle.y = tmpY.get();

        });

        scene.setOnMouseDragged(event -> {
            if (event.isPrimaryButtonDown()) {
                if (event.isShiftDown()) {
                    if (!isRenderCam) {
                        camera.translateXProperty().set(camera.getTranslateX() + (event.getSceneX() - last.x) * 0.1);
                        camera.translateYProperty().set(camera.getTranslateY() + (event.getSceneY() - last.y) * 0.1);
                    }

                    else {
                        renderCamera.translateXProperty().set(renderCamera.getTranslateX() + (event.getSceneX() - last.x) * 0.1);
                        renderCamera.translateYProperty().set(renderCamera.getTranslateY() + (event.getSceneY() - last.y) * 0.1);
                    }
                    
                } else {
                    if (!isRenderCam) {
                        angleX.set(lastAngle.x - (last.y - event.getSceneY()) * 0.5);
                        angleY.set(lastAngle.y + (last.x - event.getSceneX()) * 0.5);
                    } else {
                        renderAngleX.set(lastAngle.x - (last.y - event.getSceneY()) * 0.5);
                        renderAngleY.set(lastAngle.y + (last.x - event.getSceneX()) * 0.5);
                    }
                }   
            }

            else if (event.isMiddleButtonDown()) {
                if (!isRenderCam) {
                    camera.translateXProperty().set(camera.getTranslateX() + (event.getSceneX() - last.x) * 0.05);
                    camera.translateYProperty().set(camera.getTranslateY() + (event.getSceneY() - last.y) * 0.05);
                } else {
                    renderCamera.translateXProperty().set(renderCamera.getTranslateX() + (event.getSceneX() - last.x) * 0.05);
                    renderCamera.translateYProperty().set(renderCamera.getTranslateY() + (event.getSceneY() - last.y) * 0.05);
                }
                
            }

            else if (event.isSecondaryButtonDown()) {
                if (event.isAltDown()) {
                    if (!isRenderCam) {
                        camera.translateZProperty().set(camera.getTranslateZ() - (event.getSceneY() - last.y) * 0.1);
                    } else {
                        renderCamera.translateZProperty().set(renderCamera.getTranslateZ() - (event.getSceneY() - last.y) * 0.1);
                    }
                }
            }


            last.x = event.getSceneX();
            last.y = event.getSceneY();
            if (!isRenderCam) {
                tmpX.set(angleX.get());
                tmpY.set(angleY.get());
                
            } else {
                tmpX.set(renderAngleX.get());
                tmpY.set(renderAngleY.get());
            }
            
            lastAngle.x = tmpX.get();
            lastAngle.y = tmpY.get();

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