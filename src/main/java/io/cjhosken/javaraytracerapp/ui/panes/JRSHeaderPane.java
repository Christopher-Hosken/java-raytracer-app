package io.cjhosken.javaraytracerapp.ui.panes;

import io.cjhosken.javaraytracerapp.ui.JRSRoot;
import io.cjhosken.javaraytracerapp.ui.JRSUI;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

public class JRSHeaderPane extends HBox {
    public JRSHeaderPane(JRSRoot root) {
        super();
        prefWidthProperty().bind(root.widthProperty());
        setPrefHeight(JRSUI.NAVHEIGHT);
        setBackground(new Background(new BackgroundFill(Color.GREEN, null, null)));
        setPadding(new Insets(0, JRSUI.BUTTONSPACING, 0, JRSUI.BUTTONSPACING));

        HBox leftButtons = new HBox();
        leftButtons.setSpacing(JRSUI.BUTTONSPACING);
        leftButtons.setAlignment(Pos.CENTER);

        Button loadButton = new Button();
        loadButton.setPrefSize(JRSUI.HEADERBUTTONSIZE, JRSUI.HEADERBUTTONSIZE);
        loadButton.setGraphic(new ImageView(new Image(getClass().getClassLoader().getResource("icons/folder/folder_32.png").toString())));

        Button saveButton = new Button();
        saveButton.setPrefSize(JRSUI.HEADERBUTTONSIZE, JRSUI.HEADERBUTTONSIZE);
        saveButton.setGraphic(new ImageView(new Image(getClass().getClassLoader().getResource("icons/diskette/diskette_32.png").toString())));

        MenuButton addObjectButton = new MenuButton();
        addObjectButton.setPrefSize(JRSUI.HEADERBUTTONSIZE, JRSUI.HEADERBUTTONSIZE);
        addObjectButton.setGraphic(new ImageView(new Image(getClass().getClassLoader().getResource("icons/3d/3d_32.png").toString())));

        MenuItem addPlane = new MenuItem("Plane");
        MenuItem addSphere = new MenuItem("Sphere");
        MenuItem addCube = new MenuItem("Cube");
        MenuItem addCylinder = new MenuItem("Cylinder");
        MenuItem addTeapot = new MenuItem("Teapot");
        MenuItem addOBJ = new MenuItem("Import");

        addObjectButton.getItems().addAll(addPlane, addSphere, addCube, addCylinder, addTeapot, addOBJ);

        Platform.runLater(() ->
        {
            addObjectButton.lookup( ".arrow" ).setStyle( "-fx-background-insets: 0; -fx-padding: 0; -fx-shape: null;" );
            addObjectButton.lookup( ".arrow-button" ).setStyle( "-fx-padding: 0" );
        });

        Button cameraToggle = new Button();
        cameraToggle.setPrefSize(JRSUI.HEADERBUTTONSIZE, JRSUI.HEADERBUTTONSIZE);
        cameraToggle.setGraphic(new ImageView(new Image(getClass().getClassLoader().getResource("icons/view/view_32.png").toString())));

        leftButtons.getChildren().addAll(loadButton, saveButton, addObjectButton, cameraToggle);

        Region filler = new Region();

        HBox rightButtons = new HBox();
        rightButtons.setSpacing(JRSUI.BUTTONSPACING);
        rightButtons.setAlignment(Pos.CENTER);
        Button openDocumentationButton = new Button();
        openDocumentationButton.setPrefSize(JRSUI.HEADERBUTTONSIZE, JRSUI.HEADERBUTTONSIZE);
        openDocumentationButton.setGraphic(new ImageView(new Image(getClass().getClassLoader().getResource("icons/questions/questions_32.png").toString())));

        Button reportBugButton = new Button();
        reportBugButton.setPrefSize(JRSUI.HEADERBUTTONSIZE, JRSUI.HEADERBUTTONSIZE);
        reportBugButton.setGraphic(new ImageView(new Image(getClass().getClassLoader().getResource("icons/worldwide/worldwide_32.png").toString())));
        rightButtons.getChildren().addAll(openDocumentationButton, reportBugButton);

        getChildren().addAll(leftButtons, filler, rightButtons);

        setAlignment(Pos.CENTER);
        setHgrow(leftButtons, Priority.NEVER);
        setHgrow(filler, Priority.ALWAYS);
        setHgrow(rightButtons, Priority.NEVER);


    }
}
