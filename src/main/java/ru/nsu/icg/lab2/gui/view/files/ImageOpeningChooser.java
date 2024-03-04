package ru.nsu.icg.lab2.gui.view.files;

import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionListener;

public class ImageOpeningChooser extends ImageChooser {
    public ImageOpeningChooser(String[] supportedFormats, ActionListener actionListener) {
        super(actionListener);
        addChoosableFileFilter(new FileNameExtensionFilter("Image", supportedFormats));
    }
}
