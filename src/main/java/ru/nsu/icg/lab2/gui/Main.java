package ru.nsu.icg.lab2.gui;

import ru.nsu.icg.lab2.gui.controller.ButtonsController;
import ru.nsu.icg.lab2.gui.controller.FilesActionsController;
import ru.nsu.icg.lab2.gui.controller.WindowController;
import ru.nsu.icg.lab2.gui.view.View;
import ru.nsu.icg.lab2.gui.view.config.JSONViewConfigParser;
import ru.nsu.icg.lab2.gui.view.config.ViewConfig;
import ru.nsu.icg.lab2.gui.view.config.ViewConfigParser;
import ru.nsu.icg.lab2.gui.view.icons.IconsSupplier;
import ru.nsu.icg.lab2.gui.view.icons.IconsSupplierImpl;
import ru.nsu.icg.lab2.model.context.Context;
import ru.nsu.icg.lab2.model.image.ImageReader;
import ru.nsu.icg.lab2.model.image.ImageReaderImpl;
import ru.nsu.icg.lab2.model.image.ImageWriter;
import ru.nsu.icg.lab2.model.image.ImageWriterImpl;

public class Main {
    public static void main(String[] args) {
        try {
            final Context context = new Context();

            final ViewConfigParser viewConfigParser = new JSONViewConfigParser("view_config.json");
            final ViewConfig viewConfig = viewConfigParser.parse();

            final ImageReader imageReader = new ImageReaderImpl();
            final ImageWriter imageWriter = new ImageWriterImpl();

            final ButtonsController buttonsController = new ButtonsController(context);
            final WindowController windowController = new WindowController(context);
            final FilesActionsController filesActionsController = new FilesActionsController(
                    context,
                    imageReader,
                    imageWriter
            );

            final IconsSupplier iconsSupplier = new IconsSupplierImpl();

            final View view = new View(
                    viewConfig,
                    iconsSupplier,
                    buttonsController,
                    windowController,
                    filesActionsController,
                    imageReader.getSupportedFormats(),
                    imageWriter.getSupportedFormats()
            );

            context.addListener(view);
            view.show();
        } catch (Exception ex) {
            // TODO: сделать нормальную обработку исключений
            System.err.println(ex.getLocalizedMessage());
        }
    }
}
