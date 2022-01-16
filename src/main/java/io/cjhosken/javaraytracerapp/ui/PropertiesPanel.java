package io.cjhosken.javaraytracerapp.ui;

import io.cjhosken.javaraytracerapp.rendering.fx3d.RendererObject;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class PropertiesPanel {
    private TabPane root;
    private Tab renderingTab;
    private Tab sceneTab;
    private Tab objectTab = new Tab("Object");
    private RendererObject activeObject;

    public PropertiesPanel(TabPane parent) {
        root = parent;
        initObjectTab();
        initSceneTab();
        initRenderingTab();
    }

    public void initSceneTab() {
        sceneTab = root.getTabs().get(0);
    }

    public void initRenderingTab() {
        renderingTab = root.getTabs().get(1);
    }

    public void initObjectTab() {
        objectTab = root.getTabs().get(2);
        root.getTabs().remove(objectTab);
    }

    public TabPane root() {
        return root;
    }

    public void setObjectTab(RendererObject ob) {
        activeObject = ob;
        root.getTabs().add(objectTab);
    }

    public void removeObjectTab() {
        root.getTabs().remove(objectTab);
    }
}
