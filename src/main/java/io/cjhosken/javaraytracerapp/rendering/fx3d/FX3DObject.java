package io.cjhosken.javaraytracerapp.rendering.fx3d;

import io.cjhosken.javaraytracerapp.core.Vector3d;
import io.cjhosken.javaraytracerapp.jrs.datatypes.JRSObject;
import io.cjhosken.javaraytracerapp.jrs.datatypes.JRSObjectType;
import io.cjhosken.javaraytracerapp.jrs.datatypes.JRSShader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.TriangleMesh;

public class FX3DObject extends Group {
  private String name;
  private JRSObjectType type = JRSObjectType.EMPTY;
  private boolean isSelected = false;
  private FX3DShader shader;
  private TriangleMesh mesh;

  public FX3DObject() {
    super();
  }
  
  public FX3DObject(Group group) {
    super(group);
  }

  public FX3DObject(Node node) {
    super(node);
  }

  public String name() {
    return name;
  }

  public JRSObjectType type() {
    return type;
  }
  
  public boolean isSelected() {
    return isSelected;
  }

  public FX3DShader shader() {
    return shader;
  }

  public TriangleMesh mesh() {
    return mesh;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setType(JRSObjectType type) {
    this.type = type;
  }

  public void setSelected(boolean select) {
    isSelected = select;
    for (int idx = 0; idx < getChildren().size(); idx++) {
      Shape3D obj = (Shape3D) getChildren().get(idx);
      obj.setMaterial(shader.toPhong());
    }
  }

  public void setShader(FX3DShader shader) {
    this.shader = shader;
  }
  
  public void setMesh(TriangleMesh mesh) {
    this.mesh = mesh;
  }
  
  public JRSObject toJRS() {
    JRSObject obj = new JRSObject();
    obj.setName(name);
    obj.setType(type);
    obj.setLocation(new Vector3d(getTranslateX(), getTranslateY(), getTranslateZ()));
    obj.setRotation(new Vector3d());
    obj.setScale(new Vector3d(getScaleX(), getScaleY(), getScaleZ()));
    
    if (type == JRSObjectType.OBJ) {
      obj.fromTriangleMesh(mesh);
    }
    
    obj.setShader(JRSShader.fromFX3D(shader));
    
    return obj;
  }
  
  public static FX3DObject fromJRS(JRSObject jrs) {
    FX3DObject fxObject = new FX3DObject();
    fxObject.setName(jrs.name());
    fxObject.setType(jrs.type());
    fxObject.setTranslateX(jrs.location().x);
    fxObject.setTranslateY(jrs.location().y);
    fxObject.setTranslateZ(jrs.location().z);
    fxObject.setRotate(0);
    fxObject.setRotate(0);
    fxObject.setRotate(0);
    fxObject.setScaleX(1);
    fxObject.setScaleY(1);
    fxObject.setScaleZ(1);
    
    if (fxObject.type() == JRSObjectType.OBJ) {
      fxObject.setMesh(jrs.triangleMesh());
    }
    
    fxObject.setShader(jrs.shader().toFX3D());

    return fxObject;
  }
}
