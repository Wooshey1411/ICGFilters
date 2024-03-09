package ru.nsu.icg.lab2.gui.controller.tools.transformations;

import ru.nsu.icg.lab2.gui.view.context.Context;
import ru.nsu.icg.lab2.model.transformations.GammaCorrection;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GammaCorrectionController implements ActionListener {
    private final Context context;
    private final GammaCorrection gammaCorrection;

    public GammaCorrectionController(Context context) {
        this.context = context;
        this.gammaCorrection = new GammaCorrection();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }
}
