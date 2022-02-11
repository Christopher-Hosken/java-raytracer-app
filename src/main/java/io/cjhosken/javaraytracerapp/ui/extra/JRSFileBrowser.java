package io.cjhosken.javaraytracerapp.ui.extra;

import java.io.File;

import io.cjhosken.javaraytracerapp.core.Utils;
import io.cjhosken.javaraytracerapp.ui.JRSRoot;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

public class JRSFileBrowser extends HBox {
    TextField input;

    public JRSFileBrowser(JRSRoot root, String path) {
        super();
        setAlignment(Pos.CENTER);

        input = new TextField(path);

        Button browser = new Button();

        browser.setOnAction(value -> {
            File defaultDir = Utils.getFileFromURL(this.getClass().getClassLoader().getResource("renders/"));
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG (*.png)", "*.png"));
        fileChooser.setInitialDirectory(defaultDir);
        fileChooser.setTitle("Save Render");
        File file = fileChooser.showSaveDialog(root.window());


        if (file != null) {
            input.setText(file.toString());
        }});

        getChildren().addAll(input, browser);
    }

    public File getFile() {
        return new File(input.toString());
    }
}
