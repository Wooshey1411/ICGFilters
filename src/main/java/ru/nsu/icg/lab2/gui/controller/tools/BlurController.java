package ru.nsu.icg.lab2.gui.controller.tools;

import ru.nsu.icg.lab2.gui.controller.ToolController;
import ru.nsu.icg.lab2.gui.common.Context;
import ru.nsu.icg.lab2.gui.common.View;
import ru.nsu.icg.lab2.model.ImageFactory;
import ru.nsu.icg.lab2.model.dto.Tool;
import ru.nsu.icg.lab2.model.transformations.Blur;

import java.awt.event.ActionEvent;

public class BlurController extends ToolController {
    private final Blur blur;

    public BlurController(Context context, View view, ImageFactory imageFactory, Tool tool) {
        super(context, view, imageFactory, tool);
        blur = new Blur(imageFactory);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        blur.setParameters(1.5, 15);
        getContext().setTransformation(blur);
    }
}
