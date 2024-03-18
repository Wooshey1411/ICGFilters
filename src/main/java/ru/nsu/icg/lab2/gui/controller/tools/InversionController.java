package ru.nsu.icg.lab2.gui.controller.tools;

import ru.nsu.icg.lab2.gui.controller.ToolController;
import ru.nsu.icg.lab2.gui.model.Context;
import ru.nsu.icg.lab2.gui.model.View;
import ru.nsu.icg.lab2.model.ImageFactory;
import ru.nsu.icg.lab2.model.transformations.Inversion;

import java.awt.event.ActionEvent;

public class InversionController extends ToolController {
    private final Inversion inversion;

    public InversionController(Context context, View view, ImageFactory imageFactory) {
        super(context, view, imageFactory);
        inversion = new Inversion(imageFactory);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        getContext().setTransformation(inversion);
    }
}
