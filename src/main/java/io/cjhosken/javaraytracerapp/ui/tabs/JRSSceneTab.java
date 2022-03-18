package io.cjhosken.javaraytracerapp.ui.tabs;

import io.cjhosken.javaraytracerapp.rendering.fx3d.FX3DRenderer;
import io.cjhosken.javaraytracerapp.ui.extra.JRSBooleanInput;
import io.cjhosken.javaraytracerapp.ui.extra.JRSColorInput;
import io.cjhosken.javaraytracerapp.ui.extra.JRSDoubleInput;
import io.cjhosken.javaraytracerapp.ui.extra.JRSLabel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.control.TitledPane;

public class JRSSceneTab extends Tab {

    public JRSSceneTab(FX3DRenderer renderer) {
        super("Scene");
        VBox tabRoot = new VBox();

        TitledPane viewportPane = new TitledPane();
        viewportPane.setText("Viewport");
        VBox viewportSettings = new VBox();
        JRSDoubleInput fov = new JRSDoubleInput("FOV (deg)", 30);

        fov.input().focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldVal, Boolean newVal) {
                if (!newVal) {
                    renderer.viewportCamera().setFieldOfView(Double.valueOf(fov.input().getText()));
                }
            }
        });

        JRSDoubleInput zNear = new JRSDoubleInput("Clip Near", 0.01);

        zNear.input().focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldVal, Boolean newVal) {
                if (!newVal) {
                    renderer.viewportCamera().setNearClip(Double.valueOf(zNear.input().getText()));
                }
            }
        });


        JRSDoubleInput zFar = new JRSDoubleInput("Clip Far", 1000);

        zFar.input().focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldVal, Boolean newVal) {
                if (!newVal) {
                    renderer.viewportCamera().setFarClip(Double.valueOf(zFar.input().getText()));
                }
            }
        });

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
