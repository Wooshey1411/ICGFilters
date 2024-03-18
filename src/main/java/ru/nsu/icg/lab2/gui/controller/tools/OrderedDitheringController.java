package ru.nsu.icg.lab2.gui.controller.tools;

import ru.nsu.icg.lab2.gui.controller.ToolController;
import ru.nsu.icg.lab2.gui.model.Context;
import ru.nsu.icg.lab2.gui.model.View;
import ru.nsu.icg.lab2.model.ImageFactory;
import ru.nsu.icg.lab2.model.Tool;
import ru.nsu.icg.lab2.model.transformations.OrderedDithering;

import java.awt.event.ActionEvent;

public class OrderedDitheringController extends ToolController {
    private final OrderedDithering orderedDithering;

    public OrderedDitheringController(Context context, View view, ImageFactory imageFactory, Tool tool) {
        super(context, view, imageFactory, tool);
        orderedDithering = new OrderedDithering(imageFactory);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        getContext().setTransformation(orderedDithering);
    }
}
