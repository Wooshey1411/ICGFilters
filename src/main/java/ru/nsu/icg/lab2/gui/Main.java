package ru.nsu.icg.lab2.gui;

import com.google.gson.Gson;
import ru.nsu.icg.lab2.gui.view.View;
import ru.nsu.icg.lab2.gui.view.ViewConfig;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        ViewConfig viewConfig;
        try {
            viewConfig = new Gson().fromJson(new String(Objects.requireNonNull(Main.class.getResourceAsStream("viewConfig.json")).readAllBytes(), StandardCharsets.UTF_8), ViewConfig.class);
        } catch (IOException ex){
            System.err.println(ex.getMessage());
            return;
        }
        View view = new View(viewConfig);
    }
}