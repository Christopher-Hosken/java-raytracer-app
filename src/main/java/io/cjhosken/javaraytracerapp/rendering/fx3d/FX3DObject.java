public class FX3DObject extends Group {
  private String name;
  private JRSObjectType type = JRSObjectType.EMPTY;
  private boolean isSelected = false;
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
      if (isSelected) {
        obj.setMaterial(new PhongMaterial(Color.ROYALBLUE));
      } else {
        obj.setMaterial(new PhoneMaterial());
      }
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
}
