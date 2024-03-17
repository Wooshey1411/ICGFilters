package ru.nsu.icg.lab2.gui.controller.tools.transformations;

import ru.nsu.icg.lab2.gui.controller.TextFieldSliderController;
import ru.nsu.icg.lab2.gui.model.Context;
import ru.nsu.icg.lab2.gui.model.Utils;
import ru.nsu.icg.lab2.gui.model.View;
import ru.nsu.icg.lab2.model.ImageFactory;
import ru.nsu.icg.lab2.model.transformations.EdgeDetection;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class EdgeDetectionController implements ActionListener {

    private static final int SLIDER_MIN_VALUE = 0;
    private static final int SLIDER_MAX_VALUE = 1000;
    private static final String INCORRECT_VALUE_MESSAGE = String.format(
            "Incorrect rotation! It must be between %d and %d",
            SLIDER_MIN_VALUE,
            SLIDER_MAX_VALUE
    );
    private final Context context;
    private final EdgeDetection edgeDetection;
    private final View view;
    private final JPanel optionsSetWindow;
    private final TextFieldSliderController textFieldSliderController;
    private final JComboBox<String> algComboBox;

    public EdgeDetectionController(Context context,View view, ImageFactory imageFactory) {
        this.context = context;
        this.view = view;
        this.edgeDetection = new EdgeDetection(imageFactory);
        JTextField textField = new JTextField();
        JSlider slider = new JSlider(SLIDER_MIN_VALUE,SLIDER_MAX_VALUE);
        this.optionsSetWindow = Utils.createSimpleDialogInputPanel(textField,slider,"Binarization:",1);
        algComboBox = new JComboBox<>(edgeDetection.getTypesAsString());
        Utils.addComboBoxTo3ColsPanel(this.optionsSetWindow, algComboBox,"Algorithm:", 1);
        this.textFieldSliderController = new TextFieldSliderController(
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
            textFieldSliderController.reset(edgeDetection.getBrightnessBorder());
            algComboBox.setSelectedItem(edgeDetection.getTypeAsString());

            final boolean ok = view.showConfirmationDialog("Edge Detection", optionsSetWindow);
            if (!ok) {
                return;
            }

            if (textFieldSliderController.hasValue()) {
                edgeDetection.setBrightnessBorder(textFieldSliderController.getIntValue());
            }
            if (!textFieldSliderController.hasError()) {
                edgeDetection.setTypeFromString((String) Objects.requireNonNull(algComboBox.getSelectedItem()));
                break;
            }
            view.showError(INCORRECT_VALUE_MESSAGE);
        }

        context.setTransformation(edgeDetection);
    }
}
