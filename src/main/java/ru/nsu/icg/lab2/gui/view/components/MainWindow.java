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

    private static final int SCROLL_PANE_BORDER_THICKNESS = 10;
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
                    scrollPane.setSize(new Dimension(holder.getWidth(),holder.getHeight()));
                    Insets scrollPaneBorder = scrollPane.getInsets();
                    drawingArea.setSize(scrollPane.getWidth()-scrollPaneBorder.right-scrollPaneBorder.left,
                            scrollPane.getHeight()-scrollPaneBorder.bottom-scrollPaneBorder.top);
                    Insets drawingAreaBorder = drawingArea.getInsets();
                    windowResizeListener.onDrawingAreaResize(drawingArea.getWidth()-drawingAreaBorder.left-drawingAreaBorder.right
                            , drawingArea.getHeight()-drawingAreaBorder.top-drawingAreaBorder.bottom);
                }
            }
        });
        scrollPane.setBorder(BorderFactory.createEmptyBorder(SCROLL_PANE_BORDER_THICKNESS,SCROLL_PANE_BORDER_THICKNESS
                ,SCROLL_PANE_BORDER_THICKNESS,SCROLL_PANE_BORDER_THICKNESS));
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
