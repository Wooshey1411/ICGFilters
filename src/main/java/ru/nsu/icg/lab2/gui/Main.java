package ru.nsu.icg.lab2.gui;

import ru.nsu.icg.lab2.gui.model.*;
import ru.nsu.icg.lab2.gui.view.ViewImpl;
import ru.nsu.icg.lab2.model.ViewConfig;
import ru.nsu.icg.lab2.model.ViewConfigParser;
import ru.nsu.icg.lab2.model.config.JSONViewConfigParser;

public class Main {
    public static void main(String[] args) {
        try {
            final Context context = new Context(ViewMode.ON_WINDOW_SIZE, new BufferedImageFactory());

            final ViewConfigParser viewConfigParser = new JSONViewConfigParser("view_config.json");
            final ViewConfig viewConfig = viewConfigParser.parse();

            final ImageReader imageReader = new ImageReader();
            final ImageWriter imageWriter = new ImageWriter();

            final ViewImpl view = new ViewImpl(viewConfig, context, imageReader, imageWriter);

            context.addListener(view);

            view.show();
        } catch (Exception exception) {
            System.err.println(exception.getLocalizedMessage());
        }
    }
}
