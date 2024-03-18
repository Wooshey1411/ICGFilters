package ru.nsu.icg.lab2.gui.controller.tools.transformations;

import ru.nsu.icg.lab2.gui.model.Context;
import ru.nsu.icg.lab2.model.ImageFactory;
import ru.nsu.icg.lab2.model.transformations.GlassEffect;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GlassEffectController implements ActionListener {
    private final Context context;
    private final GlassEffect glassEffect;

    public GlassEffectController(Context context, ImageFactory imageFactory) {
        this.context = context;
        this.glassEffect = new GlassEffect(imageFactory);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        // TODO: add dialog window with spread
        glassEffect.setSpread(20);
        context.setTransformation(glassEffect);
    }
}
