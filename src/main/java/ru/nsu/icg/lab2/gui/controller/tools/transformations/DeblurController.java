package ru.nsu.icg.lab2.gui.controller.tools.transformations;

import ru.nsu.icg.lab2.gui.view.context.ContextImpl;
import ru.nsu.icg.lab2.model.transformations.Deblur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeblurController implements ActionListener {
    private final ContextImpl context;
    private final Deblur deblur;

    public DeblurController(ContextImpl context) {
        this.context = context;
        this.deblur = new Deblur();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }
}
