package ru.nsu.icg.lab2.gui.controller.menu;

import ru.nsu.icg.lab2.gui.view.View;
import ru.nsu.icg.lab2.gui.view.imageio.ImageWriter;
import ru.nsu.icg.lab2.gui.view.context.ContextImpl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class SaveController implements ActionListener {
    private final ContextImpl context;
    private final View view;
    private final ImageWriter imageWriter;

    public SaveController(ContextImpl context, View view, ImageWriter imageWriter) {
        this.context = context;
        this.view = view;
        this.imageWriter = imageWriter;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        final File file = view.showSaveDialog();

        try {
            imageWriter.save(context.getImage(), file);
        } catch (IOException exception) {
            view.showError(exception.getLocalizedMessage());
        }
    }
}
