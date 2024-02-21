package ru.nsu.icg.lab2.gui.view;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    public MainWindow(Dimension minSize, Dimension prefSize, String name){
        super(name);
        setMinimumSize(minSize);
        setPreferredSize(prefSize);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);

    }
    private void setMenu(){

    }
}
