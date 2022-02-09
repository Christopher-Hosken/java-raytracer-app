package io.cjhosken.javaraytracerapp.jrs;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.json.JSONObject;

public class JRSReader {
    public static JRSFile read(File file) throws IOException {
        JSONObject jrsData = new JSONObject(new String(Files.readAllBytes(file.toPath())));
        return JRSFile.fromJSON(jrsData);
    }
}