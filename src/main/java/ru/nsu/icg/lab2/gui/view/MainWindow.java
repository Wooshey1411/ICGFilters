package ru.nsu.icg.lab2.gui.view;

import ru.nsu.icg.lab2.gui.common.Context;
import ru.nsu.icg.lab2.gui.common.ViewMode;
import ru.nsu.icg.lab2.gui.common.WindowResizeListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowListener;

public class MainWindow extends JFrame {
    private static final int SCROLL_PANE_BORDER_THICKNESS = 10;
    private final JScrollPane scrollPane;

    private final JPanel drawingArea;
    public MainWindow(String name,
                      int minWidth,
                      int minHeight,
                      int prefWidth,
                      int prefHeight,
                      WindowListener windowListener,
                      JMenuBar menuBar,
                      JPanel toolsArea,
                      JPanel drawingArea,
                      Context context,
                      WindowResizeListener windowResizeListener
    ) {
        super(name);
        addWindowListener(windowListener);
        setMinimumSize(new Dimension(minWidth, minHeight));
        setPreferredSize(new Dimension(prefWidth, prefHeight));
        this.drawingArea = drawingArea;
        scrollPane = new JScrollPane(drawingArea);
        scrollPane.setBounds(0, 0, 0, 0);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                if (context.getViewMode() == ViewMode.ON_WINDOW_SIZE) {
                    final Insets drawingAreaBorder = drawingArea.getInsets();
                    drawingArea.setSize(new Dimension(scrollPane.getWidth()-scrollPane.getInsets().right-scrollPane.getInsets().left,
                            scrollPane.getHeight()-scrollPane.getInsets().top-scrollPane.getInsets().bottom));
                    windowResizeListener.onDrawingAreaResize(
                            drawingArea.getWidth() - drawingAreaBorder.left - drawingAreaBorder.right,
                            drawingArea.getHeight() - drawingAreaBorder.top - drawingAreaBorder.bottom
                    );
                }
            }
        });
        scrollPane.setBorder(BorderFactory.createEmptyBorder(
                SCROLL_PANE_BORDER_THICKNESS,
                SCROLL_PANE_BORDER_THICKNESS,
                SCROLL_PANE_BORDER_THICKNESS,
                SCROLL_PANE_BORDER_THICKNESS
        ));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER);
        setJMenuBar(menuBar);
        add(toolsArea, BorderLayout.NORTH);
        setLocationRelativeTo(null);
        pack();
    }
    public int getWindowedDrawingAreaWidth(){
        return scrollPane.getWidth() - scrollPane.getInsets().left - scrollPane.getInsets().right - drawingArea.getInsets().left - drawingArea.getInsets().right;
    }
    public int getWindowedDrawingAreaHeight(){
        return scrollPane.getHeight() - scrollPane.getInsets().top - scrollPane.getInsets().bottom - drawingArea.getInsets().top - drawingArea.getInsets().bottom;
    }
    public void disableScrolls(){
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
    }
    public void enableScrolls(){
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    }
    public void setWaitCursor(){
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
    }
    public void resetCursor(){
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
}
