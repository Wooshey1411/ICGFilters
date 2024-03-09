package ru.nsu.icg.lab2.gui.controller.tools.transformations;

import ru.nsu.icg.lab2.gui.view.context.ContextImpl;
import ru.nsu.icg.lab2.model.transformations.Rotation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RotationController implements ActionListener {
    private final ContextImpl context;
    private final Rotation rotation;

    public RotationController(ContextImpl context) {
        this.context = context;
        this.rotation = new Rotation();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }
}
