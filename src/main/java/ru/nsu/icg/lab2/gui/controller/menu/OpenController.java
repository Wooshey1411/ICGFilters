package ru.nsu.icg.lab2.gui.controller.menu;

import ru.nsu.icg.lab2.gui.view.BufferedImageImpl;
import ru.nsu.icg.lab2.gui.view.View;
import ru.nsu.icg.lab2.gui.view.files.ImageOpeningChooser;
import ru.nsu.icg.lab2.gui.view.imageio.ImageReader;
import ru.nsu.icg.lab2.gui.view.context.Context;
import ru.nsu.icg.lab2.model.Utils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class OpenController implements ActionListener {
    private final Context context;
    private final View view;
    private final ImageReader imageReader;
    private final JFileChooser fileChooser;

    public OpenController(Context context, View view, ImageReader imageReader) {
        this.context = context;
        this.view = view;
        this.imageReader = imageReader;
        this.fileChooser = new ImageOpeningChooser(imageReader.getSupportedFormats());
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        final int code = fileChooser.showOpenDialog(null);

        if (code == JFileChooser.CANCEL_OPTION) {
            return;
        }

        if (code == JFileChooser.ERROR_OPTION) {
            view.showError("Cannot open file");
            return;
        }

        final File file = fileChooser.getSelectedFile();

        try {
            final BufferedImage image = imageReader.read(file);
            context.setOriginalImage(new BufferedImageImpl(image));
            context.setImage(new BufferedImageImpl(Utils.deepCopy(image)));
        } catch (IOException exception) {
            view.showError(exception.getLocalizedMessage());
        }
    }
}
