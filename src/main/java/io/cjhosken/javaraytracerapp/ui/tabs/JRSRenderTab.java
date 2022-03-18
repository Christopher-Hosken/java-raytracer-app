package io.cjhosken.javaraytracerapp.ui.tabs;

import io.cjhosken.javaraytracerapp.core.Utils;
import io.cjhosken.javaraytracerapp.jrs.JRSFile;
import io.cjhosken.javaraytracerapp.rendering.fx3d.FX3DRenderer;
import io.cjhosken.javaraytracerapp.rendering.paver.PaverRenderer;
import io.cjhosken.javaraytracerapp.ui.JRSRoot;
import io.cjhosken.javaraytracerapp.ui.extra.JRSBooleanInput;
import io.cjhosken.javaraytracerapp.ui.extra.JRSDoubleInput;
import io.cjhosken.javaraytracerapp.ui.extra.JRSFileBrowser;
import io.cjhosken.javaraytracerapp.ui.extra.JRSIntegerInput;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class JRSRenderTab extends Tab {
    JRSRoot root;
    JRSFileBrowser browser;
    FX3DRenderer renderer;
    
    public JRSRenderTab(JRSRoot root, FX3DRenderer renderer) {
        super("Render");
        VBox tabRoot = new VBox();
        this.root = root;
        this.renderer = renderer;

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
        JRSDoubleInput fov = new JRSDoubleInput("FOV (deg)", 30);
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
        browser = new JRSFileBrowser(root, "C:/");
        try {
            browser = new JRSFileBrowser(root, Utils.getFileFromURL(this.getClass().getClassLoader().getResource("renders/")).toString());
        } catch (Exception e) {
            System.err.println(e);
            System.exit(1);
        }
        
        Button renderButton = new Button("Render");
        renderButton.setOnAction(value -> render());

        outputSettings.getChildren().addAll(browser, renderButton);
        outputPane.setContent(outputSettings);

        tabRoot.getChildren().addAll(resolutionPane, cameraPane, samplePane, outputPane);

        setContent(tabRoot);
    }

    private void render() {
        try {
            System.out.println(JRSFile.fromFX3D(renderer).toJSON().toString());
            BufferedImage image = PaverRenderer.renderJRS(JRSFile.fromFX3D(renderer));
            File outFile = new File("render.png");
            ImageIO.write(image, "png", outFile);
        } catch (Exception e) {
            System.err.println(e);
            System.exit(1);
        }
    }
}
