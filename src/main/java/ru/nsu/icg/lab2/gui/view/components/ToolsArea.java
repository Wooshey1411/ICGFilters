package ru.nsu.icg.lab2.gui.view.components;

import ru.nsu.icg.lab2.gui.controller.tools.HandController;
import ru.nsu.icg.lab2.gui.controller.tools.OneToOneController;
import ru.nsu.icg.lab2.gui.controller.tools.transformations.GreyTransformationController;
import ru.nsu.icg.lab2.gui.controller.tools.transformations.RotationController;
import ru.nsu.icg.lab2.gui.view.IconsSupplier;
import ru.nsu.icg.lab2.gui.view.context.ContextImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

public class ToolsArea extends JPanel {
    // TODO: вынести это в конфигурационный файл
    private static final Color AREA_BACKGROUND_COLOR = new Color(0.85f, 0.85f, 0.85f);
    private static final Color BUTTONS_BACKGROUND_COLOR = new Color(0.72f, 0.72f, 0.71f);
    private static final int TOOL_SIZE = 32;

    public ToolsArea(IconsSupplier iconsSupplier, ContextImpl context) {
        // TODO: вынести подсказки в конфигурационный файл?
        final List<ToolButton> toolButtonsProperties = Arrays.asList(
                new ToolButton(
                        new HandController(),
                        iconsSupplier.getHandIcon(),
                        "hand"
                ),
                new ToolButton(
                        new OneToOneController(),
                        iconsSupplier.getOneToOneIcon(),
                        "change view mode"
                ),
                new ToolButton(
                        new RotationController(context),
                        iconsSupplier.getRotationIcon(),
                        "rotate"
                ),
                new ToolButton(
                        new GreyTransformationController(context),
                        iconsSupplier.getGreyTransformationIcon(),
                        "convert to black-and-white"
                )
        );

        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBackground(AREA_BACKGROUND_COLOR);

        for (final var it : toolButtonsProperties) {
            final JButton toolButton = createToolButton(it.icon(), it.tip(), it.actionListener());
            toolButton.setPreferredSize(new Dimension(TOOL_SIZE,TOOL_SIZE));
            add(toolButton);
        }
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
