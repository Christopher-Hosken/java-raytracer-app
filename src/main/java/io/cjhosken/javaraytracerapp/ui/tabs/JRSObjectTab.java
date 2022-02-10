package io.cjhosken.javaraytracerapp.ui.tabs;

import io.cjhosken.javaraytracerapp.core.Vector3d;
import io.cjhosken.javaraytracerapp.ui.extra.JRSColorInput;
import io.cjhosken.javaraytracerapp.ui.extra.JRSDoubleInput;
import io.cjhosken.javaraytracerapp.ui.extra.JRSLabel;
import io.cjhosken.javaraytracerapp.ui.extra.JRSStringInput;
import io.cjhosken.javaraytracerapp.ui.extra.JRSVector3dInput;
import javafx.scene.control.Tab;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class JRSObjectTab extends Tab {
    public JRSObjectTab() {
        super("Object");
        VBox tabRoot = new VBox();

        TitledPane infoPane = new TitledPane();
        infoPane.setText("Info");
        VBox infoSettings = new VBox();
        JRSStringInput name = new JRSStringInput("Name", "cool object");
        JRSLabel type = new JRSLabel("Type", "Object");
        infoSettings.getChildren().addAll(name, type);
        infoPane.setContent(infoSettings);

        TitledPane transformsPane = new TitledPane();
        transformsPane.setText("Transforms");
        VBox transformsPaneSettings = new VBox();
        JRSVector3dInput translate = new JRSVector3dInput("Location", new Vector3d());
        JRSVector3dInput rotate = new JRSVector3dInput("Rotation", new Vector3d());
        JRSVector3dInput scale = new JRSVector3dInput("Scale", new Vector3d(1, 1, 1));
        transformsPaneSettings.getChildren().addAll(translate, rotate, scale);
        transformsPane.setContent(transformsPaneSettings);

        TitledPane shaderPane = new TitledPane();
        shaderPane.setText("Shader");
        VBox shaderPaneSettings = new VBox();
        JRSColorInput color = new JRSColorInput("Color", Color.WHITE);
        JRSDoubleInput roughness = new JRSDoubleInput("Roughness", 0.5);
        shaderPaneSettings.getChildren().addAll(color, roughness);

        shaderPane.setContent(shaderPaneSettings);

        tabRoot.getChildren().addAll(infoPane, transformsPane, shaderPane);
        
        setContent(tabRoot);
    }
}
