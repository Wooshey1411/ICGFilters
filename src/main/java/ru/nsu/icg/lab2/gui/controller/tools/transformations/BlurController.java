package ru.nsu.icg.lab2.gui.controller.tools.transformations;

import ru.nsu.icg.lab2.gui.model.Context;
import ru.nsu.icg.lab2.gui.model.View;
import ru.nsu.icg.lab2.model.ImageFactory;
import ru.nsu.icg.lab2.model.transformations.Blur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BlurController implements ActionListener {
    private final Context context;
    private final View view;
    private final Blur blur;

    public BlurController(Context context, View view, ImageFactory imageFactory) {
        this.context = context;
        this.view = view;
        this.blur = new Blur(imageFactory);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        context.setTransformation(blur);
    }
}
