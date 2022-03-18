package io.cjhosken.javaraytracerapp.ui.extra;

import io.cjhosken.javaraytracerapp.core.Vector3d;
import io.cjhosken.javaraytracerapp.ui.JRSUI;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class JRSVector3dInput extends HBox {
    private TextField inputX;
    private TextField inputY;
    private TextField inputZ;

    public JRSVector3dInput(String name, Vector3d v) {
        setAlignment(Pos.CENTER_RIGHT);
        setSpacing(JRSUI.LABELINPUTSPACING);
        Label label = new Label(name);

        HBox row = new HBox();

        inputX = new TextField(String.valueOf(v.x));
        inputY = new TextField(String.valueOf(v.y));
        inputZ = new TextField(String.valueOf(v.z));

        row.getChildren().addAll(inputX, inputY, inputZ);

        inputX.textProperty()
                .addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    if (!newValue.matches("[0-9]*\\.[0-9]*")) {
                        inputX.setText(newValue.replaceAll("[0-9]*\\.[0-9]", ""));
                    }
                });

        inputY.textProperty()
                .addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    if (!newValue.matches("[0-9]*\\.[0-9]*")) {
                        inputY.setText(newValue.replaceAll("[0-9]*\\.[0-9]", ""));
                    }
                });

        inputZ.textProperty()
                .addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    if (!newValue.matches("[0-9]*\\.[0-9]*")) {
                        inputZ.setText(newValue.replaceAll("[0-9]*\\.[0-9]", ""));
                    }
                });

        getChildren().addAll(label, row);
    }

    public TextField inputX() {
        return inputX;
    }

    public TextField inputY() {
        return inputY;
    }

    public TextField inputZ() {
        return inputZ;
    }
}
