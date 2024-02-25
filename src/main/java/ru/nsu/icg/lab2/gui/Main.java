package ru.nsu.icg.lab2.gui;

import com.google.gson.Gson;
import ru.nsu.icg.lab2.gui.controller.Controller;
import ru.nsu.icg.lab2.gui.view.View;
import ru.nsu.icg.lab2.gui.view.ViewConfig;
import ru.nsu.icg.lab2.model.context.Context;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        try {
            final Context context = new Context();

            // TODO: вынести парсинг в отдельный класс (с интерфейсом)
            final ViewConfig viewConfig = new Gson().fromJson(new String(Objects.requireNonNull(Main.class.getResourceAsStream("viewConfig.json")).readAllBytes(), StandardCharsets.UTF_8), ViewConfig.class);

            final Controller controller = new Controller(context);
            final View view = new View(
                    viewConfig,
                    controller
            );
            context.addListener(view);
            view.show();
        } catch (IOException ex){
            // TODO: сделать нормальную обработку исключений
            System.err.println(ex.getMessage());
        }
    }
}
