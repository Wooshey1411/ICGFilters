package ru.nsu.icg.lab2.gui.controller.tools;

import ru.nsu.icg.lab2.gui.model.Context;
import ru.nsu.icg.lab2.gui.model.ViewMode;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OneToOneController implements ActionListener {
    private final Context context;

    public OneToOneController(Context context){
        this.context = context;
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(context.getViewMode() == ViewMode.ONE_TO_ONE){
            return;
        }
        context.setViewMode(ViewMode.ONE_TO_ONE);
    }
}
