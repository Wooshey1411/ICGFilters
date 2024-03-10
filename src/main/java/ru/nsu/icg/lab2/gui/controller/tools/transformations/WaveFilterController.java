package ru.nsu.icg.lab2.gui.controller.tools.transformations;

import ru.nsu.icg.lab2.gui.view.context.Context;
import ru.nsu.icg.lab2.model.transformations.WaveFilter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WaveFilterController implements ActionListener {
    private final Context context;
    private final WaveFilter waveFilter;

    public WaveFilterController(Context context) {
        this.context = context;
        this.waveFilter = new WaveFilter();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        context.setTransformation(waveFilter);
    }
}
