package io.cjhosken.javaraytracerapp.jrs.datatypes;

import org.json.JSONObject;

import io.cjhosken.javaraytracerapp.core.Vector3d;

public class JRSObject {
    protected String name = "";
    protected JRSObjectType type = JRSObjectType.EMPTY;
    protected Vector3d location = new Vector3d();
    protected Vector3d rotation = new Vector3d();
    protected Vector3d scale = new Vector3d(1,1,1);
    protected JRSMesh meshData = new JRSMesh();

    protected JRSShader shader = new JRSShader();

    public JSONObject toJSON() {
        JSONObject object = new JSONObject();
        object.put("name", name);
        object.put("type", type);
        object.put("location", location.toJSONArray());
        object.put("rotation", rotation.toJSONArray());
        object.put("scale", scale.toJSONArray());

        if (type == JRSObjectType.OBJ) {
            object.put("mesh", meshData.toJSON());
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
            meshData.fromJSON(jrs.getJSONObject("mesh"));
        }

        shader.fromJSON(jrs.getJSONObject("shader"));
    }
}