package ru.nsu.icg.lab2.gui.view.components;

import ru.nsu.icg.lab2.gui.view.IconsSupplier;

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
                     ActionListener oneToOneListener,
                     ActionListener rotationListener,
                     ActionListener greyTransformationListener,
                     ActionListener gammaCorrectionListener
    ) {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBackground(AREA_BACKGROUND_COLOR);

        add(createToolButton(iconsSupplier.getHandIcon(), "hand", handListener));
        add(createToolButton(iconsSupplier.getOneToOneIcon(), "1:1", oneToOneListener));
        add(createToolButton(iconsSupplier.getRotationIcon(), "rotate", rotationListener));
        add(createToolButton(iconsSupplier.getGreyTransformationIcon(), "black-white", greyTransformationListener));
        add(createToolButton(iconsSupplier.getGammaCorrectionIcon(), "gamma correction", gammaCorrectionListener));
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
