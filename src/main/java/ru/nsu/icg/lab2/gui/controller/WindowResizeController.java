package ru.nsu.icg.lab2.gui.controller;

import lombok.Setter;
import ru.nsu.icg.lab2.model.context.Context;
import ru.nsu.icg.lab2.model.context.ContextAction;

public class WindowResizeController implements WindowResizeListener {

    public WindowResizeController(Context context){
        this.context = context;
    }
    private final Context context;

    public void onDrawingAreaResize(int width, int height){
        context.setDrawingAreaHeight(height);
        context.setDrawingAreaWidth(width);
        context.setAction(ContextAction.DRAWING_AREA_RESIZE);
    }

}
