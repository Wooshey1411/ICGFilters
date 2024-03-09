package ru.nsu.icg.lab2.gui.controller.tools.transformations;

import ru.nsu.icg.lab2.gui.view.context.ContextImpl;
import ru.nsu.icg.lab2.model.transformations.AntiAliasing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AntiAliasingController implements ActionListener {
    private final ContextImpl context;
    private final AntiAliasing antiAliasing;

    public AntiAliasingController(ContextImpl context) {
        this.context = context;
        this.antiAliasing = new AntiAliasing();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }
}
