public class FX3DObject extends Group {
  private String name;
  private JRSObjectType type = JRSObjectType.EMPTY;
  private boolean isSelected = false;
  private FX3DShader shader;
  private TriangleMesh mesh;
  
  public RendererObject(Group ob) {
    super(ob);
  }
  
  public boolean isSelected() {
    return isSelected;
  }
  
  public void setSelected(boolean select) {
    isSelected = select;
    for (int idx = 0; idx < getChildren().size(); idx++) {
      Shape3D obj = (Shape3D) getChildren.get(idx);
      obj.setMaterial(shader.toPhong());
    }
  }
  
  public void setType(JRSOBjectType type) {
    this.type = type;
  }
  
  public JRSOBjectType type() {
    return type;
  }
  
  public void setMesh(TriangleMesh mesh) {
     this.mesh = mesh;
  }
  
  public JRSObject toJRS() {
    JRSOBject obj = new JRSObject();
    
    obj.setName(name);
    obj.setType(type);
    obj.setLocation(new Vector3d());
    obj.setRotation(new Vector3d());
    obj.setScale(new Vector3d());
    
    if (type == JRSObjectType.OBJ) {
      obj.setMesh(mesh);
    }
    
    obj.setShader(shader);
    
    return obj;
  }
  
  public void fromJRS(JRSObject jrs) {
    name = jrs.name();
    type = jrs.type();
    setTranslateX();
    setTranslateY();
    setTranslateZ();
    setRotate();
    setRotate();
    setRotate();
    setScaleX();
    setScaleY();
    setScaleZ();
    
    if (type == JRSObjectType.OBJ) {
      mesh = jrs.mesh();
    }
    
    shader = jrs.shader();
  }
}
