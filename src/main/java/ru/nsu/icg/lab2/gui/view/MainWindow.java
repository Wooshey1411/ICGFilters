package ru.nsu.icg.lab2.gui.view;

import ru.nsu.icg.lab2.gui.model.Context;
import ru.nsu.icg.lab2.gui.model.ViewMode;
import ru.nsu.icg.lab2.gui.model.WindowResizeListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowListener;

public class MainWindow extends JFrame {
    private static final int SCROLL_PANE_BORDER_THICKNESS = 10;

    private final JPanel scrollPaneHolder;
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
        scrollPaneHolder = new JPanel();
        scrollPaneHolder.setLayout(null);
        scrollPaneHolder.add(scrollPane);
        scrollPane.setBounds(0, 0, 0, 0);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                if (context.getViewMode() == ViewMode.ON_WINDOW_SIZE) {
                    scrollPane.setSize(new Dimension(
                            scrollPaneHolder.getWidth(),
                            scrollPaneHolder.getHeight()
                    ));

                    final Insets scrollPaneBorder = scrollPane.getInsets();
                    drawingArea.setSize(
                            scrollPane.getWidth() - scrollPaneBorder.right - scrollPaneBorder.left,
                            scrollPane.getHeight() - scrollPaneBorder.bottom - scrollPaneBorder.top
                    );
                    final Insets drawingAreaBorder = drawingArea.getInsets();

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
        add(scrollPaneHolder, BorderLayout.CENTER);
        setJMenuBar(menuBar);
        add(toolsArea, BorderLayout.NORTH);
        setLocationRelativeTo(null);
        pack();
    }

    public void resizeImagePane(int width, int height){
        Dimension newSize = new Dimension(width + scrollPane.getInsets().right+scrollPane.getInsets().left + drawingArea.getInsets().right + drawingArea.getInsets().left,
                height + scrollPane.getInsets().top+scrollPane.getInsets().bottom + drawingArea.getInsets().bottom + drawingArea.getInsets().top);
        scrollPaneHolder.setSize(newSize);
        scrollPane.setSize(newSize);
    }
}
