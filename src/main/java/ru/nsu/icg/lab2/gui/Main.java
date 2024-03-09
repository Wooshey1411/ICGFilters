package ru.nsu.icg.lab2.gui;

import ru.nsu.icg.lab2.gui.view.ViewImpl;
import ru.nsu.icg.lab2.gui.view.context.ViewMode;
import ru.nsu.icg.lab2.model.config.JSONViewConfigParser;
import ru.nsu.icg.lab2.model.config.ViewConfig;
import ru.nsu.icg.lab2.model.config.ViewConfigParser;
import ru.nsu.icg.lab2.gui.view.imageio.ImageReader;
import ru.nsu.icg.lab2.gui.view.imageio.ImageReaderImpl;
import ru.nsu.icg.lab2.gui.view.imageio.ImageWriter;
import ru.nsu.icg.lab2.gui.view.imageio.ImageWriterImpl;
import ru.nsu.icg.lab2.gui.view.context.Context;

public class Main {
    public static void main(String[] args) {
        try {
            final Context context = new Context(ViewMode.ON_WINDOW_SIZE);

            final ViewConfigParser viewConfigParser = new JSONViewConfigParser("view_config.json");
            final ViewConfig viewConfig = viewConfigParser.parse();

            final ImageReader imageReader = new ImageReaderImpl();
            final ImageWriter imageWriter = new ImageWriterImpl();

            new ViewImpl(viewConfig, context, imageReader, imageWriter);
        } catch (Exception exception) {
            System.err.println(exception.getLocalizedMessage());
        }
    }
}
