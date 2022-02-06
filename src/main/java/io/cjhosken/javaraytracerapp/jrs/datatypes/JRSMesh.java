package io.cjhosken.javaraytracerapp.jrs.datatypes;

import org.json.JSONObject;

import io.cjhosken.javaraytracerapp.core.Vector2d;
import io.cjhosken.javaraytracerapp.core.Vector3d;

public class JRSMesh {
    private Vector3d[] vertices = new Vector3d[0];
    private Vector2d[] texCoords = new Vector2d[0];
    private Vector3d[] normals = new Vector3d[0];
    private int[] indices = new int[0];

    public JSONObject toJSON() {
        JSONObject mesh = new JSONObject();

        mesh.put("vertices", vertices);
        mesh.put("texCoords", texCoords);
        mesh.put("normals", normals);
        mesh.put("indices", indices);

        return mesh;
    }

    public void fromJSON(JSONObject jrs) {
        for (int vdx = 0; vdx < jrs.getJSONArray("vertices").length(); vdx += 3) {
            vertices[vdx] = new Vector3d(
                jrs.getJSONArray("vertices").getDouble(vdx), 
                jrs.getJSONArray("vertices").getDouble(vdx + 1),
                jrs.getJSONArray("vertices").getDouble(vdx + 2)
            );
        }

        for (int tdx = 0; tdx < jrs.getJSONArray("texCoords").length(); tdx += 2) {
            texCoords[tdx] = new Vector2d(
                jrs.getJSONArray("texCoords").getDouble(tdx), 
                jrs.getJSONArray("texCoords").getDouble(tdx + 1)
            );
        }

        for (int ndx = 0; ndx < jrs.getJSONArray("normals").length(); ndx += 3) {
            normals[ndx] = new Vector3d(
                jrs.getJSONArray("normals").getDouble(ndx), 
                jrs.getJSONArray("normals").getDouble(ndx + 1),
                jrs.getJSONArray("normals").getDouble(ndx + 2)
            );
        }

        for (int idx = 0; idx < jrs.getJSONArray("indices").length(); idx += 3) {
            indices[idx] = jrs.getJSONArray("indices").getInt(idx) / 2;
            indices[idx + 1] = jrs.getJSONArray("indices").getInt((idx + 1)) / 2;
            indices[idx + 2] = jrs.getJSONArray("indices").getInt((idx + 2)) / 3;
        }
    }
}
