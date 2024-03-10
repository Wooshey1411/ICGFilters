package ru.nsu.icg.lab2.gui.controller;

import ru.nsu.icg.lab2.gui.view.Context;
import ru.nsu.icg.lab2.gui.view.View;
import ru.nsu.icg.lab2.gui.view.WindowResizeListener;

public class WindowResizeController implements WindowResizeListener {
    private final Context context;
    private final View view;

    public WindowResizeController(Context context, View view) {
        this.context = context;
        this.view = view;
    }

    public void onDrawingAreaResize(int width, int height) {
        context.setDrawingAreaHeight(height);
        context.setDrawingAreaWidth(width);
        view.resize();
    }
}
