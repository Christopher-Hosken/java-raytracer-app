package io.cjhosken.javaraytracerapp.helpers.controllers;

import io.cjhosken.javaraytracerapp.rendering.fx3d.FX3DRenderer;

public class SceneController {
    private FX3DRenderer renderer;

    public SceneController(FX3DRenderer renderer) {
        this.renderer = renderer;
    }
}
