package ru.nsu.icg.lab2.gui.view;

import lombok.Getter;
import ru.nsu.icg.lab2.gui.controller.ToolController;

import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

@Getter
public class MenuArea extends JPanel {
    // TODO: вынести это в конфигурационный файл
    private static final Font FONT = new Font("Go", Font.BOLD, 14);
    private static final Color MENU_BACKGROUND_COLOR = new Color(0.85f, 0.85f, 0.85f);
    private static final Color BUTTONS_FONT_COLOR = new Color(0.14f, 0.13f, 0.13f);

    private final JMenuBar menuBar;

    public MenuArea(
            ActionListener openListener,
            ActionListener saveListener,
            ActionListener exitListener,
            ActionListener helpListener,
            ActionListener aboutListener,
            List<ToolController> toolControllers
    ) {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBackground(MENU_BACKGROUND_COLOR);

        menuBar = new JMenuBar();
        menuBar.setBackground(MENU_BACKGROUND_COLOR);

        menuBar.add(createFileMenu(
                openListener,
                saveListener,
                exitListener
        ));
        menuBar.add(createEditMenu(toolControllers));
        menuBar.add(createInfoMenu(
                helpListener,
                aboutListener
        ));
    }

    private static JMenu createFileMenu(
            ActionListener openListener,
            ActionListener saveListener,
            ActionListener exitListener
    ) {
        final JMenu result = createMenu("File");
        result.add(createMenuItem("Open", openListener));
        result.add(createMenuItem("Save", saveListener));
        result.add(createMenuItem("Exit", exitListener));
        return result;
    }

    private static JMenu createEditMenu(List<ToolController> toolControllers) {
        final JMenu result = createMenu("Edit");
        for (final var it : toolControllers) {
            result.add(createMenuItem(it.getTool().getName(), it));
        }
        return result;
    }

    private static JMenu createInfoMenu(ActionListener helpListener, ActionListener aboutListener) {
        final JMenu result = createMenu("Info");
        result.add(createMenuItem("Help", helpListener));
        result.add(createMenuItem("About", aboutListener));
        return result;
    }

    private static JMenu createMenu(String label) {
        JMenu result = new JMenu(label);
        result.setBackground(MENU_BACKGROUND_COLOR);
        result.setForeground(BUTTONS_FONT_COLOR);
        result.setFont(FONT);
        return result;
    }

    private static JMenuItem createMenuItem(String label, ActionListener actionListener) {
        final JMenuItem result = new JMenuItem(label);
        initButton(result, actionListener);
        return result;
    }

    private static void initButton(AbstractButton button, ActionListener actionListener) {
        button.setBorderPainted(false);
        button.setFont(FONT);
        button.setForeground(MENU_BACKGROUND_COLOR);
        button.addActionListener(actionListener);
    }
}
