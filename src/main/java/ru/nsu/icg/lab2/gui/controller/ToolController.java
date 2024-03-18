package ru.nsu.icg.lab2.gui.controller;

import ru.nsu.icg.lab2.gui.model.Context;
import ru.nsu.icg.lab2.gui.model.View;
import ru.nsu.icg.lab2.model.ImageFactory;

import java.awt.event.ActionListener;

public abstract class ToolController implements ActionListener {
    private final Context context;
    private final View view;
    private final ImageFactory imageFactory;

    protected ToolController(Context context, View view, ImageFactory imageFactory) {
        this.context = context;
        this.view = view;
        this.imageFactory = imageFactory;
    }

    protected Context getContext() {
        return context;
    }

    protected View getView() {
        return view;
    }

    protected ImageFactory getImageFactory() {
        return imageFactory;
    }
}
