package ru.nsu.icg.lab2.gui;

import ru.nsu.icg.lab2.gui.model.*;
import ru.nsu.icg.lab2.gui.view.ViewImpl;
import ru.nsu.icg.lab2.model.Tool;
import ru.nsu.icg.lab2.model.ToolsConfigParser;
import ru.nsu.icg.lab2.model.ViewConfig;
import ru.nsu.icg.lab2.model.ViewConfigParser;
import ru.nsu.icg.lab2.model.json.JSONToolsConfigParser;
import ru.nsu.icg.lab2.model.json.JSONViewConfigParser;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            final Context context = new Context(ViewMode.ONE_TO_ONE, new BufferedImageFactory());

            final ViewConfigParser viewConfigParser = new JSONViewConfigParser("view_config.json");
            final ViewConfig viewConfig = viewConfigParser.parse();

            final ToolsConfigParser toolsConfigParser = new JSONToolsConfigParser("tools_config.json");
            final List<Tool> tools = toolsConfigParser.parse();

            final ImageReader imageReader = new ImageReader();
            final ImageWriter imageWriter = new ImageWriter();

            final ViewImpl view = new ViewImpl(viewConfig, tools, context, imageReader, imageWriter);

            context.addListener(view);

            view.show();
        } catch (Exception exception) {
            System.err.println(exception.getLocalizedMessage());
        }
    }
}
