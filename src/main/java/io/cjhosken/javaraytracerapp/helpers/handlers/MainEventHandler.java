package io.cjhosken.javaraytracerapp.helpers.handlers;

import io.cjhosken.javaraytracerapp.rendering.opengl.GLRenderer;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MainEventHandler {
    private final Delta dragDelta;
    private final Stage stage;

    public MainEventHandler(Stage stage) {
        dragDelta = new Delta();
        this.stage = stage;
    }

    public EventHandler<MouseEvent> mousePressEvent = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            dragDelta.x = stage.getX() - mouseEvent.getScreenX();
            dragDelta.y = stage.getY() - mouseEvent.getScreenY();
        }
    };

    public EventHandler<MouseEvent> headerDragEvent = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            stage.setX(mouseEvent.getScreenX() + dragDelta.x);
            stage.setY(mouseEvent.getScreenY() + dragDelta.y);
        }
    };
}

class Delta { double x,y; }