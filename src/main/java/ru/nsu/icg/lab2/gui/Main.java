package ru.nsu.icg.lab2.gui;

import ru.nsu.icg.lab2.gui.view.*;
import ru.nsu.icg.lab2.gui.view.view.ViewImpl;
import ru.nsu.icg.lab2.model.config.JSONViewConfigParser;
import ru.nsu.icg.lab2.model.ViewConfig;
import ru.nsu.icg.lab2.model.ViewConfigParser;
import ru.nsu.icg.lab2.gui.view.imageio.ImageReaderImpl;
import ru.nsu.icg.lab2.gui.view.imageio.ImageWriterImpl;
import ru.nsu.icg.lab2.gui.view.context.ContextImpl;

public class Main {
    public static void main(String[] args) {
        try {
            final ContextImpl context = new ContextImpl(ViewMode.ON_WINDOW_SIZE);

            final ViewConfigParser viewConfigParser = new JSONViewConfigParser("view_config.json");
            final ViewConfig viewConfig = viewConfigParser.parse();

            final ImageReader imageReader = new ImageReaderImpl();
            final ImageWriter imageWriter = new ImageWriterImpl();

            final ViewImpl view = new ViewImpl(viewConfig, context, imageReader, imageWriter);

            context.addListener(view);
            final BufferedImageImpl startImage = new BufferedImageImpl(Utils.createBlankImage(2000, 800));
            context.setOriginalImage(startImage);
            context.setImage(Utils.deepCopy(startImage));

            view.show();
        } catch (Exception exception) {
            System.err.println(exception.getLocalizedMessage());
        }
    }
}
