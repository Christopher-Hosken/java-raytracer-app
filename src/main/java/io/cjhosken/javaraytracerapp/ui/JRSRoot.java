package io.cjhosken.javaraytracerapp.ui;
import io.cjhosken.javaraytracerapp.ui.panes.JRSHeaderPane;
import io.cjhosken.javaraytracerapp.ui.panes.JRSPropertiesPane;
import io.cjhosken.javaraytracerapp.ui.panes.JRSViewportPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.VBox;

public class JRSRoot extends VBox {
    public JRSRoot() {
        super();
        setWidth(JRSUI.PREFAPPWIDTH);
        setHeight(JRSUI.PREFAPPHEIGHT);

        JRSHeaderPane header = new JRSHeaderPane(this);
        getChildren().add(header);

        SplitPane main = new SplitPane();

        JRSViewportPane viewport = new JRSViewportPane(this);
        JRSPropertiesPane properties = new JRSPropertiesPane(this);

        main.getItems().addAll(viewport, properties);
        main.setDividerPositions(0.75);

        getChildren().add(main);
    }
}
