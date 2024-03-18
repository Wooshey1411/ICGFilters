package ru.nsu.icg.lab2.gui.controller.tools;

import ru.nsu.icg.lab2.gui.controller.ToolController;
import ru.nsu.icg.lab2.gui.model.BufferedImageImpl;
import ru.nsu.icg.lab2.gui.model.Context;
import ru.nsu.icg.lab2.gui.model.View;
import ru.nsu.icg.lab2.model.ImageFactory;

import java.awt.event.ActionEvent;

public class UndoController extends ToolController {
    public UndoController(Context context, View view, ImageFactory imageFactory) {
        super(context, view, imageFactory);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        // TODO: Hip-Hop icon

        final Context context = getContext();
        final BufferedImageImpl processedImage = context.getProcessedImage();

        if (processedImage == null) {
            return;
        }

        final BufferedImageImpl newCurrentImage = context.getCurrentImage() == context.getProcessedImage()
                ? context.getOriginalImage()
                : processedImage;

        context.setCurrentImage(newCurrentImage);
    }
}
