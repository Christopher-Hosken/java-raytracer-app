package io.cjhosken.javaraytracerapp.jrs.datatypes;

import org.json.JSONObject;

import io.cjhosken.javaraytracerapp.core.Vector2d;
import io.cjhosken.javaraytracerapp.core.Vector3d;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.shape.VertexFormat;

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

    public void setVertexAt(int idx, Vector3d vec) {
        vertices[idx] = vec;
    }

    public void setTexCoordAt(int idx, Vector2d tex) {
        texCoords[idx] = tex;
    }

    public void setNormalAt(int idx, Vector3d nor) {
        normals[idx] = nor;
    }

    public void setIndexAt(int idx, int id) {
        indices[idx] = id;
    }

    public float[] toTriPoints() {
        float[] out = new float[vertices.length * 3];
        for (int vdx = 0; vdx < out.length; vdx += 3) {
            out[vdx] = (float) vertices[vdx / 3].x;
            out[vdx + 1] = (float) vertices[vdx / 3].y;
            out[vdx + 2] = (float) vertices[vdx / 3].z;
        }

        return out;
    }

    public float[] toTriNormals() {
        float[] out = new float[normals.length * 3];
        for (int ndx = 0; ndx < out.length; ndx += 3) {
            out[ndx] = (float) normals[ndx / 3].x;
            out[ndx + 1] = (float) vertices[ndx / 3].y;
            out[ndx + 2] = (float) vertices[ndx / 3].z;
        }

        return out;
    }

    public float[] toTriTexCoords() {
        float[] out = new float[texCoords.length * 2];
        for (int tdx = 0; tdx < out.length; tdx += 2) {
            out[tdx] = (float) normals[tdx / 2].x;
            out[tdx + 1] = (float) vertices[tdx / 2].y;
        }

        return out;
    }

    public int[] toTriFaces() {
        int[] out = new int[indices.length];
        for (int fdx = 0; fdx < out.length; fdx += 3) {
            out[fdx] = indices[fdx] * 3;
            out[fdx + 1] = indices[fdx + 1] * 2;
            out[fdx + 2] = indices[fdx + 2] * 3;
        }

        return out;
    }

    public JSONObject toJSON() {
        JSONObject mesh = new JSONObject();

        mesh.put("vertices", vertices);
        mesh.put("texCoords", texCoords);
        mesh.put("normals", normals);
        mesh.put("indices", indices);

        return mesh;
    }

    public TriangleMesh toTriMesh() {
        TriangleMesh triMesh = new TriangleMesh();
        triMesh.setVertexFormat(VertexFormat.POINT_NORMAL_TEXCOORD);

        triMesh.getPoints().setAll(toTriPoints());
        triMesh.getNormals().setAll(toTriNormals());
        triMesh.getTexCoords().setAll(toTriTexCoords());
        triMesh.getFaces().setAll(toTriFaces());

        return triMesh;
    }

    public static JRSMesh fromTriMesh(TriangleMesh mesh) {
        JRSMesh jrsMesh = new JRSMesh();

        jrsMesh.setVertices(new Vector3d[mesh.getPoints().size() / 3]);
        for (int vdx = 0; vdx < mesh.getPoints().size(); vdx += 3) {
            jrsMesh.setVertexAt(vdx, new Vector3d(
                    mesh.getPoints().get(vdx),
                    mesh.getPoints().get(vdx + 1),
                    mesh.getPoints().get(vdx + 2)));
        }

        jrsMesh.setTexCoords(new Vector2d[mesh.getTexCoords().size() / 2]);
        for (int tdx = 0; tdx < mesh.getTexCoords().size(); tdx += 2) {
            jrsMesh.setTexCoordAt(tdx, new Vector2d(
                    mesh.getTexCoords().get(tdx),
                    mesh.getTexCoords().get(tdx + 1)));
        }

        jrsMesh.setNormals(new Vector3d[mesh.getNormals().size() / 3]);
        for (int ndx = 0; ndx < mesh.getNormals().size(); ndx += 3) {
            jrsMesh.setNormalAt(ndx, new Vector3d(
                    mesh.getPoints().get(ndx),
                    mesh.getPoints().get(ndx + 1),
                    mesh.getPoints().get(ndx + 2)));
        }

        jrsMesh.setIndices(new int[mesh.getFaces().size()]);
        for (int idx = 0; idx < mesh.getFaces().size(); idx += 3) {
            jrsMesh.setIndexAt(idx, mesh.getFaces().get(idx) / 3);
            jrsMesh.setIndexAt(idx + 1, mesh.getFaces().get(idx + 1) / 2);
            jrsMesh.setIndexAt(idx + 2, mesh.getFaces().get(idx + 2) / 3);
        }

        return jrsMesh;
    }

    public static JRSMesh fromJSON(JSONObject jrs) {
        JRSMesh mesh = new JRSMesh();

        mesh.setVertices(new Vector3d[jrs.getJSONArray("vertices").length() / 3]);
        for (int vdx = 0; vdx < jrs.getJSONArray("vertices").length(); vdx += 3) {
            mesh.setVertexAt(vdx, new Vector3d(
                    jrs.getJSONArray("vertices").getDouble(vdx),
                    jrs.getJSONArray("vertices").getDouble(vdx + 1),
                    jrs.getJSONArray("vertices").getDouble(vdx + 2)));
        }

        mesh.setTexCoords(new Vector2d[jrs.getJSONArray("texCoords").length() / 2]);

        for (int tdx = 0; tdx < jrs.getJSONArray("texCoords").length(); tdx += 2) {
            mesh.setTexCoordAt(tdx, new Vector2d(
                    jrs.getJSONArray("texCoords").getDouble(tdx),
                    jrs.getJSONArray("texCoords").getDouble(tdx + 1)));
        }

        mesh.setNormals(new Vector3d[jrs.getJSONArray("normals").length() / 3]);

        for (int ndx = 0; ndx < jrs.getJSONArray("normals").length(); ndx += 3) {
            mesh.setNormalAt(ndx, new Vector3d(
                    jrs.getJSONArray("normals").getDouble(ndx),
                    jrs.getJSONArray("normals").getDouble(ndx + 1),
                    jrs.getJSONArray("normals").getDouble(ndx + 2)));
        }

        mesh.setIndices(new int[jrs.getJSONArray("indices").length()]);

        for (int idx = 0; idx < jrs.getJSONArray("indices").length(); idx += 3) {
            mesh.setIndexAt(idx, jrs.getJSONArray("indices").getInt(idx) / 3);
            mesh.setIndexAt(idx + 1, jrs.getJSONArray("indices").getInt((idx + 1)) / 2);
            mesh.setIndexAt(idx + 2, jrs.getJSONArray("indices").getInt((idx + 2)) / 3);
        }

        return mesh;
    }
}
