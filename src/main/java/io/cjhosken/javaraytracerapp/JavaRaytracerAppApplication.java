package io.cjhosken.javaraytracerapp;

import java.io.IOException;

import javax.swing.SwingUtilities;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.util.Animator;

import io.cjhosken.javaraytracerapp.helpers.controllers.MainController;
import io.cjhosken.javaraytracerapp.helpers.handlers.MainEventHandler;
import io.cjhosken.javaraytracerapp.rendering.opengl.GLRenderer;
import javafx.application.Application;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class JavaRaytracerAppApplication extends Application {
    private MainController controller;
    private MainEventHandler eventHandler;

	@Override
    public void start(Stage stage) throws IOException {
        // Window Size
        int width = 1280;
        int height = 720;

        // JOGL Setup

        final GLCapabilities capabilities = new GLCapabilities(GLProfile.getDefault());
        capabilities.setDoubleBuffered(true);
        capabilities.setHardwareAccelerated(true);

        GLJPanel canvas = new GLJPanel(capabilities);
        GLRenderer renderer = new GLRenderer();
        canvas.addGLEventListener(renderer);
        canvas.addMouseMotionListener(renderer);
        canvas.addMouseWheelListener(renderer);
        canvas.addMouseListener(renderer);
        canvas.addKeyListener(renderer);

        Animator animator = new Animator(canvas);
        animator.start();

        // Loading FXML File

        FXMLLoader loader = new FXMLLoader(this.getClass().getClassLoader().getResource("fxml/main.fxml"));
        eventHandler = new MainEventHandler(stage);
        controller = new MainController(renderer, animator);

        loader.setController(controller);
        Parent root = loader.load();
        
        Rectangle rect = new Rectangle(1280, 720);
        rect.setArcHeight(70.0);
        rect.setArcWidth(70.0);
        root.setClip(rect);

        Scene scene = new Scene(root, width, height);

        // Event Handlers and GL Implementation

        StackPane viewportContainer = (StackPane) scene.lookup("#viewport");

        HBox header = (HBox) scene.lookup("#header");
        ButtonBar headerBar = (ButtonBar) scene.lookup("#headerBar");

        header.setOnMousePressed(eventHandler.mousePressEvent);
        headerBar.setOnMousePressed(eventHandler.mousePressEvent);

        header.setOnMouseDragged(eventHandler.headerDragEvent);
        headerBar.setOnMouseDragged(eventHandler.headerDragEvent);

        SwingNode swingNode = new SwingNode();

        viewportContainer.getChildren().add(swingNode);

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                swingNode.setContent(canvas);
            }
        });

        // Stage Setup

        stage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
        stage.getIcons().add(new Image("css/icons/logo/logo_64.png"));
        stage.setTitle("Java Renderer");
        stage.setScene(scene);
        stage.toFront();
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
