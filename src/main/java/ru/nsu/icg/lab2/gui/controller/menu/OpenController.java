package ru.nsu.icg.lab2.gui.controller.menu;

import ru.nsu.icg.lab2.gui.common.context.Context;
import ru.nsu.icg.lab2.gui.controller.files.ImageOpeningChooser;
import ru.nsu.icg.lab2.gui.common.*;

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
        final int code = fileChooser.showOpenDialog(view.getFrame());

        if (code == JFileChooser.CANCEL_OPTION) {
            return;
        }

        if (code == JFileChooser.ERROR_OPTION) {
            view.showError("Cannot open file");
            return;
        }

        final File file = fileChooser.getSelectedFile();

        try {
            final BufferedImageImpl image = new BufferedImageImpl(imageReader.read(file));
            final int imageType = image.bufferedImage().getType();
            final int imageFactoryType = imageType == BufferedImage.TYPE_CUSTOM
                    ? BufferedImage.TYPE_INT_ARGB
                    : imageType;
            context.getBufferedImageFactory().setType(imageFactoryType);
            context.setOriginalImage(image);
            context.setProcessedImage(null);
            context.setImage(context.getOriginalImage());
        } catch (IOException exception) {
            view.showError(exception.getLocalizedMessage());
        }
    }
}
