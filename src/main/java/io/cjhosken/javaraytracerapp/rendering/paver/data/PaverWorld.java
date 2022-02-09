package io.cjhosken.javaraytracerapp.rendering.paver.data;

import io.cjhosken.javaraytracerapp.core.Ray;
import io.cjhosken.javaraytracerapp.jrs.datatypes.JRSObject;
import io.cjhosken.javaraytracerapp.jrs.datatypes.JRSWorld;

public class PaverWorld {
  PaverObject[] objects;
  PaverCamera camera;
  
  public PaverWorld() {}
  
  public PaverCamera camera() {
    return camera;
  }
  
  public PaverObject[] objects() {
    return objects;
  }
  
  public void setCamera(PaverCamera camera) {
     this.camera = camera;
  }
  
  public void setObjects(PaverObject[] objects) {
    this.objects = objects;
  }

  public static PaverWorld fromJRS(JRSWorld world) {
    PaverWorld paverWorld = new PaverWorld();
    paverWorld.objectsFromJRSArray(world.objects());
    paverWorld.setCamera(world.camera().toPaverCamera());
    return paverWorld;
  }
  
  public void objectsFromJRSArray(JRSObject[] jrsObjects) {
    objects = new PaverObject[jrsObjects.length];
    
    for (int idx = 0; idx < objects.length; idx++) {
      objects[idx] = PaverObject.fromJRS(jrsObjects[idx]);
    }
  }

  public PaverObject hit(Ray ray) {
    PaverObject hitObj = null;
    double t = Double.POSITIVE_INFINITY;

    if (objects != null && objects.length > 0) {
      for (PaverObject obj : objects) {
        double t0 = obj.intersect(ray);
        if (t0 > 0.0000001 && t0 < t) {
          hitObj = obj;
          t = t0;
        }
      }
    }

    return hitObj;
  }
}
