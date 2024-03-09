package ru.nsu.icg.lab2.gui.controller.tools.transformations;

import ru.nsu.icg.lab2.gui.view.context.ContextImpl;
import ru.nsu.icg.lab2.model.transformations.Watercoloring;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WatercoloringController implements ActionListener {
    private final ContextImpl context;
    private final Watercoloring watercoloring;

    public WatercoloringController(ContextImpl context) {
        this.context = context;
        this.watercoloring = new Watercoloring();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }
}
