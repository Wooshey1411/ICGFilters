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
        context.setCurrentImage(Utils.deepCopy(context.getOriginalImage()));
    }
}
