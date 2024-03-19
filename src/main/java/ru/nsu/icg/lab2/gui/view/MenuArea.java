package ru.nsu.icg.lab2.gui.view;

import lombok.Getter;
import ru.nsu.icg.lab2.gui.common.DrawingAreaAction;
import ru.nsu.icg.lab2.gui.common.ViewMode;
import ru.nsu.icg.lab2.gui.common.context.Context;
import ru.nsu.icg.lab2.gui.common.context.ContextDrawingAreaActionListener;
import ru.nsu.icg.lab2.gui.common.context.ContextViewModeListener;
import ru.nsu.icg.lab2.gui.controller.ToolController;
import ru.nsu.icg.lab2.model.dto.Tool;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class MenuArea extends JPanel implements ContextViewModeListener, ContextDrawingAreaActionListener {
    // TODO: вынести это в конфигурационный файл
    private static final Font FONT = new Font("Go", Font.BOLD, 14);
    private static final Color MENU_BACKGROUND_COLOR = new Color(0.85f, 0.85f, 0.85f);
    private static final Color BUTTONS_FONT_COLOR = new Color(0.14f, 0.13f, 0.13f);

    private AbstractButton handButton;
    private AbstractButton onWindowSizeButton;
    private AbstractButton oneToOneButton;

    @Getter
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
        result.add(createItem("Open", openListener));
        result.add(createItem("Save", saveListener));
        result.add(createItem("Exit", exitListener));
        return result;
    }

    private JMenu createEditMenu(List<ToolController> toolControllers) {
        final JMenu result = createMenu("Edit");

        final Map<Integer, ButtonGroup> toolGroups = new HashMap<>();

        for (final var it : toolControllers) {
            final Tool tool = it.getTool();
            final String toolName = tool.name();

            JMenuItem item;

            if (tool.isToggle()) {
                item = createCheckboxItem(toolName, it);
            } else if (tool.hasGroup()) {
                item = createRadioItem(toolName, it);
                final int group = tool.group();

                if (!toolGroups.containsKey(group)) {
                    toolGroups.put(group, new ButtonGroup());
                }

                toolGroups.get(group).add(item);
            } else {
                item = createItem(toolName, it);
            }

            result.add(item);

            if (tool.isHand()) {
                handButton = item;
            }
            if (tool.isOneToOne()) {
                oneToOneButton = item;
            }
            if (tool.isOnWindowSize()) {
                onWindowSizeButton = item;
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

    private static JRadioButtonMenuItem createRadioItem(String label, ActionListener actionListener) {
        final JRadioButtonMenuItem result = new JRadioButtonMenuItem(label);
        initButton(result, actionListener);
        return result;
    }

    private static JCheckBoxMenuItem createCheckboxItem(String label, ActionListener actionListener) {
        final JCheckBoxMenuItem result = new JCheckBoxMenuItem(label);
        initButton(result, actionListener);
        return result;
    }

    private static void initButton(AbstractButton button, ActionListener actionListener) {
        button.setBorderPainted(false);
        button.setFont(FONT);
        button.setForeground(MENU_BACKGROUND_COLOR);
        button.addActionListener(actionListener);
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
}
