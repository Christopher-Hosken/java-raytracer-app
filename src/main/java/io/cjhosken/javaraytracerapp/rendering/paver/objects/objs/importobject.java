package io.cjhosken.javaraytracerapp.rendering.paver.objects.objs;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import io.cjhosken.javaraytracerapp.rendering.paver.base.*;
import io.cjhosken.javaraytracerapp.rendering.paver.shaders.*;

public class importobject extends meshobject {
    protected File file;


    public importobject(String name, File file) {
        super(name);
        this.file = file;
        readFile();
    }

    public importobject(String name, File file, shader shader) {
        super(name, shader);
        this.file = file;
        readFile();
    }

    public void readFile() {
        ArrayList<vec3> verts = new ArrayList<vec3>();
        ArrayList<face> faces = new ArrayList<face>();

        try {
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                String header = data.split(" ")[0];
                
                if (header.equals("v")) {
                    double vx = Double.valueOf(data.split(" ")[1]);
                    double vy = Double.valueOf(data.split(" ")[2]);
                    double vz = Double.valueOf(data.split(" ")[3]);
                    
                    verts.add(new vec3(vx, vy, vz));
                }

                if (header.equals("f")) {
                    int i0 = Integer.valueOf(data.split(" ")[1].split("/")[0]) - 1;
                    int i1 = Integer.valueOf(data.split(" ")[2].split("/")[0]) - 1;
                    int i2 = Integer.valueOf(data.split(" ")[3].split("/")[0]) - 1;
                    
                    faces.add(new face("f", verts.get(i0), verts.get(i1), verts.get(i2)));
                }
            }

            mesh = faces.toArray(new face[faces.size()]);
            reader.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("Obj File path not found!");
            e.printStackTrace();
        }
    }
}