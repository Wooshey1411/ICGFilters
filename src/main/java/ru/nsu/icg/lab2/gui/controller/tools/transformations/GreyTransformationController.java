package ru.nsu.icg.lab2.gui.controller.tools.transformations;

import ru.nsu.icg.lab2.gui.view.context.Context;
import ru.nsu.icg.lab2.model.transformations.GreyTransformation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GreyTransformationController implements ActionListener {
    private final Context context;
    private final GreyTransformation greyTransformation;

    public GreyTransformationController(Context context) {
        this.context = context;
        this.greyTransformation = new GreyTransformation();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        context.setTransformation(greyTransformation);
    }
}
