package ru.nsu.icg.lab2.gui.controller.tools;

import ru.nsu.icg.lab2.gui.controller.ToolController;
import ru.nsu.icg.lab2.gui.model.*;
import ru.nsu.icg.lab2.model.ImageFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

public class WindowSizeController extends ToolController {
    private final JPanel optionsPanel;
    private final JComboBox<String> comboBox;
    private final HashMap<String, InterpolationMethod> methodHashMap;

    public WindowSizeController(Context context, View view, ImageFactory imageFactory) {
        super(context, view, imageFactory);

        methodHashMap = new HashMap<>(Map.of(
                "Bilinear",InterpolationMethod.BILINEAR,
                "Bicubic",InterpolationMethod.BICUBIC,
                "Nearest neighbor",InterpolationMethod.NEAREST_NEIGHBOR));
        comboBox = new JComboBox<>(methodHashMap.keySet().toArray(new String[0]));
        optionsPanel = Utils.createSimpleComboDialogInputPanel(comboBox,"Interpolation method:",1);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        comboBox.setSelectedItem("Bilinear");
        final boolean ok = getView().showConfirmationDialog("Interpolation method", optionsPanel);
        if(!ok){
            return;
        }

        final Context context = getContext();
        context.setInterpolationMethod(methodHashMap.get((String)comboBox.getSelectedItem()));
        context.setViewMode(ViewMode.ON_WINDOW_SIZE);
    }
}
