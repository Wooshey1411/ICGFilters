package ru.nsu.icg.lab2.gui.controller.tools.transformations;

import ru.nsu.icg.lab2.gui.model.Context;
import ru.nsu.icg.lab2.gui.model.View;
import ru.nsu.icg.lab2.model.transformations.FloydSteinbergDithering;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FloydSteinbergDitheringController implements ActionListener {
    private final Context context;
    private final View view;
    private final FloydSteinbergDithering floydSteinbergDithering;

    public FloydSteinbergDitheringController(Context context, View view) {
        this.context = context;
        this.view = view;
        this.floydSteinbergDithering = new FloydSteinbergDithering();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }
}
