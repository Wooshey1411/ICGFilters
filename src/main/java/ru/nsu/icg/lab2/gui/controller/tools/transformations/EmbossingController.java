package ru.nsu.icg.lab2.gui.controller.tools.transformations;

import ru.nsu.icg.lab2.gui.model.Context;
import ru.nsu.icg.lab2.model.ImageFactory;
import ru.nsu.icg.lab2.model.transformations.Embossing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmbossingController implements ActionListener {
    private final Context context;
    private final Embossing embossing;

    public EmbossingController(Context context, ImageFactory imageFactory) {
        this.context = context;
        this.embossing = new Embossing(imageFactory);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        context.setTransformation(embossing);
    }
}
