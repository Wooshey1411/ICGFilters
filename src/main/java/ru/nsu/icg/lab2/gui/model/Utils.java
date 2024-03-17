package ru.nsu.icg.lab2.gui.model;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

public final class Utils {
    private Utils() {
    }

    public static JPanel createSimpleSliderDialogInputPanel(JTextField textField, JSlider slider, String name, int textShift) {
        final JPanel result = new JPanel();
        textField.setSize(40,40);
        result.setLayout(new GridLayout(1, 3));
        result.add(new JLabel(" ".repeat(textShift) +name));
        result.add(textField);
        result.add(slider);
        return result;
    }

    public static void addSyncSliderTo3ColsPanel(JPanel jPanel, JTextField textField, JSlider slider, String name, int textShift){
        GridLayout gridLayout = (GridLayout) jPanel.getLayout();
        jPanel.setLayout(new GridLayout(gridLayout.getRows()+1,3));
        jPanel.add(new JLabel(" ".repeat(textShift) +name));
        jPanel.add(textField);
        jPanel.add(slider);
    }

    public static void addComboBoxTo3ColsPanel(JPanel jPanel, JComboBox<?> comboBox,String name, int textShift){
        GridLayout gridLayout = (GridLayout) jPanel.getLayout();
        jPanel.setLayout(new GridLayout(gridLayout.getRows()+1,3));
        jPanel.add(new JLabel(" ".repeat(textShift) +name));
        jPanel.add(comboBox);
    }

    public static JPanel createSimpleComboDialogInputPanel(JComboBox<?> comboBox,String name, int textShift){
        final JPanel result = new JPanel(new GridLayout(1,2));
        result.add(new JLabel(" ".repeat(textShift) +name));
        result.add(comboBox);
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
