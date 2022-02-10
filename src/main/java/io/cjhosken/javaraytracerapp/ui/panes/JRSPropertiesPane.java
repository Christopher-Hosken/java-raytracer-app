package io.cjhosken.javaraytracerapp.ui.panes;

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
    public JRSPropertiesPane(JRSRoot root) {
        super();
        setPrefWidth(JRSUI.PREFPROPERTIESWIDTH);
        setMinWidth(JRSUI.MINPROPERTIESWIDTH);
        prefHeightProperty().bind(root.heightProperty().subtract(JRSUI.NAVHEIGHT));
        setBackground(new Background(new BackgroundFill(Color.BLUE, null, null)));
        setSide(Side.LEFT);
        setTabMinHeight(JRSUI.TABHEIGHT);
        setTabMaxHeight(JRSUI.TABHEIGHT);
        setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

        JRSSceneTab sceneTab = new JRSSceneTab();
        JRSRenderTab renderTab = new JRSRenderTab();
        JRSObjectTab objectTab = new JRSObjectTab();

        getTabs().addAll(sceneTab, renderTab, objectTab);
    }
}
