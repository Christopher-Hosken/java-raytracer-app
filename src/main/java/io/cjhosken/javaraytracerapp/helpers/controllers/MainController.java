package io.cjhosken.javaraytracerapp.helpers.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import io.cjhosken.javaraytracerapp.rendering.fx3d.FX3DRenderer;
import io.cjhosken.javaraytracerapp.rendering.fx3d.RendererObject;
import io.cjhosken.javaraytracerapp.rendering.fx3d.importers.ObjModel;

public class MainController {
    @FXML private GridPane root;
    @FXML private HBox header;
    private FX3DRenderer renderer;

    public MainController(FX3DRenderer renderer) {
        this.renderer = renderer;
    }

    @FXML
    private void closeApp() {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void minimizeApp() {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void reportBug() throws IOException, URISyntaxException {
        Desktop.getDesktop().browse(new URI("https://github.com/Christopher-Hosken/java-raytracer-app/issues"));
    }

    @FXML
    private void loadFile() {
        Stage stage = (Stage) root.getScene().getWindow();
        File defaultDir = new File(Paths.get("saves").toAbsolutePath().normalize().toString());
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Java Raytracer Scene (*.jrs)", "*.jrs"));
        fileChooser.setInitialDirectory(defaultDir);
        fileChooser.setTitle("Open Scene");
        File file = fileChooser.showOpenDialog(stage);

        System.out.println(file);
    }

    @FXML
    private void saveFile() throws IOException {
        Stage stage = (Stage) root.getScene().getWindow();
        File defaultDir = new File(Paths.get("saves").toAbsolutePath().normalize().toString());
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Java Raytracer Scene (*.jrs)", "*.jrs"));
        fileChooser.setInitialDirectory(defaultDir);
        fileChooser.setTitle("Save Scene");

        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            saveTextToFile("test", file);
        }
    }

    @FXML
    private void saveTextToFile(String content, File file) throws IOException {
        PrintWriter writer;
        writer = new PrintWriter(file);
        writer.println(content);
        writer.close();
    }


    @FXML
    private void addPlane() {
        Box model = new Box(2, 0, 2);
        RendererObject obj = new RendererObject(model);
        obj.setMesh(false);
        obj.setType("plane");

        renderer.addToWorld(obj);
    }

    @FXML
    private void addSphere() {
        Sphere model = new Sphere();
        RendererObject obj = new RendererObject(model);
        obj.setMesh(false);
        obj.setType("sphere");

        renderer.addToWorld(obj);
    }

    @FXML
    private void addCube() {
        Box model = new Box();
        RendererObject obj = new RendererObject(model);
        obj.setMesh(false);
        obj.setType("box");

        renderer.addToWorld(obj);
    }

    @FXML
    private void addCylinder() {
        Cylinder model = new Cylinder();
        RendererObject obj = new RendererObject(model);
        obj.setMesh(false);
        obj.setType("cylinder");

        renderer.addToWorld(obj);
    }

    @FXML
    private void addTeapot() throws IOException {
        ObjModel model = new ObjModel(new File("models/teapot_low.obj"));
        RendererObject obj = new RendererObject(model);
        obj.setMesh(true);
        obj.setType("obj");

        renderer.addToWorld(obj);
    }

    @FXML
    private void importOBJ() throws IOException {
        Stage stage = (Stage) root.getScene().getWindow();
        File defaultDir = new File(Paths.get("models").toAbsolutePath().normalize().toString());
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Wavefront (*.obj)", "*.obj"));
        fileChooser.setInitialDirectory(defaultDir);
        fileChooser.setTitle("Import Object");
        File file = fileChooser.showOpenDialog(stage);

        ObjModel model = new ObjModel(file);
        RendererObject obj = new RendererObject(model);
        obj.setMesh(true);
        obj.setType("obj");

        renderer.addToWorld(obj);
        System.out.println("Model Imported");
    }

    @FXML
    private void toggleRenderCam() {
        renderer.toggleRenderCamera();
        Stage stage = (Stage) root.getScene().getWindow();
        ImageView img = (ImageView) stage.getScene().lookup("#viewbuttonicon"); 
        if (renderer.isInRenderCam()) {
            img.setImage(new Image("css/icons/photo_camera/photo_camera_24.png"));
        } else {
            img.setImage(new Image("css/icons/eye/eye_24.png"));
        }
    }
}