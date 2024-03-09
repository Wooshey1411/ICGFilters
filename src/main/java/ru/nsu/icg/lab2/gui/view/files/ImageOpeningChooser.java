package ru.nsu.icg.lab2.gui.view.files;

import javax.swing.filechooser.FileNameExtensionFilter;

public class ImageOpeningChooser extends ImageChooser {
    public ImageOpeningChooser(String[] supportedFormats) {
        addChoosableFileFilter(new FileNameExtensionFilter("Image", supportedFormats));
    }
}
