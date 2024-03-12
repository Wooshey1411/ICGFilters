package ru.nsu.icg.lab2.gui.controller.tools.transformations;

import ru.nsu.icg.lab2.gui.model.Context;
import ru.nsu.icg.lab2.model.ImageFactory;
import ru.nsu.icg.lab2.model.transformations.WaveFilter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WaveFilterController implements ActionListener {
    private final Context context;
    private final WaveFilter waveFilter;

    public WaveFilterController(Context context, ImageFactory imageFactory) {
        this.context = context;
        this.waveFilter = new WaveFilter(imageFactory);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        context.setTransformation(waveFilter);
    }
}
