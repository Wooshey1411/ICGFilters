package ru.nsu.icg.lab2.gui.controller.tools;

import ru.nsu.icg.lab2.gui.common.Utils;
import ru.nsu.icg.lab2.gui.controller.TextFieldSliderController;
import ru.nsu.icg.lab2.gui.controller.ToolController;
import ru.nsu.icg.lab2.gui.common.context.Context;
import ru.nsu.icg.lab2.gui.common.View;
import ru.nsu.icg.lab2.model.ImageFactory;
import ru.nsu.icg.lab2.model.dto.Tool;
import ru.nsu.icg.lab2.model.transformations.OrderedDithering;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class OrderedDitheringController extends ToolController {
    private static final int COLOR_CHANNEL_MIN_VALUE = 2;
    private static final int COLOR_CHANNEL_MAX_VALUE = 128;
    private static final int SLIDER_COLOR_CHANNEL_MIN_VALUE = 2;
    private static final int SLIDER_COLOR_CHANNEL_MAX_VALUE = 128;

    private final OrderedDithering orderedDithering;
    private final JPanel optionsPanel;
    private final TextFieldSliderController redColorController;
    private final TextFieldSliderController blueColorController;
    private final TextFieldSliderController greenColorController;

    public OrderedDitheringController(Context context, View view, ImageFactory imageFactory, Tool tool) {
        super(context, view, imageFactory, tool);
        orderedDithering = new OrderedDithering(imageFactory);
        JSlider redSlider = new JSlider(SLIDER_COLOR_CHANNEL_MIN_VALUE, SLIDER_COLOR_CHANNEL_MAX_VALUE);
        JSlider greenSlider = new JSlider(SLIDER_COLOR_CHANNEL_MIN_VALUE, SLIDER_COLOR_CHANNEL_MAX_VALUE);
        JSlider blueSlider = new JSlider(SLIDER_COLOR_CHANNEL_MIN_VALUE, SLIDER_COLOR_CHANNEL_MAX_VALUE);
        JTextField redTextField = new JTextField();
        JTextField greenTextField = new JTextField();
        JTextField blueTextField = new JTextField();
        optionsPanel = Utils.createSimpleSliderDialogInputPanel(redTextField, redSlider, "Red quantization number:", 1);
        Utils.addSyncSliderTo3ColsPanel(optionsPanel, greenTextField, greenSlider, "Green quantization number:", 1);
        Utils.addSyncSliderTo3ColsPanel(optionsPanel, blueTextField, blueSlider, "Blue quantization number:", 1);
        redColorController = makeColorController(redTextField,redSlider);
        greenColorController = makeColorController(greenTextField,greenSlider);
        blueColorController = makeColorController(blueTextField,blueSlider);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        final View view = getView();

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

        getContext().setTransformation(orderedDithering);
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
