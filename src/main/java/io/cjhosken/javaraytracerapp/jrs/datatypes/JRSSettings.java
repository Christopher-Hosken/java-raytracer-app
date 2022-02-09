package io.cjhosken.javaraytracerapp.jrs.datatypes;

import org.json.JSONArray;
import org.json.JSONObject;

public class JRSSettings {
    private int[] resolution;
    private int samples;
    private int bounces;

    public JRSSettings() {
        resolution = new int[] { 680, 420 };
        samples = 16;
        bounces = 4;
    }

    public int width() {
        return resolution[0];
    }

    public int height() {
        return resolution[1];
    }

    public int samples() {
        return samples;
    }

    public int bounces() {
        return bounces;
    }

    public void setWidth(int width) {
        resolution[0] = width;
    }

    public void setHeight(int height) {
        resolution[1] = height;
    }

    public void setSamples(int samples) {
        this.samples = samples;
    }

    public void setBounces(int bounces) {
        this.bounces = bounces;
    }

    public JSONObject toJSON() {
        JSONObject settings = new JSONObject();

        settings.put("resolution", new JSONArray(resolution));
        settings.put("samples", samples);
        settings.put("bounces", bounces);

        return settings;
    }

    public static JRSSettings fromJSON(JSONObject jrs) {
        JRSSettings jrsSettings = new JRSSettings();
        jrsSettings.setWidth(jrs.getJSONArray("resolution").getInt(0));
        jrsSettings.setHeight(jrs.getJSONArray("resolution").getInt(1));

        jrsSettings.setSamples(jrs.getInt("samples"));
        jrsSettings.setBounces(jrs.getInt("bounces"));

        return jrsSettings;
    }
}
