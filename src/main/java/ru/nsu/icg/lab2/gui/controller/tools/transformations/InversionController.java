package ru.nsu.icg.lab2.gui.controller.tools.transformations;

import ru.nsu.icg.lab2.gui.view.context.Context;
import ru.nsu.icg.lab2.model.transformations.Inversion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InversionController implements ActionListener {
    private final Context context;
    private final Inversion inversion;

    public InversionController(Context context) {
        this.context = context;
        this.inversion = new Inversion();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        context.setTransformation(inversion);
    }
}
