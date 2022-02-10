package io.cjhosken.javaraytracerapp.ui.extra;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class JRSFileBrowser extends HBox {
    public JRSFileBrowser(String path) {
        super();
        setAlignment(Pos.CENTER);

        TextField input = new TextField(path);

        Button browser = new Button();

        getChildren().addAll(input, browser);
    }
}
