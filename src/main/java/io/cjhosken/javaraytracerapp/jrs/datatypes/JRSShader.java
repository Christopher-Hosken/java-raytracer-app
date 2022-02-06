package io.cjhosken.javaraytracerapp.jrs.datatypes;

import org.json.JSONObject;

import io.cjhosken.javaraytracerapp.core.Vector3d;

public class JRSShader {
    private String name = "";
    private Vector3d color = new Vector3d(1, 0, 1);
    private double specular = 1;
    private double roughness = 0.5;

    public void fromJSON(JSONObject jrs) {
        name = jrs.getString("name");
        color.fromJSONArray(jrs.getJSONArray("color"));
        specular = jrs.getDouble("specular");
        roughness = jrs.getDouble("roughness");
    }

    public JSONObject toJSON() {
        JSONObject shader = new JSONObject();

        shader.put("name", name);
        shader.put("color", color.toJSONArray());
        shader.put("specular", specular);
        shader.put("roughness", roughness);

        return shader;
    }
}