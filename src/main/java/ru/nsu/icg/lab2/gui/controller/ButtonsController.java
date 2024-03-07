package ru.nsu.icg.lab2.gui.controller;

import ru.nsu.icg.lab2.gui.ActionCommands;
import ru.nsu.icg.lab2.model.context.Context;
import ru.nsu.icg.lab2.model.context.ContextAction;
import ru.nsu.icg.lab2.model.context.ViewMode;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class ButtonsController implements ActionListener {
    private final Context context;

    private final Map<String, Runnable> actionCommandsHandlers = new HashMap<>();

    public ButtonsController(Context context) {
        this.context = context;

        actionCommandsHandlers.put(ActionCommands.OPEN, this::executeOpenActionCommand);
        actionCommandsHandlers.put(ActionCommands.SAVE, this::executeSaveActionCommand);
        actionCommandsHandlers.put(ActionCommands.EXIT, this::executeExitActionCommand);
        actionCommandsHandlers.put(ActionCommands.SELECT_HAND, this::executeSelectHandActionCommand);
        actionCommandsHandlers.put(ActionCommands.SWITCH_DISPLAY_MODE, this::executeSwitchDisplayModeActionCommand);
        actionCommandsHandlers.put(ActionCommands.ROTATE, this::executeRotateActionCommand);
        actionCommandsHandlers.put(ActionCommands.CHANGE_VIEW_MODE, this::executeChangeViewModeActionCommand);
        actionCommandsHandlers.put(ActionCommands.SHOW_HELP, this::executeShowHelpActionCommand);
        actionCommandsHandlers.put(ActionCommands.SHOW_ABOUT, this::executeShowAboutActionCommand);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        actionCommandsHandlers.get(actionEvent.getActionCommand()).run();
    }

    private void executeOpenActionCommand() {
        context.setAction(ContextAction.OPEN_FILE);
    }

    private void executeSaveActionCommand() {
        context.setAction(ContextAction.SAVE_FILE);
    }

    private void executeExitActionCommand() {
        context.setAction(ContextAction.EXIT);
    }

    private void executeSelectHandActionCommand() {
        System.out.println("Somebody pressed \"Select hand\"");
        context.setViewMode(ViewMode.SIZE_TO_SIZE);
    }

    private void executeSwitchDisplayModeActionCommand() {
        System.out.println("Somebody pressed \"Switch display mode\"");
        context.setViewMode(ViewMode.ON_WINDOW_SIZE);
    }

    private void executeRotateActionCommand() {
        System.out.println("Somebody pressed \"Rotate\"");
    }

    private void executeChangeViewModeActionCommand() {
        System.out.println("Somebody pressed \"ChangeViewMode\"");
    }

    private void executeShowHelpActionCommand() {
        context.setAction(ContextAction.DISPLAY_HELP);
    }

    private void executeShowAboutActionCommand() {
        context.setAction(ContextAction.DISPLAY_ABOUT);
    }
}
