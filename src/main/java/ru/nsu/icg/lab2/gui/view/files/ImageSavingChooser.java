package ru.nsu.icg.lab2.gui.view.files;

import javax.swing.filechooser.FileFilter;
import java.awt.event.ActionListener;

public class ImageSavingChooser extends ImageChooser {
    public ImageSavingChooser(String[] supportedFormats, ActionListener actionListener) {
        super(actionListener);

        for (final var it : supportedFormats) {
            final FileFilter fileFilter = new FileSavingFilter(it);
            addChoosableFileFilter(fileFilter);
        }
    }
}
