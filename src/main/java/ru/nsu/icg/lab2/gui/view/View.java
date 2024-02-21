package ru.nsu.icg.lab2.gui.view;

import java.awt.*;

public class View {

    public View(ViewConfig viewConfig){
        MainWindow mainWindow = new MainWindow(new Dimension(viewConfig.windowMinWidth(), viewConfig.windowMinHeight()),
                new Dimension(viewConfig.windowPrefWidth(), viewConfig.windowPrefHeight()),
                viewConfig.windowName());
    }

}
