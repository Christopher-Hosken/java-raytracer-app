package io.cjhosken.javaraytracerapp.ui.panes;

import io.cjhosken.javaraytracerapp.rendering.fx3d.FX3DRenderer;
import io.cjhosken.javaraytracerapp.ui.JRSRoot;
import io.cjhosken.javaraytracerapp.ui.JRSUI;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class JRSViewportPane extends StackPane {
    JRSRoot root;
    FX3DRenderer renderer;
    public JRSViewportPane(JRSRoot root, FX3DRenderer renderer) {
        super();
        this.root = root;
        this.renderer = renderer;

        
        minWidthProperty().bind(root.widthProperty().subtract(JRSUI.PREFPROPERTIESWIDTH));
        minHeightProperty().bind(root.heightProperty().subtract(JRSUI.NAVHEIGHT));
        setBackground(new Background(new BackgroundFill(Color.RED, null, null)));

        getChildren().add(renderer.scene());
    }

    public void resizeViewport() {
        renderer.scene().setWidth(getWidth());
        renderer.scene().setHeight(getHeight());
    }
}
