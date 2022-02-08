package io.cjhosken.javaraytracerapp.rendering.fx3d;

import io.cjhosken.javaraytracerapp.core.Vector3d;
import io.cjhosken.javaraytracerapp.jrs.datatypes.JRSShader;
import javafx.scene.paint.PhongMaterial;

public class FX3DShader {
  private String name;
  private Vector3d color;
  private double roughness;

  public FX3DShader() {
    name = "";
    color = new Vector3d(1, 1, 1);
    roughness = 0.5;
  }

  public String name() {
    return name;
  }

  public Vector3d color() {
    return color;
  }


  public double roughness() {
    return roughness;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setColor(Vector3d color) {
    this.color = color;
  }


  public void setRoughness(double roughness) {
    this.roughness = roughness;
  }

  public PhongMaterial toPhong() {
    PhongMaterial mat = new PhongMaterial();
    mat.setDiffuseColor(color.toColor());
    mat.setSpecularPower(roughness);
    return mat;
  }

  public JRSShader toJRS() {
    JRSShader shader = new JRSShader();
    shader.setName(name);
    shader.setColor(color);
    shader.setRoughness(roughness);
    return shader;
  }

  public void fromJRS(JRSShader shader) {
    name = shader.name();
    color = shader.color();
    roughness = shader.roughness();
  }
}
