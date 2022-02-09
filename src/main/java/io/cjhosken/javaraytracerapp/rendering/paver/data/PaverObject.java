package io.cjhosken.javaraytracerapp.rendering.paver.data;

import io.cjhosken.javaraytracerapp.core.Ray;
import io.cjhosken.javaraytracerapp.core.Vector3d;
import io.cjhosken.javaraytracerapp.jrs.datatypes.JRSObject;

public class PaverObject {
  protected String name;
  protected Vector3d location;
  protected Vector3d rotation;
  protected Vector3d scale;
  protected Vector3d tmpNormal;
  protected double tmpT;
  protected PaverShader shader = new PaverShader();

  public PaverObject() {
  }

  public String name() {
    return name;
  }

  public Vector3d location() {
    return location;
  }

  public Vector3d rotation() {
    return rotation;
  }

  public Vector3d scale() {
    return scale;
  }

  public Vector3d tmpNormal() {
    return tmpNormal;
  }

  public double tmpT() {
    return tmpT;
  }

  public PaverShader shader() {
    return shader;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setLocation(Vector3d location) {
    this.location = location;
  }

  public void setRotation(Vector3d rotation) {
    this.rotation = rotation;
  }

  public void setScale(Vector3d scale) {
    this.scale = scale;
  }

  public void setTmpNormal(Vector3d tmpNormal) {
    this.tmpNormal = tmpNormal;
  }

  public void setTmpT(double tmpT) {
    this.tmpT = tmpT;
  }

  public void setShader(PaverShader shader) {
    this.shader = shader;
  }

  public static PaverObject fromJRS(JRSObject object) {
    PaverObject paverObject = new PaverObject();
    return paverObject;
  }

  public double intersect(Ray ray) {
    return -1;
  }
}
