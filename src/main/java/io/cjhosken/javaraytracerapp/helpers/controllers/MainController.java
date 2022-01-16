package io.cjhosken.javaraytracerapp.helpers.controllers;

import javafx.fxml.FXML;
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
        renderer.addToWorld(new RendererObject(new Box(2, 0, 2)));
    }

    @FXML
    private void addSphere() {
        renderer.addToWorld(new RendererObject(new Sphere()));
    }

    @FXML
    private void addCube() {
        renderer.addToWorld(new RendererObject(new Box()));
    }

    @FXML
    private void addCylinder() {
        renderer.addToWorld(new RendererObject(new Cylinder()));
    }

    @FXML
    private void addTeapot() throws IOException {
        renderer.addToWorld(new RendererObject(new ObjModel(new File("models/teapot_low.obj"))));
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

        renderer.addToWorld(new RendererObject(new ObjModel(file)));
        System.out.println("Model Imported");
    }
}