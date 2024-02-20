package ru.nsu.icg.lab2.gui;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    public MainWindow(){
        super("FilterShop");

        final JLabel label = new JLabel("Связал, проверяй");
        label.setFont(new Font("Monospaced", Font.BOLD, 82));
        add(label);

        setMinimumSize(new Dimension(640,480));
        setPreferredSize(new Dimension(800,800));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);

    }
    private void setMenu(){

    }
}
