package io.cjhosken.javaraytracerapp.jrs;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;

import org.json.JSONObject;

import io.cjhosken.javaraytracerapp.jrs.datatypes.JRSSettings;
import io.cjhosken.javaraytracerapp.jrs.datatypes.JRSWorld;

public class JRSFile {
    private String version = "v10";
    private String lastSaved;
    private String comment  = "The JRS (Java Raytracer Scene) file extension is specific to the Java Raytracer App. It's essentially just a fancy JSON file.";

    private JRSSettings settings;
    private JRSWorld world;

    public JRSFile() {
        setDate();
        settings = new JRSSettings();
        world = new JRSWorld();
    }
    
    public JRSFile(JRSSettings settings, JRSWorld world) {

        setDate();
        this.settings = settings;
        this.world = world;
    }

    public String version() {
        return version;
    }

    public String lastSaved() {
        return lastSaved;
    }

    public String comment() {
        return comment;
    }

    public JRSSettings settings() {
        return settings;
    }

    public JRSWorld world() {
        return world;
    }

    private void setDate() {
        LocalDate currentDate = LocalDate.now();
        this.lastSaved = currentDate.getDayOfMonth() + "-" + currentDate.getMonthValue() + "-" + currentDate.getYear();
    }

    public JSONObject toJSON() {
        JSONObject jrs = new JSONObject();

        JSONObject info = new JSONObject();
        info.put("comment", comment);
        info.put("lastSaved", lastSaved);
        info.put("version", version);

        jrs.put("info", info);
        jrs.put("settings", settings.toJSON());
        jrs.put("world", world.toJSON());

        System.out.println(jrs.toString());

        return jrs;
    }

    public void fromJSON(JSONObject jrs) {
        JSONObject info = jrs.getJSONObject("info");
        version = info.getString("version");
        lastSaved = info.getString("lastSaved");
        comment = info.getString("comment");

        settings.fromJSON(jrs.getJSONObject("settings"));
        world.fromJSON(jrs.getJSONObject("world"));
    }

    public static JRSFile load(File file) throws IOException {
        JRSFile jrsFile = JRSReader.read(file);
        System.out.println("Java Raytracer Scene File: " + file + "Opened");
        return jrsFile;
    }
    
    public void save(File file) {
    
    }

}
