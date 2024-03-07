package ru.nsu.icg.lab2.gui.view.components;

import ru.nsu.icg.lab2.gui.controller.WindowResizeListener;
import ru.nsu.icg.lab2.model.context.ViewMode;
import ru.nsu.icg.lab2.model.context.ViewModeContext;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowListener;

public class MainWindow extends JFrame {
    public MainWindow(String name,
                      int minWidth,
                      int minHeight,
                      int prefWidth,
                      int prefHeight,
                      WindowListener windowListener,
                      JMenuBar menuBar,
                      JPanel toolsArea,
                      JPanel drawingArea,
                      ViewModeContext viewModeContext,
                      WindowResizeListener windowResizeListener
    ) {
        super(name);
        addWindowListener(windowListener);
        setMinimumSize(new Dimension(minWidth, minHeight));
        setPreferredSize(new Dimension(prefWidth, prefHeight));
        final JScrollPane scrollPane = new JScrollPane(drawingArea);
        JPanel holder = new JPanel();
        holder.setLayout(null);
        holder.add(scrollPane);
        scrollPane.setBounds(0,0,0,0);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                if(viewModeContext.getViewMode() == ViewMode.ON_WINDOW_SIZE) {
                    int height = getHeight() - getInsets().bottom-getInsets().top-toolsArea.getInsets().bottom-toolsArea.getInsets().top-menuBar.getInsets().bottom-menuBar.getInsets().top-menuBar.getHeight()-toolsArea.getHeight();
                    int width = getWidth() - getInsets().right - getInsets().left;
                    scrollPane.setSize(new Dimension(width, height));
                    Insets border = drawingArea.getBorder().getBorderInsets(drawingArea);
                    holder.setSize(width,height);
                    drawingArea.setSize(new Dimension(width-20,height-20));
                    windowResizeListener.onDrawingAreaResize(width-border.left-border.right-20, height-border.top-border.bottom-20);
                }
            }
        });
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(holder, BorderLayout.CENTER);
        setJMenuBar(menuBar);
        add(toolsArea, BorderLayout.NORTH);
        setLocationRelativeTo(null);
        pack();
    }

    public void showWindow() {
        setVisible(true);
    }

    public void hideWindow() {
        setVisible(false);
    }
}
