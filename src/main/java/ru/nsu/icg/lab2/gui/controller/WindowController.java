package ru.nsu.icg.lab2.gui.controller;

import ru.nsu.icg.lab2.model.context.Context;
import ru.nsu.icg.lab2.model.context.ContextAction;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WindowController extends WindowAdapter {
    private final Context context;

    public WindowController(Context context) {
        this.context = context;
    }

    @Override
    public void windowClosing(WindowEvent e) {
        context.setAction(ContextAction.EXIT);
    }
}
