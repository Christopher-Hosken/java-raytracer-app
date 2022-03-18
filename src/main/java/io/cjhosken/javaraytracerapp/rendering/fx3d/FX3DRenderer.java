package io.cjhosken.javaraytracerapp.rendering.fx3d;

import io.cjhosken.javaraytracerapp.core.Vector2d;
import io.cjhosken.javaraytracerapp.core.Vector3d;
import io.cjhosken.javaraytracerapp.jrs.JRSFile;
import io.cjhosken.javaraytracerapp.jrs.datatypes.JRSCamera;
import io.cjhosken.javaraytracerapp.jrs.datatypes.JRSObject;
import io.cjhosken.javaraytracerapp.jrs.datatypes.JRSWorld;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;

public class FX3DRenderer {
  private Group world = new Group();
  private Group objects = new Group();
  private SubScene scene = new SubScene(world, 960, 685, true, SceneAntialiasing.DISABLED);
  private PerspectiveCamera viewportCamera = new PerspectiveCamera();
  private PerspectiveCamera renderCamera = new PerspectiveCamera();
  private boolean isInRenderCamera = false;

  private Vector2d last = new Vector2d();
  private Vector2d lastAngle = new Vector2d();

  private final DoubleProperty viewportAngleX = new SimpleDoubleProperty(0);
  private final DoubleProperty viewportAngleY = new SimpleDoubleProperty(0);
  private final DoubleProperty renderAngleX = new SimpleDoubleProperty(0);
  private final DoubleProperty renderAngleY = new SimpleDoubleProperty(0);

  private final DoubleProperty currentAngleX = new SimpleDoubleProperty(0);
  private final DoubleProperty currentAngleY = new SimpleDoubleProperty(0);

  public FX3DRenderer() {
    viewportCamera = new PerspectiveCamera(true);
    viewportCamera.setNearClip(0.01);
    viewportCamera.setFarClip(10000);
    viewportCamera.setTranslateX(0);
    viewportCamera.setTranslateY(0);
    viewportCamera.setTranslateZ(-25);

    renderCamera = new PerspectiveCamera(true);
    renderCamera.setNearClip(0.01);
    renderCamera.setFarClip(10000);
    renderCamera.setTranslateX(0);
    renderCamera.setTranslateY(0);
    renderCamera.setTranslateZ(-25);

    scene.setCamera(viewportCamera);
    scene.setFill(Color.BLACK);
    world.getChildren().addAll(objects);
    initMouseControl();
  }

  public SubScene scene() {
    return scene;
  }

  public boolean isInRenderCamera() {
    return isInRenderCamera;
  }

  public PerspectiveCamera viewportCamera() {
    return this.viewportCamera;
  }

  public PerspectiveCamera renderCamera() {
    return this.renderCamera;
  }

  public Camera activeCamera() {
    return scene.getCamera();
  }

  public void setRenderCamera(PerspectiveCamera cam) {
    renderCamera = cam;
  }

  public void addToWorld(FX3DObject obj) {
    objects.getChildren().add(obj);
    obj.setOnMouseClicked(event -> {
      clearSelections();
      obj.setSelected(true);
    });
  }

  public void clearSelections() {
    for (int idx = 0; idx < objects.getChildren().size(); idx++) {
      FX3DObject obj = (FX3DObject) objects.getChildren().get(idx);
      obj.setSelected(false);
    }
  }

  public void toggleRenderCamera() {
    scene.setCamera(null);
    if (isInRenderCamera) {
      scene.setCamera(viewportCamera);
    } else {
      scene.setCamera(renderCamera);
    }

    isInRenderCamera = !isInRenderCamera;
    setCurrentAngle();
  }

  private void initMouseControl() {
    Rotate xRotate;
    Rotate yRotate;

    world.getTransforms().addAll(
        xRotate = new Rotate(0, Rotate.X_AXIS),
        yRotate = new Rotate(0, Rotate.Y_AXIS));

    xRotate.angleProperty().bind(currentAngleX);
    yRotate.angleProperty().bind(currentAngleY);

    scene.setOnScroll(event -> {
      zoomScrollView(event);
    });

    scene.setOnMouseMoved(event -> {
      last.x = event.getSceneX();
      last.y = event.getSceneY();

      setCurrentAngle();

      lastAngle.x = currentAngleX.get();
      lastAngle.y = currentAngleY.get();
    });

    scene.setOnMouseDragged(event -> {
      if (event.isPrimaryButtonDown()) {
        if (event.isShiftDown()) {
          translateView(event);
        } else {
          rotateView(event);
        }
      } else if (event.isMiddleButtonDown()) {
        translateView(event);
      } else if (event.isSecondaryButtonDown()) {
        if (event.isAltDown()) {
          zoomView(event);
        }
      }

      last.x = event.getSceneX();
      last.y = event.getSceneY();

      setCurrentAngle();

      lastAngle.x = currentAngleX.get();
      lastAngle.y = currentAngleY.get();
    });
  }

  private void translateView(MouseEvent event) {
    if (!isInRenderCamera) {
      viewportCamera.translateXProperty().set(viewportCamera.getTranslateX() + (event.getSceneX() - last.x) * 0.1);
      viewportCamera.translateYProperty().set(viewportCamera.getTranslateY() + (event.getSceneY() - last.y) * 0.1);
    } else {
      renderCamera.translateXProperty().set(renderCamera.getTranslateX() + (event.getSceneX() - last.x) * 0.1);
      renderCamera.translateYProperty().set(renderCamera.getTranslateY() + (event.getSceneY() - last.y) * 0.1);
    }
  }

  private void rotateView(MouseEvent event) {
    if (!isInRenderCamera) {
      viewportAngleX.set(lastAngle.x - (last.y - event.getSceneY()) * 0.5);
      viewportAngleY.set(lastAngle.y + (last.x - event.getSceneX()) * 0.5);
    } else {
      renderAngleX.set(lastAngle.x - (last.y - event.getSceneY()) * 0.5);
      renderAngleY.set(lastAngle.y + (last.x - event.getSceneX()) * 0.5);
    }
  }

  private void zoomView(MouseEvent event) {
    if (!isInRenderCamera) {
      viewportCamera.translateZProperty().set(viewportCamera.getTranslateZ() - (event.getSceneY() - last.y) * 0.1);
    } else {
      renderCamera.translateZProperty().set(renderCamera.getTranslateZ() - (event.getSceneY() - last.y) * 0.1);
    }
  }

  private void zoomScrollView(ScrollEvent event) {
    if (!isInRenderCamera) {
      viewportCamera.translateZProperty().set(viewportCamera.getTranslateZ() + event.getDeltaY() * 0.05);
    } else {
      renderCamera.translateZProperty().set(renderCamera.getTranslateZ() + event.getDeltaY() * 0.05);
    }
  }

  private void setCurrentAngle() {
    if (!isInRenderCamera) {
      currentAngleX.set(viewportAngleX.get());
      currentAngleY.set(viewportAngleY.get());
    } else {
      currentAngleX.set(renderAngleX.get());
      currentAngleY.set(renderAngleY.get());
    }
  }

  public Vector2d getCurrentAngle() {
    return new Vector2d(currentAngleX.doubleValue(), currentAngleY.doubleValue());
  }

  /* TODO: Camera Position */
  public Vector3d getCameraPosition() {
    /*
    Transformation matrix?

    sin cos etc.
    */
    return new Vector3d();
  }

  /*
   * private void deleteSelected() {
   * for (int idx = 0; idx < objects.getChildren().size(); idx++) {
   * FX3DObject obj = (FX3DObject) objects.getChildren().get(idx);
   * if (obj.isSelected()) {
   * objects.getChildren().remove(obj);
   * }
   * }
   * }
   */

   public Group objects() {
     return objects;
   }

   public void setObjects(Group newObjects) {
    objects.getChildren().clear();
    for (int obx = 0; obx < newObjects.getChildren().size(); obx++) {
      addToWorld((FX3DObject) newObjects.getChildren().get(obx));
    }
   }

  public JRSWorld toJRS() {
    JRSWorld world = new JRSWorld();
    JRSObject[] jrsObjects = new JRSObject[objects.getChildren().size()];

    for (int idx = 0; idx < objects.getChildren().size(); idx++) {
      FX3DObject obj = (FX3DObject) objects.getChildren().get(idx);
      jrsObjects[idx] = obj.toJRS();
      System.out.println(obj.name());
    }

    world.setObjects(jrsObjects);
    world.setCamera(JRSCamera.fromFX3D(renderCamera));

    return world;
  }

  public JRSFile toJRSFile() {
    JRSFile file = new JRSFile();
    file.setWorld(toJRS());

    return file;
  }

  public static FX3DRenderer fromJRS(JRSWorld world) {
    FX3DRenderer renderer = new FX3DRenderer();
    renderer.setRenderCamera(world.camera().toFX3D());

    for (JRSObject obj : world.objects()) {
      renderer.addToWorld(obj.toFX3D());
    }

    return renderer;
  }
}
