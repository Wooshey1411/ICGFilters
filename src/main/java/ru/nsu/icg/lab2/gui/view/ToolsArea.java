package ru.nsu.icg.lab2.gui.view;

import lombok.Getter;
import ru.nsu.icg.lab2.gui.controller.ToolController;
import ru.nsu.icg.lab2.model.dto.Tool;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Map;

@Getter
public class ToolsArea extends JPanel {
    @Getter
    public static class SelectableTool extends JToggleButton {
        private final Tool tool;

        private SelectableTool(ImageIcon icon, Tool tool) {
            super(icon);
            this.tool = tool;
        }
    }

    // TODO: вынести это в конфигурационный файл
    private static final Color AREA_BACKGROUND_COLOR = new Color(0.85f, 0.85f, 0.85f);
    private static final Color BUTTONS_BACKGROUND_COLOR = new Color(0.72f, 0.72f, 0.71f);
    private static final int TOOL_SIZE = 32;

    private final Map<Integer, ButtonGroup> toolGroups = new HashMap<>();
    private final List<SelectableTool> selectableTools = new ArrayList<>();

    public ToolsArea(List<ToolController> toolControllers) {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBackground(AREA_BACKGROUND_COLOR);

        for (final var it : toolControllers) {
            final Tool tool = it.getTool();

            if (!tool.hasGroup()) {
                add(createToolButton(it));
            } else {
                final int group = tool.getGroup();

                final SelectableTool toggleTool = createSelectableToolButton(it);
                add(toggleTool);
                selectableTools.add(toggleTool);

                int counter = 0;
                for (final var it2 : toolControllers) {
                    if (it2.getTool().getGroup() == group) {
                        counter++;
                    }
                }

                if (counter == 1) {
                    continue;
                }

                if (!toolGroups.containsKey(group)) {
                    toolGroups.put(group, new ButtonGroup());
                }

                toolGroups.get(group).add(toggleTool);
            }
        }
    }

    private static JButton createToolButton(ToolController toolController) {
        final Tool tool = toolController.getTool();
        final JButton result = new JButton(loadIcon(tool.getIconPath()));
        initButton(result, tool.getTip(), toolController);
        return result;
    }

    private static SelectableTool createSelectableToolButton(ToolController toolController) {
        final Tool tool = toolController.getTool();
        final SelectableTool result = new SelectableTool(loadIcon(tool.getIconPath()), tool);
        initButton(result, tool.getTip(), toolController);
        return result;
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

    private static ImageIcon loadIcon(String path) {
        final URL url = ToolsArea.class.getResource(path);

        if (url == null) {
            throw new RuntimeException("Icon not found: " + path);
        }

        return new ImageIcon(url);
    }
}
