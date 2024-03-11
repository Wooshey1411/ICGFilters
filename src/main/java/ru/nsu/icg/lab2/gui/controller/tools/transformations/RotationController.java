package ru.nsu.icg.lab2.gui.controller.tools.transformations;

import ru.nsu.icg.lab2.gui.controller.TextFieldSliderController;
import ru.nsu.icg.lab2.gui.model.Context;
import ru.nsu.icg.lab2.gui.model.Utils;
import ru.nsu.icg.lab2.gui.model.View;
import ru.nsu.icg.lab2.model.ImageFactory;
import ru.nsu.icg.lab2.model.transformations.Rotation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RotationController implements ActionListener {
    private static final int SLIDER_MIN_VALUE = -180;
    private static final int SLIDER_MAX_VALUE = 180;
    private static final String INCORRECT_VALUE_MESSAGE = String.format(
            "Incorrect rotation! It must be between %d and %d",
            SLIDER_MIN_VALUE,
            SLIDER_MAX_VALUE
    );

    private final Context context;
    private final View view;
    private final Rotation rotation;
    private final TextFieldSliderController dialogWindowController;
    private final JPanel panel;

    public RotationController(Context context, View view, ImageFactory imageFactory) {
        this.context = context;
        this.view = view;
        this.rotation = new Rotation(imageFactory);

        final JTextField textField = new JTextField();
        final JSlider slider = new JSlider(SLIDER_MIN_VALUE, SLIDER_MAX_VALUE);
        this.panel = Utils.createDialogInputPanel(textField, slider);
        this.dialogWindowController = new TextFieldSliderController(
                textField,
                slider,
                SLIDER_MIN_VALUE,
                SLIDER_MAX_VALUE,
                Double::valueOf,
                Double::parseDouble,
                Double::intValue,
                (x) -> String.valueOf(Math.round(x))
        );
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        while (true) {
            dialogWindowController.reset(rotation.getDegrees());

            final boolean ok = view.showConfirmationDialog("Rotation", panel);
            if (!ok) {
                return;
            }

            if (dialogWindowController.hasValue()) {
                rotation.setDegrees(dialogWindowController.getIntValue());
            }
            if (!dialogWindowController.hasError()) {
                break;
            }
            view.showError(INCORRECT_VALUE_MESSAGE);
        }

        context.setTransformation(rotation);
    }
}
