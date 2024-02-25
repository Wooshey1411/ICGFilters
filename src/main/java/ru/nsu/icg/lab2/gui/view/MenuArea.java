package ru.nsu.icg.lab2.gui.view;

import lombok.Getter;
import ru.nsu.icg.lab2.gui.ActionCommands;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Arrays;

@Getter
public class MenuArea extends JPanel {
    // TODO: вынести это в конфигурационный файл
    private static final Font FONT = new Font("Go", Font.BOLD, 14);
    private static final Color MENU_BACKGROUND_COLOR = new Color(0.85f, 0.85f, 0.85f);
    private static final Color BUTTONS_FONT_COLOR = new Color(0.14f, 0.13f, 0.13f);

    // TODO: вынести в конфигурационный файл названия меню и кнопок в них (надо ли?)
    private static final MenuProperties[] MENUS = {
            new MenuProperties(
                    "File",
                    Arrays.asList(
                            new MenuItemProperties("Open", ActionCommands.OPEN),
                            new MenuItemProperties("Save", ActionCommands.SAVE),
                            new MenuItemProperties("Exit", ActionCommands.EXIT)
                    )
            ),
            new MenuProperties(
                    "Edit",
                    Arrays.asList(
                            new MenuItemProperties("Hand", ActionCommands.SELECT_HAND),
                            new MenuItemProperties("Display mode", ActionCommands.SWITCH_DISPLAY_MODE),
                            new MenuItemProperties("Rotate", ActionCommands.ROTATE),
                            new MenuItemProperties("Filter", ActionCommands.APPLY_FILTER)
                    )
            ),
            new MenuProperties(
                    "Info",
                    Arrays.asList(
                            new MenuItemProperties("Help", ActionCommands.SHOW_HELP),
                            new MenuItemProperties("About", ActionCommands.SHOW_ABOUT)
                    )
            ),
    };

    private final JMenuBar menuBar;

    public MenuArea(ActionListener actionListener) {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBackground(MENU_BACKGROUND_COLOR);

        menuBar = new JMenuBar();
        menuBar.setBackground(MENU_BACKGROUND_COLOR);

        for (final MenuProperties menuProperties : MENUS) {
            final JMenu menu = createMenu(menuProperties.label());

            for (final MenuItemProperties itemProperties : menuProperties.items()) {
                final JMenuItem item = createMenuItem(
                        itemProperties.label(),
                        itemProperties.actionCommand(), actionListener
                );
                menu.add(item);
            }

            menuBar.add(menu);
        }
    }

    private static JMenu createMenu(String label) {
        JMenu result = new JMenu(label);
        result.setBackground(MENU_BACKGROUND_COLOR);
        result.setForeground(BUTTONS_FONT_COLOR);
        result.setFont(FONT);
        return result;
    }

    private static JMenuItem createMenuItem(String label, String actionCommand, ActionListener actionListener) {
        final JMenuItem result = new JMenuItem(label);
        initButton(result, actionCommand, actionListener);
        return result;
    }

    private static void initButton(AbstractButton button, String actionCommand, ActionListener actionListener) {
        button.setBorderPainted(false);
        button.setFont(FONT);
        button.setForeground(BUTTONS_FONT_COLOR);
        button.addActionListener(actionListener);
        button.setActionCommand(actionCommand);
    }
}
