package io.cjhosken.javaraytracerapp.ui.extra;

import io.cjhosken.javaraytracerapp.ui.JRSUI;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class JRSDoubleInput extends HBox {
    private TextField input;

    public JRSDoubleInput(String name, double i0) {
        setAlignment(Pos.CENTER_RIGHT);
        setSpacing(JRSUI.LABELINPUTSPACING);
        Label label = new Label(name);

        input = new TextField(String.valueOf(i0));

        input.textProperty()
                .addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    if (!newValue.matches("[0-9]*\\.[0-9]*")) {
                        input.setText(newValue.replaceAll("[0-9]*\\.[0-9]", ""));
                    }
                });

        getChildren().addAll(label, input);
    }

    public TextField input() {
        return input;
    }
}
