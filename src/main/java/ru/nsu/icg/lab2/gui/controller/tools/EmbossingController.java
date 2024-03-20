package ru.nsu.icg.lab2.gui.controller.tools;

import ru.nsu.icg.lab2.gui.common.ToolController;
import ru.nsu.icg.lab2.gui.common.Context;
import ru.nsu.icg.lab2.gui.common.View;
import ru.nsu.icg.lab2.model.ImageFactory;
import ru.nsu.icg.lab2.model.dto.Tool;
import ru.nsu.icg.lab2.model.transformations.Embossing;

import java.awt.event.ActionEvent;

public class EmbossingController extends ToolController {
    private final Embossing embossing;

    public EmbossingController(Context context, View view, ImageFactory imageFactory, Tool tool) {
        super(context, view, imageFactory, tool);
        embossing = new Embossing(imageFactory);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        // TODO: add dialog panel for brightness-choice

        getContext().setTransformation(embossing);
    }
}
