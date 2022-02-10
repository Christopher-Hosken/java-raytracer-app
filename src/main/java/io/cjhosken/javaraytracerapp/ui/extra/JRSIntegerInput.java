package io.cjhosken.javaraytracerapp.ui.extra;

import io.cjhosken.javaraytracerapp.ui.JRSUI;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class JRSIntegerInput extends HBox {
    public JRSIntegerInput(String name, int i0) {
        setAlignment(Pos.CENTER_RIGHT);
        setSpacing(JRSUI.LABELINPUTSPACING);
        Label label = new Label(name);

        TextField input = new TextField(String.valueOf(i0));

        input.textProperty()
                .addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    if (!newValue.matches("\\d*")) {
                        input.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                });

        getChildren().addAll(label, input);
    }
}
