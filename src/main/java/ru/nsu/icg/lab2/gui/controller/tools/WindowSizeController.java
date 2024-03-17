package ru.nsu.icg.lab2.gui.controller.tools;

import ru.nsu.icg.lab2.gui.model.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class WindowSizeController implements ActionListener {

    private final Context context;
    private final View view;

    private final JPanel optionsPanel;
    private final JComboBox<String> comboBox;
    private final HashMap<String, InterpolationMethod> methodHashMap;

    public WindowSizeController(Context context, View view){
        this.context = context;
        this.view = view;
        this.methodHashMap = new HashMap<>(Map.of(
                "Bilinear",InterpolationMethod.BILINEAR,
                "Bicubic",InterpolationMethod.BICUBIC,
                "Nearest neighbor",InterpolationMethod.NEAREST_NEIGHBOR));
        this.comboBox = new JComboBox<>(methodHashMap.keySet().toArray(new String[0]));
        this.optionsPanel = Utils.createSimpleComboDialogInputPanel(comboBox,"Interpolation method",1);
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        comboBox.setSelectedItem("Bilinear");
        final boolean ok = view.showConfirmationDialog("Interpolation method",optionsPanel);
        if(!ok){
            return;
        }

        context.setInterpolationMethod(methodHashMap.get((String)comboBox.getSelectedItem()));
        context.setViewMode(ViewMode.ON_WINDOW_SIZE);
    }
}
