package ru.nsu.icg.lab2.gui.controller.tools;

import ru.nsu.icg.lab2.gui.controller.ToolController;
import ru.nsu.icg.lab2.gui.model.Context;
import ru.nsu.icg.lab2.gui.model.View;
import ru.nsu.icg.lab2.gui.model.ViewMode;
import ru.nsu.icg.lab2.model.ImageFactory;
import ru.nsu.icg.lab2.model.dto.Tool;

import java.awt.event.ActionEvent;

public class OneToOneController extends ToolController {
    public OneToOneController(Context context, View view, ImageFactory imageFactory, Tool tool) {
        super(context, view, imageFactory, tool);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        final Context context = getContext();

        if (context.getViewMode() != ViewMode.ONE_TO_ONE) {
            context.setViewMode(ViewMode.ONE_TO_ONE);
        }
    }
}
