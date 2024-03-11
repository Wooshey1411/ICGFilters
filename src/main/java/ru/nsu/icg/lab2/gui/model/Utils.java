package ru.nsu.icg.lab2.gui.model;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

public final class Utils {
    private Utils() {
    }

    public static JPanel createDialogInputPanel(JTextField textField, JSlider slider) {
        final JPanel result = new JPanel();
        result.setLayout(new GridLayout(2, 1));
        result.add(textField);
        result.add(slider);
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
