package ru.nsu.icg.lab2.gui.controller.tools;

import ru.nsu.icg.lab2.gui.controller.ToolController;
import ru.nsu.icg.lab2.gui.model.Context;
import ru.nsu.icg.lab2.gui.model.View;
import ru.nsu.icg.lab2.model.ImageFactory;
import ru.nsu.icg.lab2.model.transformations.Embossing;

import java.awt.event.ActionEvent;

public class EmbossingController extends ToolController {
    private final Embossing embossing;

    public EmbossingController(Context context, View view, ImageFactory imageFactory) {
        super(context, view, imageFactory);
        embossing = new Embossing(imageFactory);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        getContext().setTransformation(embossing);
    }
}
