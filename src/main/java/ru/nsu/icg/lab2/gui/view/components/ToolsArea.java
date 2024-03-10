package ru.nsu.icg.lab2.gui.view.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ToolsArea extends JPanel {
    // TODO: вынести это в конфигурационный файл
    private static final Color AREA_BACKGROUND_COLOR = new Color(0.85f, 0.85f, 0.85f);
    private static final Color BUTTONS_BACKGROUND_COLOR = new Color(0.72f, 0.72f, 0.71f);
    private static final int TOOL_SIZE = 32;

    public ToolsArea(IconsSupplier iconsSupplier,
                     ActionListener handListener,
                     ActionListener undoListener,
                     ActionListener oneToOneListener,
                     ActionListener windowSizeListener,
                     ActionListener rotationListener,
                     ActionListener blackAndWhiteListener,
                     ActionListener inversionListener,
                     ActionListener gammaCorrectionListener,
                     ActionListener sharpeningListener,
                     ActionListener edgeDetectionListener,
                     ActionListener embossingListener,
                     ActionListener blurListener,
                     ActionListener watercoloringListener,
                     ActionListener floydSteinbergDitheringListener,
                     ActionListener orderedDitheringListener,
                     ActionListener waveFilterListener
    ) {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBackground(AREA_BACKGROUND_COLOR);

        add(createToolButton(iconsSupplier.getHandIcon(), "hand", handListener));
        add(createToolButton(iconsSupplier.getUndoIcon(), "undo", undoListener));
        add(createToolButton(iconsSupplier.getOneToOneIcon(), "1:1", oneToOneListener));
        add(createToolButton(iconsSupplier.getWindowSizeIcon(), "to window size", windowSizeListener));
        add(createToolButton(iconsSupplier.getRotationIcon(), "rotation", rotationListener));
        add(createToolButton(iconsSupplier.getBlackAndWhiteIcon(), "black-and-white", blackAndWhiteListener));
        add(createToolButton(iconsSupplier.getInversionIcon(), "inversion", inversionListener));
        add(createToolButton(iconsSupplier.getGammaCorrectionIcon(), "gamma correction", gammaCorrectionListener));
        add(createToolButton(iconsSupplier.getSharpeningIcon(), "sharpening", sharpeningListener));
        add(createToolButton(iconsSupplier.getEdgeDetectionIcon(), "edge detection", edgeDetectionListener));
        add(createToolButton(iconsSupplier.getEmbossingIcon(), "embossing", embossingListener));
        add(createToolButton(iconsSupplier.getBlurIcon(), "blur", blurListener));
        add(createToolButton(iconsSupplier.getWatercoloringIcon(), "watercoloring", watercoloringListener));
        add(createToolButton(iconsSupplier.getFloydSteinbergDitheringIcon(), "Floyd-Steinberg dithering", floydSteinbergDitheringListener));
        add(createToolButton(iconsSupplier.getOrderedDitheringIcon(), "ordered dithering", orderedDitheringListener));
        add(createToolButton(iconsSupplier.getWaveFilterIcon(), "wave filter", waveFilterListener));
    }

    private static void initButton(AbstractButton button, String tip, ActionListener actionListener) {
        button.setFocusPainted(false);
        button.setToolTipText(tip);
        button.addActionListener(actionListener);
        button.setPreferredSize(new Dimension(TOOL_SIZE, TOOL_SIZE));
        button.setMinimumSize(new Dimension(TOOL_SIZE, TOOL_SIZE));
        button.setBackground(BUTTONS_BACKGROUND_COLOR);
        button.setBorderPainted(false);
    }

    private JButton createToolButton(ImageIcon icon, String tip, ActionListener actionListener) {
        final JButton result = new JButton(icon);
        initButton(result, tip, actionListener);
        return result;
    }
}
