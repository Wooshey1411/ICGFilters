package ru.nsu.icg.lab2.model.config;

import com.google.gson.Gson;
import ru.nsu.icg.lab2.gui.Main;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class JSONViewConfigParser implements ViewConfigParser {
    private final String fileName;

    public JSONViewConfigParser(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public ViewConfig parse() throws IOException {
        return new Gson().fromJson(
                new String(
                        Objects.requireNonNull(Main.class.getResourceAsStream("/" + fileName)).readAllBytes(),
                        StandardCharsets.UTF_8
                ),
                ViewConfig.class
        );
    }
}
