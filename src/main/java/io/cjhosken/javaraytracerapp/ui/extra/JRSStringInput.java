package io.cjhosken.javaraytracerapp.ui.extra;

import io.cjhosken.javaraytracerapp.ui.JRSUI;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class JRSStringInput extends HBox {
    private TextField input;

    public JRSStringInput(String name, String s0) {
        setAlignment(Pos.CENTER_RIGHT);
        setSpacing(JRSUI.LABELINPUTSPACING);
        Label label = new Label(name);

        input = new TextField(s0);

        getChildren().addAll(label, input);
    }

    public TextField input() {
        return input;
    }
}
