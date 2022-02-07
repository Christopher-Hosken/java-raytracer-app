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
  
  public void objectsFromJRSArray(JRSObject[] jrsObjects) {
    objects = new PaverObject[jrsObjects.length];
    
    for (int idx = 0; idx < objects.length; idx++) {
      objects[idx] = PaverObject.fromJRS(jrsObjects[idx]);
    }
  }
}
