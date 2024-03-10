package ru.nsu.icg.lab2.gui;

import ru.nsu.icg.lab2.gui.view.BufferedImageImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

public final class Utils {
    private Utils() {}

    public static JPanel createDialogInputPanel(JTextField textField, JSlider slider) {
        final JPanel result = new JPanel();
        result.setLayout(new GridLayout(2, 1));
        result.add(textField);
        result.add(slider);
        return result;
    }

    public static BufferedImage createBlankImage(int width, int height) {
        final BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        final Graphics2D graphics = (Graphics2D) result.getGraphics();
        graphics.setPaint(Color.WHITE);
        graphics.fillRect(0, 0, width, height);

        return result;
    }

    public static BufferedImageImpl deepCopy(BufferedImageImpl bufferedImageImpl) {
        final BufferedImage bufferedImage = bufferedImageImpl.bufferedImage();
        ColorModel cm = bufferedImage.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bufferedImage.copyData(bufferedImage.getRaster().createCompatibleWritableRaster());
        return new BufferedImageImpl(new BufferedImage(cm, raster, isAlphaPremultiplied, null));
    }
}
