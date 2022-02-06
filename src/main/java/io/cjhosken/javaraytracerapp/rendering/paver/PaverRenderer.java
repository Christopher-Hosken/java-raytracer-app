package io.cjhosken.javaraytracerapp.rendering.paver;
import java.awt.image.BufferedImage;
import java.io.IOException;

import org.json.JSONObject;

import io.cjhosken.javaraytracerapp.helpers.jrs.JRSUtils;
import io.cjhosken.javaraytracerapp.rendering.paver.data.base.PaverWorld;
import io.cjhosken.javaraytracerapp.rendering.paver.data.objects.PaverCamera;

public class PaverRenderer {
    public static BufferedImage renderJRS(JSONObject jrs) throws IOException {
        BufferedImage image = new BufferedImage(JRSUtils.getImageWidthFromJRS(jrs), JRSUtils.getImageHeightFromJRS(jrs), BufferedImage.TYPE_INT_RGB);

        PaverCamera camera = JRSUtils.getPaverCameraFromJRS(jrs);
        PaverWorld world = JRSUtils.getPaverWorldFromJRS(jrs);

        image = camera.render(image, world, JRSUtils.getSamplesFromJRS(jrs), JRSUtils.getBouncesFromJRS(jrs));

        return image;
    }
}
