package ru.nsu.icg.lab2.gui.controller.tools.transformations;

import ru.nsu.icg.lab2.gui.view.context.ContextImpl;
import ru.nsu.icg.lab2.model.transformations.WaveFilter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WaveFilterController implements ActionListener {
    private final ContextImpl context;
    private final WaveFilter waveFilter;

    public WaveFilterController(ContextImpl context) {
        this.context = context;
        this.waveFilter = new WaveFilter();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }
}
