package ru.nsu.icg.lab2.gui.view.files;

import javax.swing.*;

public abstract class ImageChooser extends JFileChooser {
    public ImageChooser() {
        setAcceptAllFileFilterUsed(false);
    }
}
