package ru.nsu.icg.lab2.gui.controller.tools.transformations;

import org.decimal4j.util.DoubleRounder;
import ru.nsu.icg.lab2.gui.controller.TextFieldSliderController;
import ru.nsu.icg.lab2.gui.model.Context;
import ru.nsu.icg.lab2.gui.model.Utils;
import ru.nsu.icg.lab2.gui.model.View;
import ru.nsu.icg.lab2.model.ImageFactory;
import ru.nsu.icg.lab2.model.transformations.GammaCorrection;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GammaCorrectionController implements ActionListener {
    private static final int SLIDER_MIN_VALUE = 0;
    private static final int SLIDER_MAX_VALUE = 1000;
    private static final int SLIDER_MAX_VALUE_HALF = SLIDER_MAX_VALUE / 2;
    private static final double GAMMA_MIN_VALUE = 0.1;
    private static final double GAMMA_MAX_VALUE = 10;
    private static final double K = Math.log(GAMMA_MAX_VALUE) / SLIDER_MAX_VALUE_HALF;
    private static final String INCORRECT_VALUE_MESSAGE = String.format(
            "Incorrect gamma! It must be between %.1f and %.1f",
            GAMMA_MIN_VALUE,
            GAMMA_MAX_VALUE
    );
    private final Context context;
    private final View view;
    private final GammaCorrection gammaCorrection;
    private final DoubleRounder rounder;

    private final TextFieldSliderController dialogWindowController;
    private final JPanel panel;

    public GammaCorrectionController(Context context, View view, ImageFactory imageFactory) {
        this.context = context;
        this.view = view;
        this.gammaCorrection = new GammaCorrection(imageFactory);
        this.rounder = new DoubleRounder(1);

        final JTextField textField = new JTextField();
        final JSlider slider = new JSlider(SLIDER_MIN_VALUE, SLIDER_MAX_VALUE);
        this.panel = Utils.createDialogInputPanel(textField, slider);
        this.dialogWindowController = new TextFieldSliderController(
                textField,
                slider,
                GAMMA_MIN_VALUE,
                GAMMA_MAX_VALUE,
                this::getGammaFromSliderValue,
                this::getGammaFromTextField,
                this::getSliderValueFromGamma,
                Object::toString
        );
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        while (true) {
            dialogWindowController.reset(gammaCorrection.getGamma());

            final boolean ok = view.showConfirmationDialog("Gamma", panel);
            if (!ok) {
                return;
            }

            if (dialogWindowController.hasValue()) {
                gammaCorrection.setGamma(dialogWindowController.getValue());
            }
            if (!dialogWindowController.hasError()) {
                break;
            }
            view.showError(INCORRECT_VALUE_MESSAGE);
        }

        context.setTransformation(gammaCorrection);
    }

    private double getGammaFromSliderValue(int sliderValue) {
        return rounder.round(Math.exp(K * (sliderValue - SLIDER_MAX_VALUE_HALF)));
    }

    private int getSliderValueFromGamma(double gamma) {
        return (int) Math.round(Math.log(gamma) / K + SLIDER_MAX_VALUE_HALF);
    }

    private double getGammaFromTextField(String text) {
        return rounder.round(Double.parseDouble(text));
    }
}
