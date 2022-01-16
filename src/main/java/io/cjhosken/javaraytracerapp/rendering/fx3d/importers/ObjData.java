package io.cjhosken.javaraytracerapp.rendering.fx3d.importers;

import io.cjhosken.javaraytracerapp.rendering.core.ArrayHelper;

public class ObjData {
    private String name = "";
    private float[] points = new float[0];
    private float[] texCoords = new float[0];
    private float[] normals = new float[0];
    private int[] faces = new int[0];

    public float[] points() {
        return points;
    }

    public float[] texCoords() {
        return texCoords;
    }

    public float[] normals() {
        return normals;
    }

    public int[] faces() {
        return faces;
    }

    public String name() {
        return name;
    }

    public void setName(String n) {
        name = n;
    }

    public void AddPoints(float fx, float fy, float fz) {
        points = ArrayHelper.addToFloat(points, fx);
        points = ArrayHelper.addToFloat(points, fy);
        points = ArrayHelper.addToFloat(points, fz);
    }

    public void AddTexCoord(float u, float v) {
        texCoords = ArrayHelper.addToFloat(texCoords, u);
        texCoords = ArrayHelper.addToFloat(texCoords, v);
    }

    public void AddNormals(float fnx, float fny, float fnz) {
        normals = ArrayHelper.addToFloat(normals, fnx);
        normals = ArrayHelper.addToFloat(normals, fny);
        normals = ArrayHelper.addToFloat(normals, fnz);
    }

    public void AddFace(int vdx, int uvdx, int ndx) {
        faces = ArrayHelper.addToInt(faces, vdx);
        faces = ArrayHelper.addToInt(faces, ndx);
        faces = ArrayHelper.addToInt(faces, uvdx);
    }

    public String toString() {
        String out = "";
        out += "Object Name: " + name + "\n";
        out += "Vertices: " + points.length + " (" + points.length / 3 + ")" + "\n";
        out += "Normals: " + normals.length + "(" + normals.length / 3 + ")" + "\n";
        out += "UVs: " + texCoords.length + " (" + texCoords.length / 2 + ")" + "\n";
        out += "Faces: " + faces.length + " (" + faces.length / 9 + ")" + "\n";
        return out;
    }
}
