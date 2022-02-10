package io.cjhosken.javaraytracerapp.ui.tabs;

import io.cjhosken.javaraytracerapp.ui.extra.JRSBooleanInput;
import io.cjhosken.javaraytracerapp.ui.extra.JRSColorInput;
import io.cjhosken.javaraytracerapp.ui.extra.JRSDoubleInput;
import io.cjhosken.javaraytracerapp.ui.extra.JRSLabel;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.control.TitledPane;

public class JRSSceneTab extends Tab {
    public JRSSceneTab() {
        super("Scene");
        VBox tabRoot = new VBox();

        TitledPane viewportPane = new TitledPane();
        viewportPane.setText("Viewport");
        VBox viewportSettings = new VBox();
        JRSDoubleInput fov = new JRSDoubleInput("FOV (deg)", 60);
        JRSDoubleInput zNear = new JRSDoubleInput("Clip Near", 0.01);
        JRSDoubleInput zFar = new JRSDoubleInput("Clip Far", 1000);
        viewportSettings.getChildren().addAll(fov, zNear, zFar);
        viewportPane.setContent(viewportSettings);

        TitledPane worldPane = new TitledPane();
        worldPane.setText("World");
        VBox worldSettings = new VBox();
        JRSBooleanInput useCustomWorld = new JRSBooleanInput("Custom World?", false);
        JRSBooleanInput useGradient = new JRSBooleanInput("Use Gradient", false);
        JRSColorInput colorA = new JRSColorInput("Color", Color.WHITE);
        JRSColorInput colorB = new JRSColorInput("Color", Color.BLACK);
        JRSBooleanInput useForRendering = new JRSBooleanInput("Use For Rendering?", false);
        worldSettings.getChildren().addAll(useCustomWorld, useGradient, colorA, colorB, useForRendering);
        worldPane.setContent(worldSettings);

        TitledPane extraPane = new TitledPane();
        extraPane.setText("Extra");
        VBox extraPaneSettings = new VBox();
        JRSLabel copyright = new JRSLabel("Copyright:", "Christopher Hosken 2021");
        extraPaneSettings.getChildren().addAll(copyright);
        extraPane.setContent(extraPaneSettings);

        tabRoot.getChildren().addAll(viewportPane, worldPane, extraPane);
        setContent(tabRoot);
    }
}
