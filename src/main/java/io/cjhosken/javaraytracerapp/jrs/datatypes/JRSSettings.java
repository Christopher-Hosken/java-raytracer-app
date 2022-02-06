package io.cjhosken.javaraytracerapp.jrs.datatypes;

import org.json.JSONArray;
import org.json.JSONObject;

public class JRSSettings {
    private int[] resolution = new int[] {680, 420};
    private int samples = 16;
    private int bounces = 4;

    public JSONObject toJSON() {
        JSONObject settings = new JSONObject();

        settings.put("resolution", new JSONArray(resolution));
        settings.put("samples", samples);
        settings.put("bounces", bounces);

        return settings;
    }

    public void fromJSON(JSONObject jrs) {
        resolution = new int[] {jrs.getJSONArray("resolution").getInt(0), jrs.getJSONArray("resolution").getInt(1)};
        samples = jrs.getInt("samples");
        bounces = jrs.getInt("boucnes");
    }
}
