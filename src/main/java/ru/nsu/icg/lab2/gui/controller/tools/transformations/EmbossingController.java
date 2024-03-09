package ru.nsu.icg.lab2.gui.controller.tools.transformations;

import ru.nsu.icg.lab2.gui.view.context.ContextImpl;
import ru.nsu.icg.lab2.model.transformations.Embossing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmbossingController implements ActionListener {
    private final ContextImpl context;
    private final Embossing embossing;

    public EmbossingController(ContextImpl context) {
        this.context = context;
        this.embossing = new Embossing();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }
}
