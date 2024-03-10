package ru.nsu.icg.lab2.gui.controller.tools.transformations;

import ru.nsu.icg.lab2.gui.view.Context;
import ru.nsu.icg.lab2.model.transformations.BlackAndWhite;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BlackAndWhiteController implements ActionListener {
    private final Context context;
    private final BlackAndWhite blackAndWhite;

    public BlackAndWhiteController(Context context) {
        this.context = context;
        this.blackAndWhite = new BlackAndWhite();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        context.setTransformation(blackAndWhite);
    }
}
