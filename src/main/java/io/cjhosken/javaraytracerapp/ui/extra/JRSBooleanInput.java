package io.cjhosken.javaraytracerapp.ui.extra;

import io.cjhosken.javaraytracerapp.ui.JRSUI;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class JRSBooleanInput extends HBox {
    public JRSBooleanInput(String name, Boolean b) {
        setAlignment(Pos.CENTER_RIGHT);
        setSpacing(JRSUI.LABELINPUTSPACING);
        Label label = new Label(name);

        CheckBox input = new CheckBox();
        input.setSelected(b);

        getChildren().addAll(label, input);
    }
}
