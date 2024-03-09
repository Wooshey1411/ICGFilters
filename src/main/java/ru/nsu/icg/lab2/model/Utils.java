package ru.nsu.icg.lab2.model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

public final class Utils {
    private Utils() {}

    public static BufferedImage createBlankImage(int width, int height) {
        final BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        final Graphics2D graphics = (Graphics2D) result.getGraphics();
        graphics.setPaint(Color.WHITE);
        graphics.fillRect(0, 0, width, height);

        return result;
    }

    public static BufferedImage deepCopy(BufferedImage bufferedImage) {
        ColorModel cm = bufferedImage.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bufferedImage.copyData(bufferedImage.getRaster().createCompatibleWritableRaster());
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }
}
