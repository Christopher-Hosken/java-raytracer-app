package io.cjhosken.javaraytracerapp.ui.panes;

import io.cjhosken.javaraytracerapp.ui.JRSRoot;
import io.cjhosken.javaraytracerapp.ui.JRSUI;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class JRSViewportPane extends StackPane {
    public JRSViewportPane(JRSRoot root) {
        super();
        prefWidthProperty().bind(root.widthProperty().subtract(JRSUI.PREFPROPERTIESWIDTH));
        prefHeightProperty().bind(root.heightProperty().subtract(JRSUI.NAVHEIGHT));
        setBackground(new Background(new BackgroundFill(Color.RED, null, null)));
    }
}
