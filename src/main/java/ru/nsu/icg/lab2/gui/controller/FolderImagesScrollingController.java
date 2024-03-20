package ru.nsu.icg.lab2.gui.controller;

import ru.nsu.icg.lab2.gui.common.context.ContextImageReader;
import ru.nsu.icg.lab2.gui.common.View;
import ru.nsu.icg.lab2.gui.common.Context;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.List;

public class FolderImagesScrollingController implements KeyEventDispatcher {
    private static final boolean RETURN_VALUE = true;

    private final Context context;
    private final View view;
    private final ContextImageReader imageReader;
    private final String[] lowerCaseExtensions;
    private final FilenameFilter filenameFilter;

    public FolderImagesScrollingController(Context context, View view, ContextImageReader imageReader) {
        this.context = context;
        this.view = view;
        this.imageReader = imageReader;

        final String[] supportedFormats = imageReader.getSupportedFormats();
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
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        final int keyCode = keyEvent.getKeyCode();
        final boolean isKeyPressed = keyEvent.getID() == KeyEvent.KEY_PRESSED;
        final boolean openedFileEarly = context.getCurrentFile() != null && context.getWorkingDirectory() != null;
        final boolean pressedRightOrLeftArrow = keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_LEFT;

        if (!isKeyPressed || !openedFileEarly || !pressedRightOrLeftArrow) {
            return RETURN_VALUE;
        }

        final File workingDirectory = context.getWorkingDirectory();
        final File[] directoryFilesArray = workingDirectory.listFiles(filenameFilter);

        if (directoryFilesArray == null) {
            view.showError("Cannot read files in directory " + workingDirectory.getAbsolutePath());
            return RETURN_VALUE;
        }

        // User opened file in directory but later he deleted it, so directory is empty
        if (directoryFilesArray.length == 0) {
            return RETURN_VALUE;
        }

        imageReader.read(getNextFile(keyEvent, directoryFilesArray));

        return RETURN_VALUE;
    }

    private File getNextFile(KeyEvent keyEvent, File[] filesArray) {
        final List<File> filesList = Arrays.asList(filesArray);

        final int indexOfCurrentFile = filesList.indexOf(context.getCurrentFile());

        int indexOfNewFile;
        if (indexOfCurrentFile == -1) {
            indexOfNewFile = 0;
        } else {
            if (keyEvent.getKeyCode() == KeyEvent.VK_RIGHT) {
                indexOfNewFile = (indexOfCurrentFile + 1) % filesList.size();
            } else {
                if (indexOfCurrentFile == 0) {
                    indexOfNewFile = filesList.size() - 1;
                } else {
                    indexOfNewFile = (indexOfCurrentFile - 1) % filesList.size();
                }
            }
        }

        return filesList.get(indexOfNewFile);
    }
}
