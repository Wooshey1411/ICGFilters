package ru.nsu.icg.lab2.gui.controller.tools.transformations;

import ru.nsu.icg.lab2.gui.model.Context;
import ru.nsu.icg.lab2.model.ImageFactory;
import ru.nsu.icg.lab2.model.transformations.BlackAndWhite;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BlackAndWhiteController implements ActionListener {
    private final Context context;
    private final BlackAndWhite blackAndWhite;

    public BlackAndWhiteController(Context context, ImageFactory imageFactory) {
        this.context = context;
        this.blackAndWhite = new BlackAndWhite(imageFactory);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        context.setTransformation(blackAndWhite);
    }
}
