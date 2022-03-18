package io.cjhosken.javaraytracerapp.jrs.datatypes;

import org.json.JSONObject;

import io.cjhosken.javaraytracerapp.core.Vector3d;
import io.cjhosken.javaraytracerapp.rendering.fx3d.FX3DObject;
import javafx.scene.shape.TriangleMesh;

public class JRSObject {
    protected String name;
    protected JRSObjectType type;
    protected Vector3d location;
    protected Vector3d rotation;
    protected Vector3d scale;

    protected JRSMesh mesh;
    protected double radius;

    protected JRSShader shader;

    public JRSObject() {
        name = "";
        type = JRSObjectType.EMPTY;
        location = new Vector3d();
        rotation = new Vector3d();
        scale = new Vector3d(1, 1, 1);
        mesh = new JRSMesh();
        shader = new JRSShader();
        radius = 1;
    }

    public String name() {
        return name;
    }

    public JRSObjectType type() {
        return type;
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

    public JRSMesh mesh() {
        return mesh;
    }

    public double radius() {
        return radius;
    }

    public TriangleMesh triangleMesh() {
        return  mesh.toTriMesh();
    }

    public JRSShader shader() {
        return shader;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(JRSObjectType type) {
        this.type = type;
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

    public void setMesh(JRSMesh mesh) {
        this.mesh = mesh;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public void fromTriangleMesh(TriangleMesh mesh) {
        this.mesh = JRSMesh.fromTriMesh(mesh);
    }

    public void setShader(JRSShader shader) {
        this.shader = shader;
    }

    public static JRSObject fromFX3D(FX3DObject object) {
        return object.toJRS();
    }

    public FX3DObject toFX3D() {
        return FX3DObject.fromJRS(this);
    }

    public JSONObject toJSON() {
        JSONObject object = new JSONObject();
        object.put("name", name);
        object.put("type", type);
        object.put("location", location.toJSONArray());
        object.put("rotation", rotation.toJSONArray());
        object.put("scale", scale.toJSONArray());

        if (type == JRSObjectType.OBJ) {
            object.put("mesh", mesh.toJSON());
        } else if (type == JRSObjectType.SPHERE) {
            object.put("radius", radius);
        }

        object.put("shader", shader.toJSON());

        return object;
    }

    public static JRSObject fromJSON(JSONObject jrs) {
        JRSObject jrsObject = new JRSObject();
        jrsObject.setName(jrs.getString("name"));
        jrsObject.setType(jrs.getEnum(JRSObjectType.class, "type"));
        jrsObject.setLocation(Vector3d.fromJSONArray(jrs.getJSONArray("location")));
        jrsObject.setRotation(Vector3d.fromJSONArray(jrs.getJSONArray("rotation")));
        jrsObject.setScale(Vector3d.fromJSONArray(jrs.getJSONArray("scale")));

        if (jrsObject.type() == JRSObjectType.OBJ) {
            jrsObject.setMesh(JRSMesh.fromJSON(jrs.getJSONObject("mesh")));
        } if (jrsObject.type() == JRSObjectType.SPHERE) {
            jrsObject.setRadius(jrs.getDouble("radius"));
        }

        jrsObject.setShader(JRSShader.fromJSON(jrs.getJSONObject("shader")));

        return jrsObject;
    }
}
