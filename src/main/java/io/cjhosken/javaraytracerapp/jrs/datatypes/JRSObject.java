package io.cjhosken.javaraytracerapp.jrs.datatypes;

import org.json.JSONObject;

import io.cjhosken.javaraytracerapp.core.Vector3d;

public class JRSObject {
    protected String name = "";
    protected JRSObjectType type = JRSObjectType.EMPTY;
    protected Vector3d location = new Vector3d();
    protected Vector3d rotation = new Vector3d();
    protected Vector3d scale = new Vector3d(1,1,1);
    protected JRSMesh mesh = new JRSMesh();

    protected JRSShader shader = new JRSShader();
    
    public JRSObject(String name, JRSObjectType type) {
        this.name = name;
        this.type = type;
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
    
    public TriangleMesh mesh() {
        TriangleMesh triMesh = new TriangleMesh();
        
        return triMesh;
    }
    
    public JRSShader shader() {
        return shader;
    }
    
    public FX3DShader shader() {
        FX3DShader fx3dShader = new FX3DShader();
        
        return fx3dShader;
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
        this.mesh = mesh
    }
    
    public void setMesh(TriangleMesh mesh) {
    
    }
    
    public void setShader(JRSShader shader) {
        this.shader= shader;
    }
    
    public void setShader(FX3DShader shader) {
    
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
        location.fromJSONArray(jrs.getJSONArray("rotation"));
        location.fromJSONArray(jrs.getJSONArray("scale"));

        if (type == JRSObjectType.OBJ) {
            mesh.fromJSON(jrs.getJSONObject("mesh"));
        }

        shader.fromJSON(jrs.getJSONObject("shader"));
    }
}
