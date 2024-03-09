package ru.nsu.icg.lab2.gui.controller.menu;

import ru.nsu.icg.lab2.gui.view.View;
import ru.nsu.icg.lab2.gui.view.imageio.ImageReader;
import ru.nsu.icg.lab2.gui.view.context.ContextImpl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class OpenController implements ActionListener {
    private final ContextImpl context;
    private final View view;
    private final ImageReader imageReader;

    public OpenController(ContextImpl context, View view, ImageReader imageReader) {
        this.context = context;
        this.view = view;
        this.imageReader = imageReader;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        final File file = view.showOpenDialog();

        try {
            final BufferedImage image = imageReader.read(file);
            context.setOriginalImage(image);
            context.setImage(image);
        } catch (IOException exception) {
            view.showError(exception.getLocalizedMessage());
        }
    }
}
