package ru.nsu.icg.lab2.gui;

import com.formdev.flatlaf.intellijthemes.FlatArcDarkOrangeIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatArcOrangeIJTheme;
import ru.nsu.icg.lab2.gui.controller.ButtonsController;
import ru.nsu.icg.lab2.gui.controller.FilesActionsController;
import ru.nsu.icg.lab2.gui.controller.WindowController;
import ru.nsu.icg.lab2.gui.controller.WindowResizeController;
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

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            FlatArcDarkOrangeIJTheme.setup();
            //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            final Context context = new Context();

            final ViewConfigParser viewConfigParser = new JSONViewConfigParser("view_config.json");
            final ViewConfig viewConfig = viewConfigParser.parse();

            final ImageReader imageReader = new ImageReaderImpl();
            final ImageWriter imageWriter = new ImageWriterImpl();

            final ButtonsController buttonsController = new ButtonsController(context);
            final WindowController windowController = new WindowController(context);
            final WindowResizeController windowResizeController = new WindowResizeController(context);
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
                    imageWriter.getSupportedFormats(),
                    context,
                    windowResizeController
            );

            context.addListener(view);
            view.show();
        } catch (Exception exception) {
            System.err.println(exception.getLocalizedMessage());
        }
    }
}
