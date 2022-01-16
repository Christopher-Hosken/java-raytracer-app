package io.cjhosken.javaraytracerapp.rendering.fx3d.importers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class ObjLoader {
    public static ObjData load(File file) throws IOException {
        ObjData model = new ObjData();

        List<String> lines = Files.readAllLines(file.toPath());

        for (String line : lines) {
            if (line.length() > 0) {
                String header = line.substring(0, line.indexOf(" "));
                String body = line.substring(line.indexOf(" ") + 1);

                if (header.equals("#")) {
                    System.out.println(body);

                } else if (header.equals("o")) {
                    model.setName(body);

                } else if (header.equals("v")) {
                    String[] verts = body.split(" ");
                    model.AddPoints(Float.valueOf(verts[0]), Float.valueOf(verts[1]), Float.valueOf(verts[2]));

                } else if (header.equals("vt")) {
                    String[] uvs = body.split(" ");
                    model.AddTexCoord(Float.valueOf(uvs[0]), Float.valueOf(uvs[1]));

                } else if (header.equals("vn")) {
                    String[] normals = body.split(" ");
                    model.AddNormals(Float.valueOf(normals[0]), Float.valueOf(normals[1]), Float.valueOf(normals[2]));

                } else if (header.equals("f")) {
                    String[] faces = body.split(" ");
                    for (String face : faces) {
                        String[] faceElements = face.split("/");
                        model.AddFace(Integer.valueOf(faceElements[0]) - 1, Integer.valueOf(faceElements[1]) - 1, Integer.valueOf(faceElements[2]) - 1);
                    }
                }
            }
        }




        return model;
    }
}
