package ru.nsu.icg.lab2.gui.view.components;

import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

@Setter
public class DrawingArea extends JPanel {
    private BufferedImage image;

    public DrawingArea() {
        setBorder(BorderFactory.createDashedBorder(Color.GREEN,3,  3,3,false));
        setBackground(Color.WHITE);
    }

    public void resizeSoftly(int width, int height) {
        setPreferredSize(new Dimension(width+6, height+6));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 3, 3, null);
    }
}
