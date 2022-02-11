package io.cjhosken.javaraytracerapp.ui;
import io.cjhosken.javaraytracerapp.rendering.fx3d.FX3DRenderer;
import io.cjhosken.javaraytracerapp.ui.panes.JRSHeaderPane;
import io.cjhosken.javaraytracerapp.ui.panes.JRSPropertiesPane;
import javafx.scene.SubScene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.VBox;
import javafx.stage.Window;

public class JRSRoot extends VBox {
    public JRSRoot() {
        super();
        setWidth(JRSUI.PREFAPPWIDTH);
        setHeight(JRSUI.PREFAPPHEIGHT);

        FX3DRenderer renderer = new FX3DRenderer();

        JRSHeaderPane header = new JRSHeaderPane(this, renderer);
        getChildren().add(header);

        SplitPane main = new SplitPane();

        SubScene viewport = renderer.scene();

        JRSPropertiesPane properties = new JRSPropertiesPane(this, renderer);

        /* TODO: VIEWPORT RESIZE NOT WORKING. (because it's a subscene) */
        
        main.getItems().addAll(viewport, properties);
        main.setDividerPositions(0.75);

        main.getDividers().get(0).positionProperty().addListener(listener -> viewport.resize(getWidth() * main.getDividerPositions()[0], getHeight() - JRSUI.NAVHEIGHT));

        getChildren().add(main);
    }

    public Window window() {
        return getScene().getWindow();
    }
}
