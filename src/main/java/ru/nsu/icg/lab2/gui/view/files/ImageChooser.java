package ru.nsu.icg.lab2.gui.view.files;

import javax.swing.*;
import java.awt.event.ActionListener;

public abstract class ImageChooser extends JFileChooser {
    public ImageChooser(ActionListener actionListener) {
        setAcceptAllFileFilterUsed(false);
        addActionListener(actionListener);
    }
}
