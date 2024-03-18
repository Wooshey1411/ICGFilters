package ru.nsu.icg.lab2.gui.controller.tools;

import ru.nsu.icg.lab2.gui.controller.ToolController;
import ru.nsu.icg.lab2.gui.model.Context;
import ru.nsu.icg.lab2.gui.model.View;
import ru.nsu.icg.lab2.model.ImageFactory;
import ru.nsu.icg.lab2.model.transformations.GlassEffect;

import java.awt.event.ActionEvent;

public class GlassEffectController extends ToolController {
    private final GlassEffect glassEffect;

    public GlassEffectController(Context context, View view, ImageFactory imageFactory) {
        super(context, view, imageFactory);
        glassEffect = new GlassEffect(imageFactory);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        // TODO: add dialog window with spread
        glassEffect.setSpread(20);
        getContext().setTransformation(glassEffect);
    }
}
