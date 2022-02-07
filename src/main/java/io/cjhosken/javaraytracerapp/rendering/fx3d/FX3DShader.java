public class FX3DShader {
  private String name;
  private Vector3d color;
  private double specular;
  private double roughness;
  
  public FX3DShader() {
  
  }
  
  public PhongShader toPhong() {
   
  }
  
  public JRSShader toJRS() {
    JRSShader shader = new JRSShader();
    shader.setName(name);
    shader.setColor(color);
    shader.setSpecular(specular);
    shader.setRoughness(roughness);
  }
 
  public void fromJRS(JRSShader shader) {
    name = shader.name();
    color = shader.color();
    specular = shader.specular();
    roughness = shader.roughness();
  }
}
