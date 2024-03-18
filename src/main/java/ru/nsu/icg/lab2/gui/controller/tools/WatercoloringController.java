package ru.nsu.icg.lab2.gui.controller.tools;

import ru.nsu.icg.lab2.gui.controller.ToolController;
import ru.nsu.icg.lab2.gui.model.Context;
import ru.nsu.icg.lab2.gui.model.View;
import ru.nsu.icg.lab2.model.ImageFactory;
import ru.nsu.icg.lab2.model.transformations.Watercoloring;

import java.awt.event.ActionEvent;

public class WatercoloringController extends ToolController {
    private final Watercoloring watercoloring;

    public WatercoloringController(Context context, View view, ImageFactory imageFactory) {
        super(context, view, imageFactory);
        watercoloring = new Watercoloring(imageFactory);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        getContext().setTransformation(watercoloring);
    }
}
