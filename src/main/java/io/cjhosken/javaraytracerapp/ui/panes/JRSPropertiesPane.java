package io.cjhosken.javaraytracerapp.ui.panes;

import io.cjhosken.javaraytracerapp.rendering.fx3d.FX3DRenderer;
import io.cjhosken.javaraytracerapp.ui.JRSRoot;
import io.cjhosken.javaraytracerapp.ui.JRSUI;
import io.cjhosken.javaraytracerapp.ui.tabs.JRSObjectTab;
import io.cjhosken.javaraytracerapp.ui.tabs.JRSRenderTab;
import io.cjhosken.javaraytracerapp.ui.tabs.JRSSceneTab;
import javafx.geometry.Side;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;

public class JRSPropertiesPane extends TabPane {
    public JRSPropertiesPane(JRSRoot root, FX3DRenderer renderer) {
        super();
        setPrefWidth(JRSUI.PREFPROPERTIESWIDTH);
        setMinWidth(JRSUI.MINPROPERTIESWIDTH);
        prefHeightProperty().bind(root.heightProperty().subtract(JRSUI.NAVHEIGHT));
        setBackground(new Background(new BackgroundFill(Color.BLUE, null, null)));
        setSide(Side.LEFT);
        setTabMinHeight(JRSUI.TABHEIGHT);
        setTabMaxHeight(JRSUI.TABHEIGHT);
        setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

        JRSSceneTab sceneTab = new JRSSceneTab(renderer);
        JRSRenderTab renderTab = new JRSRenderTab(root, renderer);
        JRSObjectTab objectTab = new JRSObjectTab();

        getTabs().addAll(sceneTab, renderTab, objectTab);
    }
}
