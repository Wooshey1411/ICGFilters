package ru.nsu.icg.lab2.gui.controller.tools.transformations;

import ru.nsu.icg.lab2.gui.view.context.ContextImpl;
import ru.nsu.icg.lab2.model.transformations.Inversion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InversionController implements ActionListener {
    private final ContextImpl context;
    private final Inversion inversion;

    public InversionController(ContextImpl context) {
        this.context = context;
        this.inversion = new Inversion();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }
}
