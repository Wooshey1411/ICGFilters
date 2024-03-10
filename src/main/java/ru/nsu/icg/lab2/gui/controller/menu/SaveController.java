package ru.nsu.icg.lab2.gui.controller.menu;

import ru.nsu.icg.lab2.gui.view.Context;
import ru.nsu.icg.lab2.gui.view.ImageWriter;
import ru.nsu.icg.lab2.gui.view.View;
import ru.nsu.icg.lab2.gui.controller.files.ImageSavingChooser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class SaveController implements ActionListener {
    private final Context context;
    private final View view;
    private final ImageWriter imageWriter;
    private final JFileChooser fileChooser;

    public SaveController(Context context, View view, ImageWriter imageWriter) {
        this.context = context;
        this.view = view;
        this.imageWriter = imageWriter;
        this.fileChooser = new ImageSavingChooser(imageWriter.getSupportedFormats());
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        final int code = fileChooser.showSaveDialog(view.getFrame());

        if (code == JFileChooser.CANCEL_OPTION) {
            return;
        }

        if (code == JFileChooser.ERROR_OPTION) {
            view.showError("Cannot save file");
            return;
        }

        final String fileWithoutExtension = fileChooser.getSelectedFile().toString();
        final String fileExtension = fileChooser.getFileFilter().getDescription();
        final String filePath = String.format("%s.%s", fileWithoutExtension, fileExtension);

        try {
            imageWriter.save(context.getImage().bufferedImage(), new File(filePath));
        } catch (IOException exception) {
            view.showError(exception.getLocalizedMessage());
        }
    }
}
