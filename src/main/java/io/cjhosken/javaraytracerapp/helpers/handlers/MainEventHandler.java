package io.cjhosken.javaraytracerapp.helpers.handlers;

import io.cjhosken.javaraytracerapp.rendering.core.Delta;
import io.cjhosken.javaraytracerapp.rendering.fx3d.FX3DRenderer;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MainEventHandler {
    private FX3DRenderer renderer;
    private final Stage stage;
    private boolean objectMoving;

    public MainEventHandler(Stage stage, FX3DRenderer renderer) {
        this.stage = stage;
        this.renderer = renderer;
    }

    public EventHandler<KeyEvent> keyPressEvent = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            System.out.println(event.getCode());
            if (objectMoving) {
                if (event.getCode() == KeyCode.ENTER) {
                    objectMoving = false;
                }
            } else {
                if (event.getCode() == KeyCode.H) {
                    renderer.camera().setTranslateX(0);
                    renderer.camera().setTranslateY(0);
                    renderer.camera().setTranslateZ(-25);
                    renderer.angleX().set(0);
                    renderer.angleY().set(0);
                } else if (event.getCode() == KeyCode.DELETE
                        || (event.isControlDown() && event.getCode() == KeyCode.X)) {
                    renderer.deleteSelected();
                }
            }
        }
    };
}