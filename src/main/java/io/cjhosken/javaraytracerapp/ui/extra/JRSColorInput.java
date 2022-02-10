package io.cjhosken.javaraytracerapp.ui.extra;

import io.cjhosken.javaraytracerapp.ui.JRSUI;
import javafx.geometry.Pos;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class JRSColorInput extends HBox {
    public JRSColorInput(String name, Color c0) {
        setAlignment(Pos.CENTER_RIGHT);
        setSpacing(JRSUI.LABELINPUTSPACING);
        Label label = new Label(name);

        ColorPicker input = new ColorPicker(c0);

        getChildren().addAll(label, input);
    }
}
