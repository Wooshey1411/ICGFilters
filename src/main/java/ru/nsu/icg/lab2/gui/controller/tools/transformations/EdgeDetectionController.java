package ru.nsu.icg.lab2.gui.controller.tools.transformations;

import ru.nsu.icg.lab2.gui.model.Context;
import ru.nsu.icg.lab2.model.transformations.EdgeDetection;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EdgeDetectionController implements ActionListener {
    private final Context context;
    private final EdgeDetection edgeDetection;

    public EdgeDetectionController(Context context) {
        this.context = context;
        this.edgeDetection = new EdgeDetection();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        context.setTransformation(edgeDetection);
    }
}
