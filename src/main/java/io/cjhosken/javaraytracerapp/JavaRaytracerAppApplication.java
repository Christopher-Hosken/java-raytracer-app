package io.cjhosken.javaraytracerapp;

import java.io.IOException;

import io.cjhosken.javaraytracerapp.helpers.controllers.MainController;
import io.cjhosken.javaraytracerapp.helpers.controllers.SceneController;
import io.cjhosken.javaraytracerapp.helpers.handlers.MainEventHandler;
import io.cjhosken.javaraytracerapp.rendering.fx3d.FX3DRenderer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class JavaRaytracerAppApplication extends Application {
    private MainController controller;
    private SceneController sceneController;
    private MainEventHandler events;
    private FX3DRenderer renderer;

	@Override
    public void start(Stage stage) throws IOException {
        int width = 1280;
        int height = 720;

        FXMLLoader loader = new FXMLLoader(this.getClass().getClassLoader().getResource("fxml/main.fxml"));
        FXMLLoader sceneLoader = new FXMLLoader(this.getClass().getClassLoader().getResource("fxml/sceneInfo.fxml"));

        renderer = new FX3DRenderer();
        events = new MainEventHandler(stage, renderer);
        controller = new MainController(renderer);
        sceneController = new SceneController(renderer);

        loader.setController(controller);
        Parent root = loader.load();

        sceneLoader.setController(sceneController);
        TabPane sceneRoot = sceneLoader.load();
        renderer.setPropertiesPanel(sceneRoot);

        Scene scene = new Scene(root, width, height);
        scene.setOnKeyPressed(events.keyPressEvent);

        StackPane viewportContainer = (StackPane) scene.lookup("#viewport");
        StackPane setttingsContainer = (StackPane) scene.lookup("#settings");

        Platform.runLater(new Runnable() {
            public void run() {
                viewportContainer.getChildren().add(renderer.scene());
                setttingsContainer.getChildren().add(renderer.propertiesPanel().root());
                
            }
        });

        stage.getIcons().add(new Image("css/icons/logo/logo_64.png"));
        stage.setTitle("Java Renderer");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}