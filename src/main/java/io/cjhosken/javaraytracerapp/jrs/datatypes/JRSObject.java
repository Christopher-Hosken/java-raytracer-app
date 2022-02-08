package io.cjhosken.javaraytracerapp.jrs.datatypes;

import org.json.JSONObject;

import io.cjhosken.javaraytracerapp.core.Vector3d;
import io.cjhosken.javaraytracerapp.rendering.fx3d.FX3DShader;
import javafx.scene.shape.TriangleMesh;

public class JRSObject {
    protected String name;
    protected JRSObjectType type;
    protected Vector3d location;
    protected Vector3d rotation;
    protected Vector3d scale;
    protected JRSMesh mesh;

    protected JRSShader shader;

    public JRSObject() {
        name = "";
        type = JRSObjectType.EMPTY;
        location = new Vector3d();
        rotation = new Vector3d();
        scale = new Vector3d(1, 1, 1);
        mesh = new JRSMesh();
        shader = new JRSShader();
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

    public TriangleMesh triangleMesh() {
        TriangleMesh triMesh = new TriangleMesh();

        return triMesh;
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

    public void fromTriangleMesh(TriangleMesh mesh) {
        /* DOTO */
    }

    public void setShader(JRSShader shader) {
        this.shader = shader;
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
        }

        object.put("shader", shader.toJSON());

        return object;
    }

    public void fromJSON(JSONObject jrs) {
        name = jrs.getString("name");
        type = jrs.getEnum(JRSObjectType.class, "type");
        location.fromJSONArray(jrs.getJSONArray("location"));
        rotation.fromJSONArray(jrs.getJSONArray("rotation"));
        scale.fromJSONArray(jrs.getJSONArray("scale"));

        if (type == JRSObjectType.OBJ) {
            mesh.fromJSON(jrs.getJSONObject("mesh"));
        }

        shader.fromJSON(jrs.getJSONObject("shader"));
    }
}
