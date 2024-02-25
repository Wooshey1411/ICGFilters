package ru.nsu.icg.lab2.gui.view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class DrawingArea extends JPanel {
    private BufferedImage image;

    public DrawingArea() {
        setBackground(Color.WHITE);
    }

    void setImage(BufferedImage image) {
        this.image = image;
    }

    void resizeSoftly(int width, int height) {
        setPreferredSize(new Dimension(width, height));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }
}
