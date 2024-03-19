package ru.nsu.icg.lab2.gui.view;

import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;

@Setter
public class DrawingArea extends JPanel {
    private static final int BORDER_THICKNESS = 3;
    private static final int BORDER_LENGTH = 3;
    private static final int BORDER_SPACING = 3;
    private BufferedImage image;

    public DrawingArea(MouseAdapter mouseAdapter) {
        setBorder(BorderFactory.createDashedBorder(
                Color.GREEN, BORDER_THICKNESS,
                BORDER_LENGTH, BORDER_SPACING,
                false
        ));
        setBackground(Color.DARK_GRAY);
        this.setDoubleBuffered(true);
        this.addMouseListener(mouseAdapter);
        this.addMouseMotionListener(mouseAdapter);
    }

    public void resizeSoftly(int width, int height) {
        setPreferredSize(new Dimension(
                width + getInsets().left + getInsets().right,
                height + getInsets().top + getInsets().bottom
        ));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, getInsets().top, getInsets().left, null);
    }
}
