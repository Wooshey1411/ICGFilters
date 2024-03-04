package ru.nsu.icg.lab2.gui;

import com.google.gson.Gson;
import ru.nsu.icg.lab2.gui.controller.ButtonsController;
import ru.nsu.icg.lab2.gui.controller.FilesActionsController;
import ru.nsu.icg.lab2.gui.controller.WindowController;
import ru.nsu.icg.lab2.gui.view.View;
import ru.nsu.icg.lab2.gui.view.ViewConfig;
import ru.nsu.icg.lab2.model.context.Context;
import ru.nsu.icg.lab2.model.image.ImageReader;
import ru.nsu.icg.lab2.model.image.ImageReaderImpl;
import ru.nsu.icg.lab2.model.image.ImageWriter;
import ru.nsu.icg.lab2.model.image.ImageWriterImpl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        try {
            final Context context = new Context();

            // TODO: вынести парсинг в отдельный класс (с интерфейсом)
            final ViewConfig viewConfig = new Gson().fromJson(new String(Objects.requireNonNull(Main.class.getResourceAsStream("viewConfig.json")).readAllBytes(), StandardCharsets.UTF_8), ViewConfig.class);

            final ImageReader imageReader = new ImageReaderImpl();
            final ImageWriter imageWriter = new ImageWriterImpl();

            final ButtonsController buttonsController = new ButtonsController(context);
            final WindowController windowController = new WindowController(context);
            final FilesActionsController filesActionsController = new FilesActionsController(
                    context,
                    imageReader,
                    imageWriter
            );

            final View view = new View(
                    viewConfig,
                    buttonsController,
                    windowController,
                    filesActionsController,
                    imageReader.getSupportedFormats(),
                    imageWriter.getSupportedFormats()
            );
            context.addListener(view);
            view.show();
        } catch (IOException ex){
            // TODO: сделать нормальную обработку исключений
            System.err.println(ex.getMessage());
        }
    }
}
