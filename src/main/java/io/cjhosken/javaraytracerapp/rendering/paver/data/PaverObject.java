public class PaverObject {
  protected String name;
  protected Vector3d location;
  protected Vector3d rotation;
  protected Vector3d scale;
  protected Vector3d tmpNormal;
  protected double tmpT;
  protected PaverShader shader = new PaverDiffuse();
  
  public PaverObject() {}
  
  
  public static PaverObject fromJRS(JRSObject object) {
    return new PaverObject();
  }
  
  public double intersect(Ray ray) {
    return -1;
  }
}
