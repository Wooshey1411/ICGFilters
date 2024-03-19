package ru.nsu.icg.lab2.gui.controller;

import ru.nsu.icg.lab2.gui.common.context.Context;
import ru.nsu.icg.lab2.gui.common.DrawingAreaAction;
import ru.nsu.icg.lab2.gui.common.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DrawingAreaController extends MouseAdapter {

    private final Context context;
    private final View view;

    public DrawingAreaController(Context context, View view){
        this.context = context;
        this.view = view;
    }

    private int prevX,prevY;
    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        if(context.getDrawingAreaAction() == DrawingAreaAction.SWAP_IMAGE){
            System.out.println("SWAP");
            context.swapImage();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        if(context.getDrawingAreaAction() == DrawingAreaAction.MOVE_SCROLLS){
            prevX = e.getX();
            prevY = e.getY();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        super.mouseDragged(e);
        if(context.getDrawingAreaAction() == DrawingAreaAction.MOVE_SCROLLS){
            if(e.getModifiersEx() == InputEvent.BUTTON3_DOWN_MASK){
                return;
            }
            JScrollPane spIm = view.getMainScrollPane();
            Point scroll = spIm.getViewport().getViewPosition();
            scroll.x += (prevX - e.getX());
            scroll.y += (prevY - e.getY());
            spIm.getHorizontalScrollBar().setValue(scroll.x);
            spIm.getVerticalScrollBar().setValue(scroll.y);
            spIm.repaint();
        }
    }
}
