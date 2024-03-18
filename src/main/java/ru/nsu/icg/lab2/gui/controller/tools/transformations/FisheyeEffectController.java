package ru.nsu.icg.lab2.gui.controller.tools.transformations;

import ru.nsu.icg.lab2.gui.model.Context;
import ru.nsu.icg.lab2.model.ImageFactory;
import ru.nsu.icg.lab2.model.transformations.FisheyeEffect;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FisheyeEffectController implements ActionListener {
    private final Context context;
    private final FisheyeEffect fisheyeEffect;

    public FisheyeEffectController(Context context, ImageFactory imageFactory) {
        this.context = context;
        this.fisheyeEffect = new FisheyeEffect(imageFactory);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        context.setTransformation(fisheyeEffect);
    }
}
