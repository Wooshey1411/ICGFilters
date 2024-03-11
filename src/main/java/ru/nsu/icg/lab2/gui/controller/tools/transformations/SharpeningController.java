package ru.nsu.icg.lab2.gui.controller.tools.transformations;

import ru.nsu.icg.lab2.gui.model.Context;
import ru.nsu.icg.lab2.model.transformations.Sharpening;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SharpeningController implements ActionListener {
    private final Context context;
    private final Sharpening sharpening;

    public SharpeningController(Context context) {
        this.context = context;
        this.sharpening = new Sharpening();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        context.setTransformation(sharpening);
    }
}
