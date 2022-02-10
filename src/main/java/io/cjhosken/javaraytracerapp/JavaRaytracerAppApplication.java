package io.cjhosken.javaraytracerapp;

import java.io.IOException;

import io.cjhosken.javaraytracerapp.ui.JRSRoot;
import io.cjhosken.javaraytracerapp.ui.JRSUI;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JavaRaytracerAppApplication extends Application {
    public static void main(String args[]) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        JRSRoot root = new JRSRoot();

        Scene scene = new Scene(root, root.getWidth(), root.getHeight());


        stage.setMinWidth(JRSUI.MINWIDTH);
        stage.setMinHeight(JRSUI.MINHEIGHT);

        stage.setTitle("Java Raytracer");
        stage.setScene(scene);
        stage.show();

        stage.widthProperty().addListener((obs, oldVal, newVal) -> {
            System.out.println(root.getWidth());
        });
    }
}