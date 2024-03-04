package ru.nsu.icg.lab2.gui.controller;

import ru.nsu.icg.lab2.model.context.Context;
import ru.nsu.icg.lab2.model.context.ContextAction;
import ru.nsu.icg.lab2.model.image.ImageReader;
import ru.nsu.icg.lab2.model.image.ImageWriter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class FilesActionsController implements ActionListener {
    private final Context context;
    private final ImageReader imageReader;
    private final ImageWriter imageWriter;

    public FilesActionsController(Context context, ImageReader imageReader, ImageWriter imageWriter) {
        this.context = context;
        this.imageReader = imageReader;
        this.imageWriter = imageWriter;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        handleJFileChooserEvent(actionEvent, (JFileChooser) actionEvent.getSource());
    }

    private void handleJFileChooserEvent(ActionEvent actionEvent, JFileChooser fileChooser) {
        final String actionCommand = actionEvent.getActionCommand();

        if (!Objects.equals(actionCommand, JFileChooser.APPROVE_SELECTION)) {
            return;
        }

        if (context.getAction() == ContextAction.OPEN_FILE) {
            handleApproveOpeningFile(fileChooser);
        } else {
            handleApproveSavingFile(fileChooser);
        }
    }

    private void handleApproveOpeningFile(JFileChooser fileChooser) {
        try {
            final BufferedImage image = imageReader.read(fileChooser.getSelectedFile());
            context.setOriginalImage(image);
            context.setProcessedImage(image);
            context.setAction(ContextAction.REPAINT);
        } catch (IOException exception) {
            context.pushError(exception.getLocalizedMessage());
        }
    }

    private void handleApproveSavingFile(JFileChooser fileChooser) {
        final String description = fileChooser.getFileFilter().getDescription();

        try {
            final File file = new File(fileChooser.getSelectedFile().getAbsolutePath() + "." + description);
            imageWriter.save(context.getProcessedImage(), file);
        } catch (IOException exception) {
            context.pushError(exception.getLocalizedMessage());
        }
    }
}
