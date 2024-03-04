package ru.nsu.icg.lab2.gui.view.components;

import ru.nsu.icg.lab2.gui.ActionCommands;
import ru.nsu.icg.lab2.gui.view.icons.IconsSupplier;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

public class ToolsArea extends JPanel {
    // TODO: вынести это в конфигурационный файл
    private static final Color AREA_BACKGROUND_COLOR = new Color(0.85f, 0.85f, 0.85f);
    private static final Color BUTTONS_BACKGROUND_COLOR = new Color(0.72f, 0.72f, 0.71f);
    private static final int TOOL_SIZE = 25;

    public ToolsArea(IconsSupplier iconsSupplier, ActionListener actionListener) {
        // TODO: вынести подсказки в конфигурационный файл?
        final List<ToolButton> toolButtonsProperties = Arrays.asList(
                new ToolButton(iconsSupplier.getHandIcon(), ActionCommands.SELECT_HAND, "hand"),
                new ToolButton(iconsSupplier.getDisplayIcon(), ActionCommands.SWITCH_DISPLAY_MODE, "switch display mode"),
                new ToolButton(iconsSupplier.getRotationIcon(), ActionCommands.ROTATE, "rotate"),
                new ToolButton(iconsSupplier.getDisplayIcon(), ActionCommands.APPLY_FILTER, "filter")
        );

        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBackground(AREA_BACKGROUND_COLOR);

        for (final var it : toolButtonsProperties) {
            final String actionCommand = it.actionCommand();
            final JButton toolButton = createToolButton(it.icon(), actionCommand, it.tip(), actionListener);
            add(toolButton);
        }
    }

    private static void initButton(AbstractButton button, String actionCommand, String tip, ActionListener actionListener) {
        button.setFocusPainted(false);
        button.setActionCommand(actionCommand);
        button.setToolTipText(tip);
        button.addActionListener(actionListener);
        button.setPreferredSize(new Dimension(TOOL_SIZE, TOOL_SIZE));
        button.setMinimumSize(new Dimension(TOOL_SIZE, TOOL_SIZE));
        button.setBackground(BUTTONS_BACKGROUND_COLOR);
        button.setBorderPainted(false);
    }

    private JButton createToolButton(ImageIcon icon, String actionCommand, String tip, ActionListener actionListener) {
        final JButton result = new JButton(icon);
        initButton(result, actionCommand, tip, actionListener);
        return result;
    }
}
