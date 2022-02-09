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
        return FX3DShader.fromJRS(this);
    }

    public static JRSShader fromFX3D(FX3DShader shader) {
        return shader.toJRS();
    }

    public static JRSShader fromJSON(JSONObject jrs) {
        JRSShader shader = new JRSShader();
        shader.setName(jrs.getString("name"));
        shader.setColor(Vector3d.fromJSONArray(jrs.getJSONArray("color")));
        shader.setRoughness(jrs.getDouble("roughness"));
        return shader;
    }

    public JSONObject toJSON() {
        JSONObject shader = new JSONObject();

        shader.put("name", name);
        shader.put("color", color.toJSONArray());
        shader.put("roughness", roughness);

        return shader;
    }
}
