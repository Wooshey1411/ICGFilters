package ru.nsu.icg.lab2.gui.controller.tools.transformations;

import ru.nsu.icg.lab2.gui.view.Context;
import ru.nsu.icg.lab2.gui.view.View;
import ru.nsu.icg.lab2.model.transformations.Blur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BlurController implements ActionListener {
    private final Context context;
    private final View view;
    private final Blur blur;

    public BlurController(Context context, View view) {
        this.context = context;
        this.view = view;
        this.blur = new Blur();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }
}
