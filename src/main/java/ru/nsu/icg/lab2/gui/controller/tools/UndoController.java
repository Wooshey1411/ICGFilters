package ru.nsu.icg.lab2.gui.controller.tools;

import ru.nsu.icg.lab2.gui.model.Context;
import ru.nsu.icg.lab2.gui.model.Utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UndoController implements ActionListener {
    private final Context context;

    public UndoController(Context context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        // TODO: проверки на null, Hip-Hop icon
        if(context.getCurrentImage() == context.getProcessedImage()){
            context.setCurrentImage(context.getOriginalImage());
        } else {
            context.setCurrentImage(context.getProcessedImage());
        }
    }
}
