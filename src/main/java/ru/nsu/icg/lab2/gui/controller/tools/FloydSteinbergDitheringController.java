package ru.nsu.icg.lab2.gui.controller.tools;

import ru.nsu.icg.lab2.gui.common.ToolController;
import ru.nsu.icg.lab2.gui.common.context.Context;
import ru.nsu.icg.lab2.gui.common.View;
import ru.nsu.icg.lab2.model.ImageFactory;
import ru.nsu.icg.lab2.model.dto.Tool;
import ru.nsu.icg.lab2.model.transformations.FloydSteinbergDithering;

import java.awt.event.ActionEvent;

public class FloydSteinbergDitheringController extends ToolController {
    private final FloydSteinbergDithering floydSteinbergDithering;

    public FloydSteinbergDitheringController(Context context, View view, ImageFactory imageFactory, Tool tool) {
        super(context, view, imageFactory, tool);
        floydSteinbergDithering = new FloydSteinbergDithering(imageFactory);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        // TODO: implement
        // TODO: add dialog window
    }
}
