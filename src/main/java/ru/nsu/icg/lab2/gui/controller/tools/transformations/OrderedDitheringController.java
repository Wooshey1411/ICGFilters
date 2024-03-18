package ru.nsu.icg.lab2.gui.controller.tools.transformations;

import ru.nsu.icg.lab2.gui.controller.TextFieldSliderController;
import ru.nsu.icg.lab2.gui.model.Context;
import ru.nsu.icg.lab2.gui.model.Utils;
import ru.nsu.icg.lab2.gui.model.View;
import ru.nsu.icg.lab2.model.ImageFactory;
import ru.nsu.icg.lab2.model.transformations.OrderedDithering;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OrderedDitheringController implements ActionListener {

    private static final int COLOR_CHANNEL_MIN_VALUE = 2;
    private static final int COLOR_CHANNEL_MAX_VALUE = 128;
    private static final int SLIDER_COLOR_CHANNEL_MIN_VALUE = 2;
    private static final int SLIDER_COLOR_CHANNEL_MAX_VALUE = 128;
    private final Context context;
    private final View view;
    private final OrderedDithering orderedDithering;

    private final JPanel optionsPanel;
    private final TextFieldSliderController redColorController;
    private final TextFieldSliderController blueColorController;
    private final TextFieldSliderController greenColorController;

    public OrderedDitheringController(Context context, View view, ImageFactory imageFactory) {
        this.context = context;
        this.view = view;
        this.orderedDithering = new OrderedDithering(imageFactory);
        JSlider redSlider = new JSlider(SLIDER_COLOR_CHANNEL_MIN_VALUE, SLIDER_COLOR_CHANNEL_MAX_VALUE);
        JSlider greenSlider = new JSlider(SLIDER_COLOR_CHANNEL_MIN_VALUE, SLIDER_COLOR_CHANNEL_MAX_VALUE);
        JSlider blueSlider = new JSlider(SLIDER_COLOR_CHANNEL_MIN_VALUE, SLIDER_COLOR_CHANNEL_MAX_VALUE);
        JTextField redTextField = new JTextField();
        JTextField greenTextField = new JTextField();
        JTextField blueTextField = new JTextField();
        this.optionsPanel = Utils.createSimpleSliderDialogInputPanel(redTextField, redSlider, "Red quantization number:", 1);
        Utils.addSyncSliderTo3ColsPanel(this.optionsPanel, greenTextField, greenSlider, "Green quantization number:", 1);
        Utils.addSyncSliderTo3ColsPanel(this.optionsPanel, blueTextField, blueSlider, "Blue quantization number:", 1);
        this.redColorController = makeColorController(redTextField,redSlider);
        this.greenColorController = makeColorController(greenTextField,greenSlider);
        this.blueColorController = makeColorController(blueTextField,blueSlider);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        while (true) {
            redColorController.reset(orderedDithering.getRedK());
            blueColorController.reset(orderedDithering.getBlueK());
            greenColorController.reset(orderedDithering.getGreenK());
            final boolean ok = view.showConfirmationDialog("Ordered dithering", optionsPanel);
            if (!ok) {
                return;
            }

            if (redColorController.hasError()) {
                view.showError(String.format(
                        "Incorrect red quantization number! It must be between %d and %d",
                        COLOR_CHANNEL_MIN_VALUE,
                        COLOR_CHANNEL_MAX_VALUE
                ));
                continue;
            }
            orderedDithering.setRedK(redColorController.getIntValue());

            if (greenColorController.hasError()) {
                view.showError(String.format(
                        "Incorrect green quantization number! It must be between %d and %d",
                        COLOR_CHANNEL_MIN_VALUE,
                        COLOR_CHANNEL_MAX_VALUE
                ));
                continue;
            }
            orderedDithering.setGreenK(greenColorController.getIntValue());

            if (blueColorController.hasError()) {
                view.showError(String.format(
                        "Incorrect blue quantization number! It must be between %d and %d",
                        COLOR_CHANNEL_MIN_VALUE,
                        COLOR_CHANNEL_MAX_VALUE
                ));
                continue;
            }
            orderedDithering.setBlueK(blueColorController.getIntValue());

            break;
        }


        context.setTransformation(orderedDithering);
    }

    private TextFieldSliderController makeColorController(JTextField textField, JSlider slider) {
        return new TextFieldSliderController(
                textField,
                slider,
                COLOR_CHANNEL_MIN_VALUE,
                COLOR_CHANNEL_MAX_VALUE,
                Double::valueOf,
                Double::parseDouble,
                Double::intValue,
                aDouble -> String.valueOf(Math.round(aDouble)));
    }
}
