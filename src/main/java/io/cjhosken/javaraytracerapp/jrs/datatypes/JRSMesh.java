package io.cjhosken.javaraytracerapp.jrs.datatypes;

import org.json.JSONObject;

import io.cjhosken.javaraytracerapp.core.Vector2d;
import io.cjhosken.javaraytracerapp.core.Vector3d;

public class JRSMesh {
    private Vector3d[] vertices = new Vector3d[0];
    private Vector2d[] texCoords = new Vector2d[0];
    private Vector3d[] normals = new Vector3d[0];
    private int[] indices = new int[0];

    public JRSMesh() {
        vertices = new Vector3d[0];
        texCoords = new Vector2d[0];
        normals = new Vector3d[0];
        indices = new int[0];
    }

    public Vector3d[] vertices() {
        return vertices;
    }

    public Vector2d[] texCoords() {
        return texCoords;
    }

    public Vector3d[] normals() {
        return normals;
    }

    public int[] indices() {
        return indices;
    }

    public void setVertices(Vector3d[] vertices) {
        this.vertices = vertices;
    }

    public void setTexCoords(Vector2d[] texCoords) {
        this.texCoords = texCoords;
    }

    public void setNormals(Vector3d[] normals) {
        this.normals = normals;
    }

    public void setIndices(int[] indices) {
        this.indices = indices;
    }

    public JSONObject toJSON() {
        JSONObject mesh = new JSONObject();

        mesh.put("vertices", vertices);
        mesh.put("texCoords", texCoords);
        mesh.put("normals", normals);
        mesh.put("indices", indices);

        return mesh;
    }

    public void fromJSON(JSONObject jrs) {
        vertices = new Vector3d[jrs.getJSONArray("vertices").length() / 3]; 
        for (int vdx = 0; vdx < jrs.getJSONArray("vertices").length(); vdx += 3) {
            vertices[vdx] = new Vector3d(
                jrs.getJSONArray("vertices").getDouble(vdx), 
                jrs.getJSONArray("vertices").getDouble(vdx + 1),
                jrs.getJSONArray("vertices").getDouble(vdx + 2)
            );
        }

        texCoords = new Vector2d[jrs.getJSONArray("texCoords").length() / 2]; 

        for (int tdx = 0; tdx < jrs.getJSONArray("texCoords").length(); tdx += 2) {
            texCoords[tdx] = new Vector2d(
                jrs.getJSONArray("texCoords").getDouble(tdx), 
                jrs.getJSONArray("texCoords").getDouble(tdx + 1)
            );
        }

        normals = new Vector3d[jrs.getJSONArray("normals").length() / 3]; 

        for (int ndx = 0; ndx < jrs.getJSONArray("normals").length(); ndx += 3) {
            normals[ndx] = new Vector3d(
                jrs.getJSONArray("normals").getDouble(ndx), 
                jrs.getJSONArray("normals").getDouble(ndx + 1),
                jrs.getJSONArray("normals").getDouble(ndx + 2)
            );
        }

        indices = new int[jrs.getJSONArray("indices").length()]; 

        for (int idx = 0; idx < jrs.getJSONArray("indices").length(); idx += 3) {
            indices[idx] = jrs.getJSONArray("indices").getInt(idx) / 2;
            indices[idx + 1] = jrs.getJSONArray("indices").getInt((idx + 1)) / 2;
            indices[idx + 2] = jrs.getJSONArray("indices").getInt((idx + 2)) / 3;
        }
    }
}
