package ru.nsu.icg.lab2.gui.controller.tools.transformations;

import org.decimal4j.util.DoubleRounder;
import ru.nsu.icg.lab2.gui.controller.TextFieldSliderController;
import ru.nsu.icg.lab2.gui.model.Context;
import ru.nsu.icg.lab2.gui.model.Utils;
import ru.nsu.icg.lab2.gui.model.View;
import ru.nsu.icg.lab2.model.ImageFactory;
import ru.nsu.icg.lab2.model.transformations.WaveFilter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class WaveFilterController implements ActionListener {

    private static final int AMP_SLIDER_MIN_VALUE = 0;
    private static final int AMP_SLIDER_MAX_VALUE = 100;
    private static final int FREQ_MIN_VALUE = 0;

    private static final int FREQ_BOUND_VALUE = 1;
    private static final int FREQ_MAX_VALUE = 10;

    private static final int FREQ_SLIDER_MIN_VALUE = 0;

    private static final int FREQ_SLIDER_BOUND_VALUE = 1000;
    private static final int FREQ_SLIDER_MAX_VALUE = 1900;

    private static final double step = (FREQ_MAX_VALUE-FREQ_BOUND_VALUE)*1.0/(FREQ_SLIDER_MAX_VALUE-FREQ_SLIDER_BOUND_VALUE);

    private final Context context;
    private final WaveFilter waveFilter;
    private final View view;
    private final JPanel optionsSetWindow;
    private final TextFieldSliderController ampXTextFieldSliderController;
    private final TextFieldSliderController ampYTextFieldSliderController;
    private final TextFieldSliderController freqXTextFieldSliderController;
    private final TextFieldSliderController freqYTextFieldSliderController;
    private final JComboBox<String> orderComboBox;

    private final DoubleRounder doubleRounder3;
    private final DoubleRounder doubleRounder2;

    public WaveFilterController(Context context, View view, ImageFactory imageFactory) {
        this.context = context;
        this.view = view;
        this.waveFilter = new WaveFilter(imageFactory);
        this.orderComboBox = new JComboBox<>(waveFilter.getTypesAsString());
        JSlider ampXSlider = new JSlider(AMP_SLIDER_MIN_VALUE,AMP_SLIDER_MAX_VALUE);
        JSlider ampYSlider = new JSlider(AMP_SLIDER_MIN_VALUE,AMP_SLIDER_MAX_VALUE);
        JSlider freqXSlider = new JSlider(FREQ_SLIDER_MIN_VALUE,FREQ_SLIDER_MAX_VALUE);
        JSlider freqYSlider = new JSlider(FREQ_SLIDER_MIN_VALUE,FREQ_SLIDER_MAX_VALUE);
        JTextField ampXTextField = new JTextField();
        JTextField ampYTextField = new JTextField();
        JTextField freqXTextField = new JTextField();
        JTextField freqYTextField = new JTextField();
        this.optionsSetWindow = Utils.createSimpleDialogInputPanel(ampXTextField,ampXSlider,"X amplitude",1);
        Utils.addSyncSliderTo3ColsPanel(this.optionsSetWindow,freqXTextField,freqXSlider,"X frequency",1);
        Utils.addSyncSliderTo3ColsPanel(this.optionsSetWindow,ampYTextField,ampYSlider,"Y amplitude",1);
        Utils.addSyncSliderTo3ColsPanel(this.optionsSetWindow,freqYTextField,freqYSlider,"Y frequency",1);
        Utils.addComboBoxTo3ColsPanel(this.optionsSetWindow,orderComboBox,"Order",1);
        this.ampXTextFieldSliderController = new TextFieldSliderController(
                ampXTextField,
                ampXSlider,
                AMP_SLIDER_MIN_VALUE,
                AMP_SLIDER_MAX_VALUE,
                Double::valueOf,
                Double::parseDouble,
                Double::intValue,
                (x) -> String.valueOf(Math.round(x))
        );
        this.ampYTextFieldSliderController = new TextFieldSliderController(
                ampYTextField,
                ampYSlider,
                AMP_SLIDER_MIN_VALUE,
                AMP_SLIDER_MAX_VALUE,
                Double::valueOf,
                Double::parseDouble,
                Double::intValue,
                (x) -> String.valueOf(Math.round(x))
        );
        doubleRounder2 = new DoubleRounder(2);
        doubleRounder3 = new DoubleRounder(3);
        System.out.println(step);
        this.freqXTextFieldSliderController = new TextFieldSliderController( // 0-1 -> 0.001 ; 1-10 -> 0.01
                freqXTextField,
                freqXSlider,
                FREQ_MIN_VALUE,
                FREQ_MAX_VALUE,
                this::getFromSliderValue,
                this::getFromTextField,
                this::getSliderValueFrom,
                this::toTextFieldValue
        );
        this.freqYTextFieldSliderController = new TextFieldSliderController( // 0-1 -> 0.001 ; 1-10 -> 0.01
                freqYTextField,
                freqYSlider,
                FREQ_MIN_VALUE,
                FREQ_MAX_VALUE,
                this::getFromSliderValue,
                this::getFromTextField,
                this::getSliderValueFrom,
                this::toTextFieldValue
        );
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        while (true) {
            ampXTextFieldSliderController.reset(waveFilter.getAmpX());
            ampYTextFieldSliderController.reset(waveFilter.getAmpY());
            freqXTextFieldSliderController.reset(waveFilter.getFreqX());
            freqYTextFieldSliderController.reset(waveFilter.getFreqY());
            orderComboBox.setSelectedItem(waveFilter.getTypeAsString());

            final boolean ok = view.showConfirmationDialog("Wave", optionsSetWindow);
            if (!ok) {
                return;
            }

            if (ampXTextFieldSliderController.hasValue()) {
                waveFilter.setAmpX(ampXTextFieldSliderController.getIntValue());
            }

            if (ampXTextFieldSliderController.hasError()) {
                view.showError(String.format("Incorrect X amplitude! It must be between %d and %d",
                        AMP_SLIDER_MIN_VALUE,
                        AMP_SLIDER_MAX_VALUE));
                continue;
            }
            if (ampYTextFieldSliderController.hasValue()) {
                waveFilter.setAmpY(ampYTextFieldSliderController.getIntValue());
            }

            if (ampYTextFieldSliderController.hasError()) {
                view.showError(String.format("Incorrect Y amplitude! It must be between %d and %d",
                        AMP_SLIDER_MIN_VALUE,
                        AMP_SLIDER_MAX_VALUE));
                continue;
            }

            if (freqXTextFieldSliderController.hasValue()) {
                waveFilter.setFreqX(freqXTextFieldSliderController.getValue());
            }

            if (freqXTextFieldSliderController.hasError()) {
                view.showError(String.format("Incorrect X frequency! It must be between %d and %d",
                        FREQ_MIN_VALUE,
                        FREQ_MAX_VALUE));
                continue;
            }

            if (freqYTextFieldSliderController.hasValue()) {
                waveFilter.setFreqY(freqYTextFieldSliderController.getValue());
            }

            if (freqYTextFieldSliderController.hasError()) {
                view.showError(String.format("Incorrect Y frequency! It must be between %d and %d",
                        FREQ_MIN_VALUE,
                        FREQ_MAX_VALUE));
                continue;
            }

            break;
        }
        waveFilter.setTypeFromString((String) Objects.requireNonNull(orderComboBox.getSelectedItem()));



        context.setTransformation(waveFilter);
    }

    private double getFromSliderValue(int sliderValue) {
        if (sliderValue == 0) {
            return 0.0;
        }
        if (sliderValue < FREQ_SLIDER_BOUND_VALUE) {
            return doubleRounder3.round(sliderValue * 1.0 / FREQ_SLIDER_BOUND_VALUE);
        } else {
            return 1 + doubleRounder2.round(step * (sliderValue - FREQ_SLIDER_BOUND_VALUE));
        }
    }

    private int getSliderValueFrom(double aDouble) {
        if (aDouble < 1) {
            return (int) (doubleRounder3.round(aDouble) * FREQ_SLIDER_BOUND_VALUE);
        } else {
            return FREQ_SLIDER_BOUND_VALUE + (int) ((doubleRounder2.round(aDouble - 1) / step));
        }
    }

    private double getFromTextField(String text) {
        double val = Double.parseDouble(text);
        if (val < 1) {
            return doubleRounder3.round(val);
        } else {
            return doubleRounder2.round(val);
        }
    }
    private String toTextFieldValue(double aDouble){
            if(aDouble < 1){
                return doubleRounder3.round(aDouble) + "";
            } else {
                return doubleRounder2.round(aDouble) + "";
            }
    }
}
