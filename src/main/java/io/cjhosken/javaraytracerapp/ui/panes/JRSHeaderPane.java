package io.cjhosken.javaraytracerapp.ui.panes;

import java.io.File;
import java.net.URI;
import java.awt.*;

import io.cjhosken.javaraytracerapp.core.Utils;
import io.cjhosken.javaraytracerapp.jrs.JRSFile;
import io.cjhosken.javaraytracerapp.jrs.datatypes.JRSObjectType;
import io.cjhosken.javaraytracerapp.rendering.fx3d.FX3DObject;
import io.cjhosken.javaraytracerapp.rendering.fx3d.FX3DRenderer;
import io.cjhosken.javaraytracerapp.ui.JRSRoot;
import io.cjhosken.javaraytracerapp.ui.JRSUI;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import javafx.stage.FileChooser;

public class JRSHeaderPane extends HBox {
    JRSRoot root;
    FX3DRenderer renderer;
    
    public JRSHeaderPane(JRSRoot root, FX3DRenderer renderer) {
        super();
        this.root = root;
        this.renderer = renderer;
        prefWidthProperty().bind(root.widthProperty());
        setPrefHeight(JRSUI.NAVHEIGHT);
        setMinHeight(JRSUI.NAVHEIGHT);
        setBackground(new Background(new BackgroundFill(Color.GREEN, null, null)));
        setPadding(new Insets(0, JRSUI.BUTTONSPACING, 0, JRSUI.BUTTONSPACING));

        HBox leftButtons = new HBox();
        leftButtons.setSpacing(JRSUI.BUTTONSPACING);
        leftButtons.setAlignment(Pos.CENTER);

        Button loadButton = new Button();
        loadButton.setPrefSize(JRSUI.HEADERBUTTONSIZE, JRSUI.HEADERBUTTONSIZE);
        loadButton.setGraphic(new ImageView(new Image(getClass().getClassLoader().getResource("icons/folder/folder_32.png").toString())));
        loadButton.setOnAction(value -> openFile());

        Button saveButton = new Button();
        saveButton.setPrefSize(JRSUI.HEADERBUTTONSIZE, JRSUI.HEADERBUTTONSIZE);
        saveButton.setGraphic(new ImageView(new Image(getClass().getClassLoader().getResource("icons/diskette/diskette_32.png").toString())));
        saveButton.setOnAction(value -> saveFile());

        MenuButton addObjectButton = new MenuButton();
        addObjectButton.setPrefSize(JRSUI.HEADERBUTTONSIZE, JRSUI.HEADERBUTTONSIZE);
        addObjectButton.setGraphic(new ImageView(new Image(getClass().getClassLoader().getResource("icons/3d/3d_32.png").toString())));

        MenuItem addPlane = new MenuItem("Plane");
        addPlane.setOnAction(value -> addPlane());

        MenuItem addSphere = new MenuItem("Sphere");
        addSphere.setOnAction(value -> addSphere());
        
        MenuItem addCube = new MenuItem("Cube");
        addCube.setOnAction(value -> addCube());

        MenuItem addCylinder = new MenuItem("Cylinder");
        addCylinder.setOnAction(value -> addCylinder());

        MenuItem addTeapot = new MenuItem("Teapot");
        addTeapot.setOnAction(value -> addTeapot());

        MenuItem addOBJ = new MenuItem("Import");
        addOBJ.setOnAction(value -> addOBJ());

        addObjectButton.getItems().addAll(addPlane, addSphere, addCube, addCylinder, addTeapot, addOBJ);

        Platform.runLater(() ->
        {
            addObjectButton.lookup( ".arrow" ).setStyle( "-fx-background-insets: 0; -fx-padding: 0; -fx-shape: null;" );
            addObjectButton.lookup( ".arrow-button" ).setStyle( "-fx-padding: 0" );
        });

        ToggleButton cameraToggle = new ToggleButton();
        cameraToggle.setPrefSize(JRSUI.HEADERBUTTONSIZE, JRSUI.HEADERBUTTONSIZE);
        cameraToggle.setGraphic(new ImageView(new Image(getClass().getClassLoader().getResource("icons/view/view_32.png").toString())));
        cameraToggle.setOnAction(value -> toggleCamera(cameraToggle));

        leftButtons.getChildren().addAll(loadButton, saveButton, addObjectButton, cameraToggle);

        Region filler = new Region();

        HBox rightButtons = new HBox();
        rightButtons.setSpacing(JRSUI.BUTTONSPACING);
        rightButtons.setAlignment(Pos.CENTER);
        Button openDocumentationButton = new Button();
        openDocumentationButton.setPrefSize(JRSUI.HEADERBUTTONSIZE, JRSUI.HEADERBUTTONSIZE);
        openDocumentationButton.setGraphic(new ImageView(new Image(getClass().getClassLoader().getResource("icons/questions/questions_32.png").toString())));
        openDocumentationButton.setOnAction(value -> openDocumentation());

        Button reportBugButton = new Button();
        reportBugButton.setPrefSize(JRSUI.HEADERBUTTONSIZE, JRSUI.HEADERBUTTONSIZE);
        reportBugButton.setGraphic(new ImageView(new Image(getClass().getClassLoader().getResource("icons/worldwide/worldwide_32.png").toString())));
        reportBugButton.setOnAction(value -> reportBug());
        
        rightButtons.getChildren().addAll(openDocumentationButton, reportBugButton);

        getChildren().addAll(leftButtons, filler, rightButtons);

        setAlignment(Pos.CENTER);
        setHgrow(leftButtons, Priority.NEVER);
        setHgrow(filler, Priority.ALWAYS);
        setHgrow(rightButtons, Priority.NEVER);
    }

    private void openFile() {
        File defaultDir = Utils.getFileFromURL(this.getClass().getClassLoader().getResource("saves/"));
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Java Raytracer Scene (*.jrs)", "*.jrs"));
        fileChooser.setInitialDirectory(defaultDir);
        fileChooser.setTitle("Load Scene");
        File file = fileChooser.showOpenDialog(root.window());

        if (file != null) {
            try {
                JRSFile jrs = JRSFile.load(file);
                FX3DRenderer newRenderer = FX3DRenderer.fromJRS(jrs.world());
                renderer.setObjects(newRenderer.objects());
                renderer.setRenderCamera(newRenderer.renderCamera());
            } catch (Exception e) {
                System.err.println(e);
                System.exit(1);
            }
        }
    }

    private void saveFile() {
        File defaultDir = Utils.getFileFromURL(this.getClass().getClassLoader().getResource("saves/"));
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Java Raytracer Scene (*.jrs)", "*.jrs"));
        fileChooser.setInitialDirectory(defaultDir);
        fileChooser.setTitle("Save Scene");
        File file = fileChooser.showSaveDialog(root.window());

        JRSFile jrs = renderer.toJRSFile();

        if (file != null) {
            try {
                jrs.save(file);
            } catch (Exception e) {
                System.err.println(e);
                System.exit(1);
            }
        }
    }

    private void addPlane() {
        Box model = new Box(2, 0, 2);
        FX3DObject object = new FX3DObject(model);
        object.setType(JRSObjectType.PLANE);
        object.setName("Plane");

        renderer.addToWorld(object);
    }

    private void addSphere() {
        Sphere model = new Sphere();
        FX3DObject object = new FX3DObject(model);
        object.setType(JRSObjectType.SPHERE);
        object.setName("Sphere");

        renderer.addToWorld(object);
    }

    private void addCube() {
        Box model = new Box();
        FX3DObject object = new FX3DObject(model);
        object.setType(JRSObjectType.CUBE);
        object.setName("Cube");

        renderer.addToWorld(object);
    }

    private void addCylinder() {
        Cylinder model = new Cylinder();
        FX3DObject object = new FX3DObject(model);
        object.setType(JRSObjectType.CYLINDER);
        object.setName("Cylinder");

        renderer.addToWorld(object);
    }

    private void addTeapot() {
        System.out.println("TEAPOT");
    }

    private void addOBJ() {
        System.out.println("OBJ");
    }

    private void toggleCamera(ToggleButton button) {
        if (button.isSelected()) {
            button.setGraphic(new ImageView(new Image(getClass().getClassLoader().getResource("icons/camera/camera_32.png").toString())));
        }

        else {
            button.setGraphic(new ImageView(new Image(getClass().getClassLoader().getResource("icons/view/view_32.png").toString())));
        }
        renderer.toggleRenderCamera();
    }

    private void openDocumentation() {
        try {
            Desktop.getDesktop().browse(new URI("https://github.com/Christopher-Hosken/java-raytracer-app/wiki"));
        } catch (Exception e) {
            System.err.println(e);
            System.exit(1);
        }
    }

    private void reportBug() {
        try {
            Desktop.getDesktop().browse(new URI("https://github.com/Christopher-Hosken/java-raytracer-app/issues"));
        } catch (Exception e) {
            System.err.println(e);
            System.exit(1);
        }
    }
}
