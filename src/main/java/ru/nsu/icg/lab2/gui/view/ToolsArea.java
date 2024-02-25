package ru.nsu.icg.lab2.gui.view;

import ru.nsu.icg.lab2.gui.ActionCommands;
import ru.nsu.icg.lab2.model.image.ImageReader;
import ru.nsu.icg.lab2.model.image.ImageReaderImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

public class ToolsArea extends JPanel {
    // TODO: вынести это в конфигурационный файл
    private static final Color AREA_BACKGROUND_COLOR = new Color(0.85f, 0.85f, 0.85f);
    private static final Color BUTTONS_BACKGROUND_COLOR = new Color(0.72f, 0.72f, 0.71f);
    private static final int TOOL_SIZE = 26;
    private static final int ICON_SIZE = 20;

    public ToolsArea(ActionListener actionListener) {
        final ImageReader reader = new ImageReaderImpl();

        ImageIcon icon = null;

        // TODO: убрать колхоз с чтение иконок во View и перенести в Main
        try {
            icon = new ImageIcon(reader.readResource("/star.png"));
        } catch (Exception exception) {
            System.out.println(exception.getLocalizedMessage());
        }

        // TODO: вынести подсказки в конфигурационный файл?
        final List<ToolButton> toolButtonsProperties = Arrays.asList(
                new ToolButton(icon, ActionCommands.SELECT_HAND, "select"),
                new ToolButton(icon, ActionCommands.SWITCH_DISPLAY_MODE, "switch display mode"),
                new ToolButton(icon, ActionCommands.ROTATE, "rotate"),
                new ToolButton(icon, ActionCommands.APPLY_FILTER, "filter")
        );

        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBackground(AREA_BACKGROUND_COLOR);

        for (final var it : toolButtonsProperties) {
            final String actionCommand = it.actionCommand();
            final JButton toolButton = createToolButton(it.icon(), actionCommand, it.tip(), actionListener);
            add(toolButton);
        }
    }

    private static ImageIcon scaleIcon(ImageIcon icon) {
        final Image image = icon.getImage();
        final Image scaledImage = image.getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    private static void initButton(AbstractButton button, String actionCommand, String tip, ActionListener actionListener) {
        button.setFocusPainted(false);
        button.setActionCommand(actionCommand);
        button.setToolTipText(tip);
        button.addActionListener(actionListener);
        button.setPreferredSize(new Dimension(TOOL_SIZE, TOOL_SIZE));
        button.setBackground(BUTTONS_BACKGROUND_COLOR);
        button.setBorderPainted(false);
    }

    private JButton createToolButton(ImageIcon icon, String actionCommand, String tip, ActionListener actionListener) {
        final JButton result = new JButton(scaleIcon(icon));
        initButton(result, actionCommand, tip, actionListener);
        return result;
    }
}
