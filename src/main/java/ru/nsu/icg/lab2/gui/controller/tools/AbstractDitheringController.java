package ru.nsu.icg.lab2.gui.controller.tools;

import ru.nsu.icg.lab2.gui.common.Context;
import ru.nsu.icg.lab2.gui.common.ToolController;
import ru.nsu.icg.lab2.gui.common.Utils;
import ru.nsu.icg.lab2.gui.common.View;
import ru.nsu.icg.lab2.gui.controller.TextFieldSliderController;
import ru.nsu.icg.lab2.model.ImageFactory;
import ru.nsu.icg.lab2.model.dto.Tool;
import ru.nsu.icg.lab2.model.transformations.AbstractDithering;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractDitheringController extends ToolController {
    private static final int COLOR_CHANNEL_MIN_VALUE = 2;
    private static final int COLOR_CHANNEL_MAX_VALUE = 128;
    private static final int SLIDER_COLOR_CHANNEL_MIN_VALUE = 2;
    private static final int SLIDER_COLOR_CHANNEL_MAX_VALUE = 128;
    private final JPanel optionsPanel;
    private final TextFieldSliderController redColorController;
    private final TextFieldSliderController blueColorController;
    private final TextFieldSliderController greenColorController;
    private final JComboBox<String> creatorComboBox;
    private final HashMap<String,AbstractDithering.FilterCreator> stringFilterCreatorHashMap;

    protected AbstractDitheringController(Context context, View view, ImageFactory imageFactory, Tool tool) {
        super(context, view, imageFactory, tool);

        stringFilterCreatorHashMap = new HashMap<>(Map.of(
                "Sirotkin", AbstractDithering.FilterCreator.SIROTKIN,
                "Vorobev", AbstractDithering.FilterCreator.VOROBEV,
                "Kondrenko", AbstractDithering.FilterCreator.KONDRENKO
        ));

        JSlider redSlider = new JSlider(SLIDER_COLOR_CHANNEL_MIN_VALUE, SLIDER_COLOR_CHANNEL_MAX_VALUE);
        JSlider greenSlider = new JSlider(SLIDER_COLOR_CHANNEL_MIN_VALUE, SLIDER_COLOR_CHANNEL_MAX_VALUE);
        JSlider blueSlider = new JSlider(SLIDER_COLOR_CHANNEL_MIN_VALUE, SLIDER_COLOR_CHANNEL_MAX_VALUE);
        JTextField redTextField = new JTextField();
        JTextField greenTextField = new JTextField();
        JTextField blueTextField = new JTextField();
        optionsPanel = Utils.createSimpleSliderDialogInputPanel(redTextField, redSlider, "Red quantization number:", 1);
        Utils.addSyncSliderTo3ColsPanel(optionsPanel, greenTextField, greenSlider, "Green quantization number:", 1);
        Utils.addSyncSliderTo3ColsPanel(optionsPanel, blueTextField, blueSlider, "Blue quantization number:", 1);
        this.creatorComboBox = new JComboBox<>(stringFilterCreatorHashMap.keySet().toArray(new String[0]));
        Utils.addComboBoxTo3ColsPanel(optionsPanel, creatorComboBox, "Algorithm creator:", 1);
        redColorController = makeColorController(redTextField, redSlider);
        greenColorController = makeColorController(greenTextField, greenSlider);
        blueColorController = makeColorController(blueTextField, blueSlider);
    }

    protected abstract AbstractDithering getDithering();
    protected abstract String getAlgName();

    @Override
    public void actionPerformed(ActionEvent e) {
        final View view = getView();

        while (true) {
            redColorController.reset(getDithering().getRedK());
            blueColorController.reset(getDithering().getBlueK());
            greenColorController.reset(getDithering().getGreenK());

            String currentSelectedCreator = null;

            for (Map.Entry<String,AbstractDithering.FilterCreator> entry : stringFilterCreatorHashMap.entrySet()){
                if(entry.getValue() == getDithering().getCreator()){
                    currentSelectedCreator = entry.getKey();
                    break;
                }
            }

            if(currentSelectedCreator == null){
                throw new IllegalArgumentException("No such creator");
            }

            creatorComboBox.setSelectedItem(currentSelectedCreator);

            final boolean ok = view.showConfirmationDialog(getAlgName(), optionsPanel);
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
            getDithering().setRedK(redColorController.getIntValue());

            if (greenColorController.hasError()) {
                view.showError(String.format(
                        "Incorrect green quantization number! It must be between %d and %d",
                        COLOR_CHANNEL_MIN_VALUE,
                        COLOR_CHANNEL_MAX_VALUE
                ));
                continue;
            }
            getDithering().setGreenK(greenColorController.getIntValue());

            if (blueColorController.hasError()) {
                view.showError(String.format(
                        "Incorrect blue quantization number! It must be between %d and %d",
                        COLOR_CHANNEL_MIN_VALUE,
                        COLOR_CHANNEL_MAX_VALUE
                ));
                continue;
            }
            getDithering().setBlueK(blueColorController.getIntValue());
            break;
        }

        getDithering().setCreator(stringFilterCreatorHashMap.get((String)creatorComboBox.getSelectedItem()));

        getContext().setTransformation(getDithering());
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
