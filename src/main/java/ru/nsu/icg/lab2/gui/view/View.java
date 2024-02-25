package ru.nsu.icg.lab2.gui.view;

import ru.nsu.icg.lab2.model.context.Context;
import ru.nsu.icg.lab2.model.context.ContextAction;
import ru.nsu.icg.lab2.model.context.ContextListener;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Consumer;

public class View implements ContextListener {
    private final MenuArea menuArea;
    private final ToolsArea toolsArea;
    private final DrawingArea drawingArea;
    private final MainWindow mainWindow;

    private final Map<ContextAction, Consumer<Context>> contextStateChangeHandlers = new EnumMap<>(ContextAction.class);

    public View(ViewConfig viewConfig, ActionListener actionListener){
        this.drawingArea = new DrawingArea();
        this.menuArea = new MenuArea(actionListener);
        this.toolsArea = new ToolsArea(actionListener);
        this.mainWindow = new MainWindow(
                viewConfig.windowName(),
                viewConfig.windowMinWidth(),
                viewConfig.windowMinHeight(),
                viewConfig.windowPrefWidth(),
                viewConfig.windowPrefHeight(),
                menuArea.getMenuBar(),
                toolsArea,
                drawingArea
        );

        contextStateChangeHandlers.put(ContextAction.IDLE, View.this::onIdle);
        contextStateChangeHandlers.put(ContextAction.REPAINT, View.this::onRepainting);
        contextStateChangeHandlers.put(ContextAction.OPEN_FILE, View.this::onOpeningFile);
        contextStateChangeHandlers.put(ContextAction.SAVE_FILE, View.this::onSavingFile);
        contextStateChangeHandlers.put(ContextAction.EXIT, View.this::onExiting);
        contextStateChangeHandlers.put(ContextAction.DISPLAY_ERROR, View.this::onDisplayingError);
        contextStateChangeHandlers.put(ContextAction.DISPLAY_HELP, View.this::onDisplayingHelp);
        contextStateChangeHandlers.put(ContextAction.DISPLAY_ABOUT, View.this::onDisplayingAbout);
    }

    public void show() {
        mainWindow.showWindow();
    }

    public void hide() {
        mainWindow.hideWindow();
    }

    @Override
    public void onContextActionChange(Context context, ContextAction action) {
        contextStateChangeHandlers.get(action).accept(context);
    }

    private void onIdle(Context context) {
        // Method is empty because we don't want to do anything with action "IDLE" of context
    }

    private void onRepainting(Context context) {
        final BufferedImage image = context.getImage();
        drawingArea.resizeSoftly(image.getWidth(), image.getHeight());
        drawingArea.setImage(image);
        drawingArea.repaint();
        drawingArea.revalidate();
        mainWindow.pack();
    }

    private void onOpeningFile(Context context) {
        JOptionPane.showMessageDialog(
                null,
                "I'm opening file ...",
                "File opening",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void onSavingFile(Context context) {
        JOptionPane.showMessageDialog(
                null,
                "I'm saving file ...",
                "File saving",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void onExiting(Context context) {
        mainWindow.hideWindow();
        mainWindow.dispose();
    }

    private void onDisplayingError(Context context) {
        JOptionPane.showMessageDialog(
                null,
                context.getErrorMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }

    private void onDisplayingHelp(Context context) {
        JOptionPane.showMessageDialog(
                null,
                "It's help",
                "Help",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void onDisplayingAbout(Context context) {
        JOptionPane.showMessageDialog(
                null,
                "It's about",
                "About",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}
