package ru.nsu.icg.lab2.gui.view;

import ru.nsu.icg.lab2.gui.common.DrawingAreaAction;
import ru.nsu.icg.lab2.gui.common.ViewMode;
import ru.nsu.icg.lab2.gui.common.context.Context;
import ru.nsu.icg.lab2.gui.common.context.ContextDrawingAreaActionListener;
import ru.nsu.icg.lab2.gui.common.context.ContextViewModeListener;
import ru.nsu.icg.lab2.gui.controller.ToolController;
import ru.nsu.icg.lab2.model.dto.Tool;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Map;

public class ToolsArea extends JPanel implements ContextViewModeListener, ContextDrawingAreaActionListener {
    private class ToolButton {
        private final Tool tool;
        private final AbstractButton button;

        private ToolButton(Tool tool, AbstractButton button) {
            this.tool = tool;
            this.button = button;
        }

        public void setSelected(boolean b) {
            button.setSelected(b);
            if (b) {
                lastSelectedButtons.put(tool.group(), button);
            }
        }
    }

    // TODO: вынести это в конфигурационный файл
    private static final Color AREA_BACKGROUND_COLOR = new Color(0.85f, 0.85f, 0.85f);
    private static final Color BUTTONS_BACKGROUND_COLOR = new Color(0.72f, 0.72f, 0.71f);
    private static final int TOOL_SIZE = 32;

    private ToolButton handButton;
    private ToolButton onWindowSizeButton;
    private ToolButton oneToOneButton;

    private final Map<Integer, AbstractButton> lastSelectedButtons = new HashMap<>();

    public ToolsArea(List<ToolController> toolControllers) {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBackground(AREA_BACKGROUND_COLOR);

        final Map<Integer, ButtonGroup> toolGroups = new HashMap<>();

        for (final var it : toolControllers) {
            final Tool tool = it.getTool();

            AbstractButton abstractButton;

            if (tool.isToggle()) {
                abstractButton = createToolToggleButton(it);
            } else if (tool.hasGroup()) {
                final int group = tool.group();
                abstractButton = createToolToggleButton(it);

                if (!toolGroups.containsKey(group)) {
                    toolGroups.put(group, new ButtonGroup());
                }

                toolGroups.get(group).add(abstractButton);

                abstractButton.addActionListener(actionEvent -> {
                    final AbstractButton lastSelectedButton = lastSelectedButtons.get(group);

                    if (lastSelectedButton != null) {
                        lastSelectedButton.setSelected(true);
                    }

                    lastSelectedButtons.put(group, abstractButton);
                });

            } else {
                abstractButton = createToolButton(it);
            }

            add(abstractButton);

            if (tool.isHand()) {
                handButton = new ToolButton(tool, abstractButton);
            }
            if (tool.isOneToOne()) {
                oneToOneButton = new ToolButton(tool, abstractButton);
            }
            if (tool.isOnWindowSize()) {
                onWindowSizeButton = new ToolButton(tool, abstractButton);
            }
        }
    }

    @Override
    public void onDrawingAreaActionChange(Context context) {
        if (handButton == null) {
            return;
        }

        handButton.setSelected(context.getDrawingAreaAction() == DrawingAreaAction.MOVE_SCROLLS);
    }

    @Override
    public void onChangeViewMode(Context context) {
        final ViewMode viewMode = context.getViewMode();

        if (oneToOneButton != null) {
            oneToOneButton.setSelected(viewMode == ViewMode.ONE_TO_ONE);
        }

        if (onWindowSizeButton != null) {
            onWindowSizeButton.setSelected(viewMode == ViewMode.ON_WINDOW_SIZE);
        }
    }

    private static JButton createToolButton(ToolController toolController) {
        final Tool tool = toolController.getTool();
        final JButton result = new JButton(loadIcon(tool.iconPath()));
        initButton(result, tool.tip(), toolController);
        return result;
    }

    private static JToggleButton createToolToggleButton(ToolController toolController) {
        final Tool tool = toolController.getTool();
        final JToggleButton result = new JToggleButton(loadIcon(tool.iconPath()));
        initButton(result, tool.tip(), toolController);
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
