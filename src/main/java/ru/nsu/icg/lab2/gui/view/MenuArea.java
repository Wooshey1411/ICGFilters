package ru.nsu.icg.lab2.gui.view;

import lombok.Getter;
import lombok.Setter;
import ru.nsu.icg.lab2.gui.controller.ToolController;
import ru.nsu.icg.lab2.model.dto.Tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Map;

@Getter
public class MenuArea extends JPanel {
    @Getter
    private static class SelectableRadioItem extends JRadioButtonMenuItem {
        private final Tool tool;

        private SelectableRadioItem(String label, Tool tool) {
            super(label);
            this.tool = tool;
        }
    }

    @Getter
    @Setter
    private static class SelectableToggleItem extends JCheckBoxMenuItem {
        private final Tool tool;
        private boolean needUpdate;

        private SelectableToggleItem(String label, Tool tool) {
            super(label);
            this.tool = tool;
        }
    }

    // TODO: вынести это в конфигурационный файл
    private static final Font FONT = new Font("Go", Font.BOLD, 14);
    private static final Color MENU_BACKGROUND_COLOR = new Color(0.85f, 0.85f, 0.85f);
    private static final Color BUTTONS_FONT_COLOR = new Color(0.14f, 0.13f, 0.13f);

    private final Map<Integer, ButtonGroup> radioItemsGroups = new HashMap<>();
    private final List<SelectableToggleItem> toggleItems = new ArrayList<>();

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

    public void selectTool(Tool tool) {
        // Trying to find toggle-button-tool. Else - tool is radio-item
        for (final var it : toggleItems) {
            if (tool.equals(it.getTool())) {
                it.setSelected(!it.isSelected());
                return;
            }
        }

        // Handling case when the tool is radio-item
        final ButtonGroup toolsGroup = radioItemsGroups.get(tool.getGroup());
        if (toolsGroup == null) {
            return;
        }
        final var elements = toolsGroup.getElements();
        while (elements.hasMoreElements()) {
            final var item = (SelectableRadioItem)elements.nextElement();
            item.setSelected(item.getTool() == tool);
        }
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

    private JMenu createEditMenu(List<ToolController> toolControllers) {
        final JMenu result = createMenu("Edit");
        for (final var it : toolControllers) {
            final Tool tool = it.getTool();

            if (!tool.hasGroup()) {
                result.add(createMenuItem(it.getTool().getName(), it));
            } else {
                final int group = tool.getGroup();

                int counter = 0;
                for (final var it2 : toolControllers) {
                    if (it2.getTool().getGroup() == group) {
                        counter++;
                    }
                }

                if (counter == 1) {
                    final SelectableToggleItem toggleItem = createToggleButtonMenuItem(tool.getName(), it, tool);
                    result.add(toggleItem);
                    toggleItems.add(toggleItem);
                } else {
                    final SelectableRadioItem item = createRadioButtonMenuItem(tool.getName(), it, tool);
                    result.add(item);

                    if (!radioItemsGroups.containsKey(group)) {
                        radioItemsGroups.put(group, new ButtonGroup());
                    }
                    radioItemsGroups.get(group).add(item);
                }
            }
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

    private static SelectableRadioItem createRadioButtonMenuItem(String label, ActionListener actionListener, Tool tool) {
        final SelectableRadioItem result = new SelectableRadioItem(label, tool);
        initButton(result, actionListener);
        return result;
    }

    private static SelectableToggleItem createToggleButtonMenuItem(String label, ActionListener actionListener, Tool tool) {
        final SelectableToggleItem result = new SelectableToggleItem(label, tool);
        result.addActionListener(actionEvent -> result.setSelected(!result.isSelected()));
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
