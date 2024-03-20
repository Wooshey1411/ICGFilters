package ru.nsu.icg.lab2.gui.controller;

import ru.nsu.icg.lab2.gui.common.BufferedImageImpl;
import ru.nsu.icg.lab2.gui.common.ImageReader;
import ru.nsu.icg.lab2.gui.common.View;
import ru.nsu.icg.lab2.gui.common.context.Context;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class KeyController extends KeyAdapter {
    private final Context context;
    private final View view;

    private final String[] lowerCaseExtensions;
    private final FilenameFilter filenameFilter;

    public KeyController(Context context, View view, String[] supportedFormats) {
        this.context = context;
        this.view = view;

        lowerCaseExtensions = new String[supportedFormats.length];
        for (int i = 0; i < supportedFormats.length; i++) {
            lowerCaseExtensions[i] = supportedFormats[i].toLowerCase();
        }

        this.filenameFilter = (dir, name) -> {
            if (!name.contains(".")) {
                return false;
            }

            final String[] words = name.split("\\.");
            final String ext = words[words.length - 1].toLowerCase();

            for (final var it : lowerCaseExtensions) {
                if (ext.equals(it.toLowerCase())) {
                    return true;
                }
            }

            return false;
        };
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (context.getCurrentFile() == null || context.getWorkingDirectory() == null) {
            return;
        }

        final int keyCode = keyEvent.getKeyCode();

        if (keyCode != KeyEvent.VK_RIGHT && keyCode != KeyEvent.VK_LEFT) {
            return;
        }

        final File workingDirectory = context.getWorkingDirectory();
        final File currentFile = context.getCurrentFile();

        final File[] filesArray = workingDirectory.listFiles(filenameFilter);

        if (filesArray == null || filesArray.length == 0) {
            // TODO: do something
            return;
        }

        final List<File> filesList = Arrays.asList(filesArray);

        final int index = filesList.indexOf(currentFile);
        File file;
        if (index == -1) {
            file = filesList.get(0);
        } else {
            if (keyEvent.getKeyCode() == KeyEvent.VK_RIGHT) {
                file = filesList.get((index + 1) % filesList.size());
            } else {
                if (index == 0) {
                    file = filesList.get(filesList.size() - 1);
                } else {
                    file = filesList.get((index - 1) & filesList.size());
                }
            }
        }

        final ImageReader imageReader = new ImageReader();

        try {
            final BufferedImageImpl image = new BufferedImageImpl(imageReader.read(file));
            final int imageType = image.bufferedImage().getType();
            final int imageFactoryType = imageType == BufferedImage.TYPE_CUSTOM
                    ? BufferedImage.TYPE_INT_ARGB
                    : imageType;

            context.setWorkingDirectory(file.getParentFile());
            context.setCurrentFile(file);
            context.getBufferedImageFactory().setType(imageFactoryType);
            context.setOriginalImage(image);
            context.setProcessedImage(null);
            context.setImage(image);
        } catch (IOException exception) {
            view.showError(exception.getLocalizedMessage());
        }
    }
}
