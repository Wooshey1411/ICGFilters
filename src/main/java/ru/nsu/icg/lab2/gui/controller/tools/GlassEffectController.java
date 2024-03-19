package ru.nsu.icg.lab2.gui.controller.tools;

import ru.nsu.icg.lab2.gui.controller.ToolController;
import ru.nsu.icg.lab2.gui.common.context.Context;
import ru.nsu.icg.lab2.gui.common.View;
import ru.nsu.icg.lab2.model.ImageFactory;
import ru.nsu.icg.lab2.model.dto.Tool;
import ru.nsu.icg.lab2.model.transformations.GlassEffect;

import java.awt.event.ActionEvent;

public class GlassEffectController extends ToolController {
    private final GlassEffect glassEffect;

    public GlassEffectController(Context context, View view, ImageFactory imageFactory, Tool tool) {
        super(context, view, imageFactory, tool);
        glassEffect = new GlassEffect(imageFactory);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        // TODO: add dialog window with spread
        glassEffect.setSpread(20);
        getContext().setTransformation(glassEffect);
    }
}
