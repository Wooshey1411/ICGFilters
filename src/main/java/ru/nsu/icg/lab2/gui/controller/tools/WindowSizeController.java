package ru.nsu.icg.lab2.gui.controller.tools;

import ru.nsu.icg.lab2.gui.model.Context;
import ru.nsu.icg.lab2.gui.model.ViewMode;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WindowSizeController implements ActionListener {

    private final Context context;

    public WindowSizeController(Context context){
        this.context = context;
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(context.getViewMode() == ViewMode.ON_WINDOW_SIZE){
            return;
        }
        context.setViewMode(ViewMode.ON_WINDOW_SIZE);
    }
}
