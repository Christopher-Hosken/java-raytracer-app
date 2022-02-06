package io.cjhosken.javaraytracerapp.helpers.jrs;

import org.json.JSONArray;
import org.json.JSONObject;

import io.cjhosken.javaraytracerapp.rendering.core.Vec3;
import io.cjhosken.javaraytracerapp.rendering.fx3d.FX3DRenderer;
import io.cjhosken.javaraytracerapp.rendering.fx3d.RendererObject;
import io.cjhosken.javaraytracerapp.rendering.paver.data.base.Face;
import io.cjhosken.javaraytracerapp.rendering.paver.data.base.PaverMeshObject;
import io.cjhosken.javaraytracerapp.rendering.paver.data.base.PaverTriMeshObject;
import io.cjhosken.javaraytracerapp.rendering.paver.data.base.PaverWorld;
import io.cjhosken.javaraytracerapp.rendering.paver.data.base.Tri;
import io.cjhosken.javaraytracerapp.rendering.paver.data.objects.PaverCamera;
import io.cjhosken.javaraytracerapp.rendering.paver.data.objects.primitives.PaverCube;
import io.cjhosken.javaraytracerapp.rendering.paver.data.objects.primitives.PaverPlane;
import io.cjhosken.javaraytracerapp.rendering.paver.data.objects.primitives.PaverSphere;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.Sphere;
import javafx.scene.shape.TriangleMesh;

import java.time.*;
import java.util.ArrayList;

public class JRSUtils {
    public static JSONObject write(FX3DRenderer renderer, String inWidth, String inHeight, String inSamples, String inBounces) {


        int width = Integer.valueOf(inWidth);
        int height = Integer.valueOf(inHeight);

        int samples = Integer.valueOf(inSamples);
        int bounces = Integer.valueOf(inBounces);

        Camera renderCam = renderer.renderCamera();
        Group inObjects = renderer.objects();

        JSONObject info = new JSONObject();
        info.put("version", "v01");
        info.put("lastSaved", LocalDate.now());
        info.put("comment", "The JRS (Java Raytracer Scene) file extension is specific to the Java Raytracer App. It's essentially just a fancy JSON file.");


        JSONObject settings = new JSONObject();
        settings.put("resolution", new JSONArray().put(width).put(height));
        settings.put("samples", samples);
        settings.put("bounces", bounces);
        
        JSONObject camera = new JSONObject();
        camera.put("location", new JSONArray().put(renderCam.getTranslateX()).put(renderCam.getTranslateY()).put(renderCam.getTranslateZ()));
        camera.put("focal", 30);
        settings.put("camera", camera);

        JSONArray objects = new JSONArray();

        JSONArray meshObjects = new JSONArray();

        for (int i = 0; i < inObjects.getChildren().size(); i++) {
            RendererObject obj = (RendererObject) inObjects.getChildren().get(i);

            JSONObject jrsObject = new JSONObject();
            jrsObject.put("name", "Object Name");
            jrsObject.put("type", obj.type());
            jrsObject.put("isMesh", obj.isMesh());
            jrsObject.put("location", new JSONArray().put(obj.getTranslateZ()).put(obj.getTranslateY()).put(obj.getTranslateX()));
            jrsObject.put("rotation", new JSONArray().put(0).put(0).put(0));
            jrsObject.put("scale", new JSONArray().put(1).put(1).put(1));

            if (!obj.isMesh()) {
                switch (obj.type()) {
                    case "sphere":
                        Sphere sph = (Sphere) obj.getChildren().get(0);
                        jrsObject.put("radius", sph.getRadius());
                }

                objects.put(jrsObject);
                
            }

            else {
                MeshView objModel = (MeshView) obj.getChildren().get(0);
                TriangleMesh mesh = (TriangleMesh) objModel.getMesh();

                float[] verts = new float[mesh.getPoints().size()];
                float[] texCoords  = new float[mesh.getTexCoords().size()];
                float[] normals = new float[mesh.getNormals().size()];
                int[] faces  = new int[mesh.getFaces().size()];

                verts = mesh.getPoints().toArray(verts);
                texCoords = mesh.getTexCoords().toArray(texCoords);
                normals = mesh.getNormals().toArray(normals);
                faces = mesh.getFaces().toArray(faces);

                jrsObject.put("vertices", new JSONArray(verts));
                jrsObject.put("texCoords", new JSONArray(texCoords));
                jrsObject.put("faces", new JSONArray(faces));
                jrsObject.put("normals", new JSONArray(normals));

                meshObjects.put(jrsObject);
            }

        }

        JSONObject world = new JSONObject();
        world.put("objects", objects);
        world.put("meshObjects", meshObjects);

        JSONObject scene = new JSONObject();
        scene.put("settings", settings);
        scene.put("world", world);

        JSONObject jrsFile = new JSONObject();
        jrsFile.put("info", info);
        jrsFile.put("scene", scene);

        System.out.println(jrsFile.toString());

        return jrsFile;
    }

    public static PaverCamera getPaverCameraFromJRS(JSONObject jrs) {
        JSONObject scene = jrs.getJSONObject("scene");

        JSONObject settings = scene.getJSONObject("settings");

        JSONObject camera = settings.getJSONObject("camera");

        JSONArray cameraLocation = camera.getJSONArray("location");
        double focal = camera.getDouble("focal");

        int width = settings.getJSONArray("resolution").getInt(0);
        int height = settings.getJSONArray("resolution").getInt(1);
        
        return new PaverCamera(width, height, new Vec3(0, 0, 25), new Vec3(0, 0, 0), focal, 2, (double) width / height, 5);
    }

    public static int getSamplesFromJRS(JSONObject jrs) {

        JSONObject scene = jrs.getJSONObject("scene");

        JSONObject settings = scene.getJSONObject("settings");

        return settings.getInt("samples");
    }

    public static int getBouncesFromJRS(JSONObject jrs) {
        JSONObject scene = jrs.getJSONObject("scene");

        JSONObject settings = scene.getJSONObject("settings");
        return settings.getInt("bounces");
    }

    public static PaverWorld getPaverWorldFromJRS(JSONObject jrs) {
        PaverWorld paverWorld = new PaverWorld();

        JSONObject scene = jrs.getJSONObject("scene");

        JSONObject world = scene.getJSONObject("world");

        JSONArray objects = world.getJSONArray("objects");

        JSONArray meshObjects = world.getJSONArray("meshObjects");

        for (int i = 0; i < objects.length(); i++) {
            
            JSONObject obj = objects.getJSONObject(i);

            switch (obj.getString("type")) {
                case "sphere":
                    double radius = obj.getDouble("radius");
                    PaverSphere sph = new PaverSphere("Paver Sphere", radius);
                    paverWorld.add(sph);
                case "cube":
                    PaverCube cube = new PaverCube("Paver Cube");
                    paverWorld.add(cube);
                case "plane":
                    PaverPlane plane = new PaverPlane("Paver Plane");
                    paverWorld.add(plane);
            }
        }

        for (int mi = 0; mi < meshObjects.length(); mi++) {
            JSONObject mobj = meshObjects.getJSONObject(mi);

            double[] vertices = new double[mobj.getJSONArray("vertices").length()];
            double[] texCoords = new double[mobj.getJSONArray("texCoords").length()];
            int[] faces = new int[mobj.getJSONArray("faces").length()];
            double[] normals = new double[mobj.getJSONArray("normals").length()];

            for (int vdx = 0; vdx < mobj.getJSONArray("vertices").length(); vdx++) {
                vertices[vdx] = mobj.getJSONArray("vertices").getDouble(vdx);
            }

            for (int vdx = 0; vdx < mobj.getJSONArray("texCoords").length(); vdx++) {
                texCoords[vdx] = mobj.getJSONArray("texCoords").getDouble(vdx);
            }

            for (int vdx = 0; vdx < mobj.getJSONArray("faces").length(); vdx++) {
                faces[vdx] = mobj.getJSONArray("faces").getInt(vdx);
            }

            for (int vdx = 0; vdx < mobj.getJSONArray("normals").length(); vdx++) {
                normals[vdx] = mobj.getJSONArray("normals").getDouble(vdx);
            }

            PaverTriMeshObject meshObject = new PaverTriMeshObject("Mesh Object");

            ArrayList<Tri> meshData = new ArrayList<Tri>();

            for (int fdx = 0; fdx < faces.length; fdx += 9) {
                meshData.add(new Tri(
                new Vec3(vertices[faces[fdx]], vertices[faces[fdx] + 1], vertices[faces[fdx]] + 2), 
                new Vec3(vertices[faces[fdx + 3]], vertices[faces[fdx + 3] + 1], vertices[faces[fdx + 3] + 2]), 
                new Vec3(vertices[faces[fdx + 6]], vertices[faces[fdx + 6] + 1], vertices[faces[fdx + 6] + 2])));
            }

            Tri[] tris = new Tri[meshData.size()];

            for (int tdx = 0; tdx < meshData.size(); tdx++) {
                tris[tdx] = meshData.get(tdx);
            }

            meshObject.setMesh(tris);

            paverWorld.add(meshObject);
        }

        return paverWorld;
    }

    public static int getImageWidthFromJRS(JSONObject jrs) {

        JSONObject scene = jrs.getJSONObject("scene");

        JSONObject settings = scene.getJSONObject("settings");

        return settings.getJSONArray("resolution").getInt(0);
    }

    public static int getImageHeightFromJRS(JSONObject jrs) {

        JSONObject scene = jrs.getJSONObject("scene");

        JSONObject settings = scene.getJSONObject("settings");

        return settings.getJSONArray("resolution").getInt(1);
    }


    public static Group getGLWorldFromJRS(JSONObject jrs) {
        Group worldObjects = new Group();

        JSONObject scene = jrs.getJSONObject("scene");

        JSONObject world = scene.getJSONObject("world");

        JSONArray objects = world.getJSONArray("objects");

        JSONArray meshObjects = world.getJSONArray("meshObjects");
        return worldObjects;
    }

    public static Camera getGLRenderCameraFromJRS(JSONObject jrs) {
        return new PerspectiveCamera();
    }
}