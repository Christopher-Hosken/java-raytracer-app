package io.cjhosken.javaraytracerapp.rendering.paver;

import java.awt.image.BufferedImage;
import java.io.IOException;


import io.cjhosken.javaraytracerapp.jrs.JRSFile;
import io.cjhosken.javaraytracerapp.jrs.datatypes.JRSSettings;
import io.cjhosken.javaraytracerapp.rendering.paver.data.PaverCamera;
import io.cjhosken.javaraytracerapp.rendering.paver.data.PaverWorld;

public class PaverRenderer {
  public PaverRenderer() {
  }

  public static BufferedImage renderJRS(JRSFile jrs) throws IOException {
    JRSSettings settings = jrs.settings();

    BufferedImage image = new BufferedImage(settings.width(), settings.height(), BufferedImage.TYPE_INT_RGB);

    PaverWorld world = jrs.world().toPaverWorld();

    PaverCamera camera = world.camera();

    image = camera.render(image, world, settings.samples(), settings.bounces());

    return image;
  }
}
