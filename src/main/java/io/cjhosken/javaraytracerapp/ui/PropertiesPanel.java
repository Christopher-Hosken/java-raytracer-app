package io.cjhosken.javaraytracerapp.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.swing.Action;

import org.json.JSONObject;

import io.cjhosken.javaraytracerapp.helpers.jrs.JRSUtils;
import io.cjhosken.javaraytracerapp.rendering.fx3d.FX3DRenderer;
import io.cjhosken.javaraytracerapp.rendering.fx3d.RendererObject;
import io.cjhosken.javaraytracerapp.rendering.paver.PaverRenderer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.awt.image.BufferedImage;

public class PropertiesPanel {
    private FX3DRenderer renderer;
    private TabPane root;
    private Tab renderingTab;
    private Tab sceneTab;
    private Tab objectTab = new Tab("Object");
    private RendererObject activeObject;

    public PropertiesPanel(TabPane parent) {
        root = parent;
        initObjectTab();
        initSceneTab();
        initRenderingTab();
    }

    public void setRenderer(FX3DRenderer r) {
        renderer = r;
    }

    public void initSceneTab() {
        sceneTab = root.getTabs().get(0);
    }

    public void initRenderingTab() {
        renderingTab = root.getTabs().get(1);
        ;
        TextField render_resolution_x = (TextField) renderingTab.getContent().lookup("#render_resolution_x");
        render_resolution_x.setText("680");

        TextField render_resolution_y = (TextField) renderingTab.getContent().lookup("#render_resolution_y");
        render_resolution_y.setText("420");

        render_resolution_x.textProperty()
                .addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    if (!newValue.matches("\\d*")) {
                        render_resolution_x.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                });

        render_resolution_y.textProperty()
                .addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    if (!newValue.matches("\\d*")) {
                        render_resolution_y.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                });

        TextField samples = (TextField) renderingTab.getContent().lookup("#samples");
        samples.setText("16");

        TextField bounces = (TextField) renderingTab.getContent().lookup("#bounces");
        bounces.setText("4");

        samples.textProperty()
                .addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    if (!newValue.matches("\\d*")) {
                        samples.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                });

        bounces.textProperty()
                .addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    if (!newValue.matches("\\d*")) {
                        bounces.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                });

        TextField output_path = (TextField) renderingTab.getContent().lookup("#output_path");
        output_path.setText(Paths.get("renders").toAbsolutePath().normalize().toString() + "\\" + "render.png");

        Button path_browser = (Button) renderingTab.getContent().lookup("#output_path_button");

        path_browser.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                Stage stage = (Stage) root.getScene().getWindow();
                File defaultDir = new File(Paths.get("renders").toAbsolutePath().normalize().toString());
                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG (*.png)", "*.png"));
                fileChooser.setInitialDirectory(defaultDir);
                fileChooser.setTitle("Render Destination");

                File file = fileChooser.showSaveDialog(stage);

                if (file != null) {
                    try {
                        PrintWriter writer;
                    writer = new PrintWriter(file);
                    writer.println("f");
                    writer.close();
                    
                    output_path.setText(file.getAbsolutePath());
                        
                    }

                    catch (Exception err) {
                        System.err.println(err);
                        System.exit(1);
                    }
                    
                }

            }

        });

        Button render_button = (Button) renderingTab.getContent().lookup("#render_button");
        render_button.setText("Render");

        render_button.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                JSONObject jrs = JRSUtils.write(renderer, render_resolution_x.getText(), render_resolution_y.getText(), samples.getText(), bounces.getText());

                try {
                    BufferedImage render = PaverRenderer.renderJRS(jrs);
                    ImageIO.write(render, "png", new File(output_path.getText()));
                    ImageIO.read(new File(output_path.getText()));
                } catch (Exception err) {
                    System.err.println(err);
                    System.exit(1);
                }

                System.out.println("RENDERED");
            }
        });

    }

    public void initObjectTab() {
        objectTab = root.getTabs().get(2);
        root.getTabs().remove(objectTab);
    }

    public TabPane root() {
        return root;
    }

    public void setObjectTab(RendererObject ob) {
        activeObject = ob;
        root.getTabs().add(objectTab);
    }

    public void removeObjectTab() {
        root.getTabs().remove(objectTab);
    }
}
