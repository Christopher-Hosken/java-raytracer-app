package io.cjhosken.javaraytracerapp.ui.tabs;

import io.cjhosken.javaraytracerapp.ui.extra.JRSBooleanInput;
import io.cjhosken.javaraytracerapp.ui.extra.JRSDoubleInput;
import io.cjhosken.javaraytracerapp.ui.extra.JRSFileBrowser;
import io.cjhosken.javaraytracerapp.ui.extra.JRSIntegerInput;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;

public class JRSRenderTab extends Tab {
    public JRSRenderTab() {
        super("Render");
        VBox tabRoot = new VBox();

        TitledPane resolutionPane = new TitledPane();
        resolutionPane.setText("Resolution");
        VBox resolutionSettings = new VBox();
        JRSIntegerInput resolutionX = new JRSIntegerInput("X Resolution", 680);
        JRSIntegerInput resolutionY = new JRSIntegerInput("Y", 420);
        resolutionSettings.getChildren().addAll(resolutionX, resolutionY);

        resolutionPane.setContent(resolutionSettings);

        TitledPane cameraPane = new TitledPane();
        cameraPane.setText("Camera");
        VBox cameraSettings = new VBox();
        JRSDoubleInput fov = new JRSDoubleInput("FOV (deg)", 60);
        JRSDoubleInput aperture = new JRSDoubleInput("Apeture", 2);
        JRSBooleanInput dof = new JRSBooleanInput("Use DOF?", false);
        JRSDoubleInput focusDistance = new JRSDoubleInput("DOF Distance", 1);
        cameraSettings.getChildren().addAll(fov, aperture, dof, focusDistance);

        cameraPane.setContent(cameraSettings);

        TitledPane samplePane = new TitledPane();
        samplePane.setText("Sampling");
        VBox sampleSettings = new VBox();
        JRSIntegerInput samples = new JRSIntegerInput("Samples", 16);
        JRSIntegerInput bounces = new JRSIntegerInput("Bounces", 4);
        sampleSettings.getChildren().addAll(samples, bounces);

        samplePane.setContent(sampleSettings);

        TitledPane outputPane = new TitledPane();
        outputPane.setText("Output");
        VBox outputSettings = new VBox();
        outputSettings.setAlignment(Pos.CENTER);

        JRSFileBrowser browser = new JRSFileBrowser(getClass().getClassLoader().getResource("renders/image.png").toString());

        Button renderButton = new Button("Render");

        outputSettings.getChildren().addAll(browser, renderButton);
        outputPane.setContent(outputSettings);

        tabRoot.getChildren().addAll(resolutionPane, cameraPane, samplePane, outputPane);

        setContent(tabRoot);
    }
}
