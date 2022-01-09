package io.cjhosken.javaraytracerapp.helpers.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import com.jogamp.opengl.util.Animator;

import io.cjhosken.javaraytracerapp.rendering.opengl.GLRenderer;

public class MainController {
    @FXML private GridPane root;
    @FXML private HBox header;
    private Animator animator;
    private GLRenderer renderer;

    public MainController(GLRenderer renderer, Animator animator) {
        this.renderer = renderer;
        this.animator = animator;
    }

    @FXML
    private void closeApp() {
        animator.stop();
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
        Desktop.getDesktop().browse(new URI("https://github.com/Christopher-Hosken/java_raytracer_app/issues"));
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
        renderer.addObject("plane");
    }

    @FXML
    private void addSphere() {
        renderer.addObject("sphere");
    }

    @FXML
    private void addCube() {
        renderer.addObject("cube");
    }


}