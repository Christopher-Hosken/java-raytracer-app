package io.cjhosken.javaraytracerapp.rendering.fx3d.importers;

import java.io.File;
import java.io.IOException;

import javafx.scene.image.Image;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.shape.VertexFormat;
import javafx.scene.transform.Rotate;

public class ObjModel extends MeshView {
    public ObjModel(File file) throws IOException {
        super();
        ObjData objData = ObjLoader.load(file);

        System.out.println(objData.toString());
        
        TriangleMesh mesh = new TriangleMesh();
        mesh.getPoints().addAll(objData.points());
        mesh.getTexCoords().addAll(objData.texCoords());
        mesh.getFaces().addAll(objData.faces());
        mesh.getNormals().addAll(objData.normals());
        mesh.setVertexFormat(VertexFormat.POINT_NORMAL_TEXCOORD);

        setCullFace(CullFace.NONE);
        setMesh(mesh);
        setMaterial(new PhongMaterial());
        getTransforms().add(new Rotate(180, Rotate.X_AXIS));
    }
}
