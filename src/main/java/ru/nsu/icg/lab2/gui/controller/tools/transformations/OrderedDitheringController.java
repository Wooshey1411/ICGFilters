package ru.nsu.icg.lab2.gui.controller.tools.transformations;

import ru.nsu.icg.lab2.gui.view.context.Context;
import ru.nsu.icg.lab2.model.transformations.OrderedDithering;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OrderedDitheringController implements ActionListener {
    private final Context context;
    private final OrderedDithering orderedDithering;

    public OrderedDitheringController(Context context) {
        this.context = context;
        this.orderedDithering = new OrderedDithering();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }
}
