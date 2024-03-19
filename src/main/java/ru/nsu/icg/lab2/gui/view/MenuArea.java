package ru.nsu.icg.lab2.gui.view;

import lombok.Getter;
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
    public static class SelectableTool {
        private final Tool tool;
        private final AbstractButton abstractButton;

        private SelectableTool(AbstractButton abstractButton, Tool tool) {
            this.tool = tool;
            this.abstractButton = abstractButton;
        }

        public boolean isSelected() {
            return abstractButton.isSelected();
        }

        public void setSelected(boolean b) {
            abstractButton.setSelected(b);
        }

        public void addActionListener(ActionListener actionListener) {
            abstractButton.addActionListener(actionListener);
        }
    }

    @Getter
    public static class SelectableToggleTool extends JCheckBoxMenuItem {
        private final Tool tool;

        private SelectableToggleTool(Tool tool) {
            super(tool.getName());
            this.tool = tool;
        }
    }

    // TODO: вынести это в конфигурационный файл
    private static final Font FONT = new Font("Go", Font.BOLD, 14);
    private static final Color MENU_BACKGROUND_COLOR = new Color(0.85f, 0.85f, 0.85f);
    private static final Color BUTTONS_FONT_COLOR = new Color(0.14f, 0.13f, 0.13f);

    private final Map<Integer, ButtonGroup> toolGroups = new HashMap<>();
    private final JMenuBar menuBar;

    @Getter
    private final List<SelectableTool> selectableTools = new ArrayList<>();

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
        result.add(createItem("Open", openListener));
        result.add(createItem("Save", saveListener));
        result.add(createItem("Exit", exitListener));
        return result;
    }

    private JMenu createEditMenu(List<ToolController> toolControllers) {
        final JMenu result = createMenu("Edit");

        for (final var it : toolControllers) {
            final Tool tool = it.getTool();

            if (!tool.hasGroup()) {
                result.add(createItem(it.getTool().getName(), it));
            } else {
                final int group = tool.getGroup();

                int counter = 0;
                for (final var it2 : toolControllers) {
                    if (it2.getTool().getGroup() == group) {
                        counter++;
                    }
                }

                if (counter == 1) {
                    final SelectableTool toggleTool = createCheckboxItem(it);
                    result.add(toggleTool.abstractButton);
                    selectableTools.add(toggleTool);
                } else {
                    final SelectableTool selectableTool = createRadioItem(it);
                    final AbstractButton button = selectableTool.abstractButton;

                    result.add(button);
                    selectableTools.add(selectableTool);

                    if (!toolGroups.containsKey(group)) {
                        toolGroups.put(group, new ButtonGroup());
                    }
                    toolGroups.get(group).add(button);
                }
            }
        }

        return result;
    }

    private static JMenu createInfoMenu(ActionListener helpListener, ActionListener aboutListener) {
        final JMenu result = createMenu("Info");
        result.add(createItem("Help", helpListener));
        result.add(createItem("About", aboutListener));
        return result;
    }

    private static JMenu createMenu(String label) {
        JMenu result = new JMenu(label);
        result.setBackground(MENU_BACKGROUND_COLOR);
        result.setForeground(BUTTONS_FONT_COLOR);
        result.setFont(FONT);
        return result;
    }

    private static JMenuItem createItem(String label, ActionListener actionListener) {
        final JMenuItem result = new JMenuItem(label);
        initButton(result, actionListener);
        return result;
    }

    private static SelectableTool createRadioItem(ToolController toolController) {
        final Tool tool = toolController.getTool();
        final JRadioButtonMenuItem item = new JRadioButtonMenuItem(tool.getName());
        initButton(item, toolController);
        return new SelectableTool(item, tool);
    }

    private static SelectableTool createCheckboxItem(ToolController toolController) {
        final Tool tool = toolController.getTool();
        final JCheckBoxMenuItem item = new JCheckBoxMenuItem(tool.getName());
        initButton(item, toolController);
        return new SelectableTool(item, tool);
    }

    private static void initButton(AbstractButton button, ActionListener actionListener) {
        button.setBorderPainted(false);
        button.setFont(FONT);
        button.setForeground(MENU_BACKGROUND_COLOR);
        button.addActionListener(actionListener);
    }
}
