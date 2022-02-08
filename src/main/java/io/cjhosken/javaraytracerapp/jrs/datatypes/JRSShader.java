package io.cjhosken.javaraytracerapp.jrs.datatypes;

import org.json.JSONObject;

import io.cjhosken.javaraytracerapp.core.Vector3d;
import io.cjhosken.javaraytracerapp.rendering.fx3d.FX3DShader;

public class JRSShader {
    private String name;
    private Vector3d color;
    private double roughness;
    
    public JRSShader() {
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

    public FX3DShader toFX3D() {
        FX3DShader shader = new FX3DShader();
        return shader;
    }

    public void fromFX3D(FX3DShader shader) {}

    public void fromJSON(JSONObject jrs) {
        name = jrs.getString("name");
        color.fromJSONArray(jrs.getJSONArray("color"));
        roughness = jrs.getDouble("roughness");
    }

    public JSONObject toJSON() {
        JSONObject shader = new JSONObject();

        shader.put("name", name);
        shader.put("color", color.toJSONArray());
        shader.put("roughness", roughness);

        return shader;
    }
}
