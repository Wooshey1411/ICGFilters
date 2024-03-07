package ru.nsu.icg.lab2.gui.view;

import ru.nsu.icg.lab2.gui.controller.WindowResizeListener;
import ru.nsu.icg.lab2.gui.view.components.DrawingArea;
import ru.nsu.icg.lab2.gui.view.components.MainWindow;
import ru.nsu.icg.lab2.gui.view.components.MenuArea;
import ru.nsu.icg.lab2.gui.view.components.ToolsArea;
import ru.nsu.icg.lab2.gui.view.config.ViewConfig;
import ru.nsu.icg.lab2.gui.view.files.ImageChooser;
import ru.nsu.icg.lab2.gui.view.files.ImageOpeningChooser;
import ru.nsu.icg.lab2.gui.view.files.ImageSavingChooser;
import ru.nsu.icg.lab2.gui.view.icons.IconsSupplier;
import ru.nsu.icg.lab2.model.context.Context;
import ru.nsu.icg.lab2.model.context.ContextAction;
import ru.nsu.icg.lab2.model.context.ContextListener;
import ru.nsu.icg.lab2.model.context.ViewModeContext;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Consumer;

public class View implements ContextListener {
    private final MenuArea menuArea;
    private final ToolsArea toolsArea;
    private final DrawingArea drawingArea;
    private final MainWindow mainWindow;
    private final ImageChooser imagesOpeningFileChoose;
    private final JFileChooser imagesSavingFileChooser;
    private final ViewModeContext viewModeContext;
    private final Map<ContextAction, Consumer<Context>> contextStateChangeHandlers = new EnumMap<>(ContextAction.class);

    public View(
            ViewConfig viewConfig,
            IconsSupplier iconsSupplier,
            ActionListener buttonsListener,
            WindowListener windowListener,
            ActionListener filesActionsListener,
            String[] supportedReadFormats,
            String[] supportedWriteFormats,
            ViewModeContext viewModeContext,
            WindowResizeListener windowResizeListener
    ) {
        this.drawingArea = new DrawingArea();
        this.menuArea = new MenuArea(buttonsListener);
        this.toolsArea = new ToolsArea(iconsSupplier, buttonsListener);
        this.viewModeContext = viewModeContext;
        this.mainWindow = new MainWindow(
                viewConfig.windowName(),
                viewConfig.windowMinWidth(),
                viewConfig.windowMinHeight(),
                viewConfig.windowPrefWidth(),
                viewConfig.windowPrefHeight(),
                windowListener,
                menuArea.getMenuBar(),
                toolsArea,
                drawingArea,
                viewModeContext,
                windowResizeListener
        );

        contextStateChangeHandlers.put(ContextAction.IDLE, View.this::onIdle);
        contextStateChangeHandlers.put(ContextAction.REPAINT, View.this::onRepainting);
        contextStateChangeHandlers.put(ContextAction.OPEN_FILE, View.this::onOpeningFile);
        contextStateChangeHandlers.put(ContextAction.SAVE_FILE, View.this::onSavingFile);
        contextStateChangeHandlers.put(ContextAction.EXIT, View.this::onExiting);
        contextStateChangeHandlers.put(ContextAction.DISPLAY_ERROR, View.this::onDisplayingError);
        contextStateChangeHandlers.put(ContextAction.DISPLAY_HELP, View.this::onDisplayingHelp);
        contextStateChangeHandlers.put(ContextAction.DISPLAY_ABOUT, View.this::onDisplayingAbout);
        contextStateChangeHandlers.put(ContextAction.DRAWING_AREA_RESIZE,View.this::onDrawingAreaResize);

        this.imagesOpeningFileChoose = new ImageOpeningChooser(supportedReadFormats, filesActionsListener);
        this.imagesSavingFileChooser = new ImageSavingChooser(supportedWriteFormats, filesActionsListener);
    }

    public void show() {
        mainWindow.showWindow();
    }

    @Override
    public void onContextActionChange(Context context, ContextAction action) {
        contextStateChangeHandlers.get(action).accept(context);
    }

    private void onIdle(Context context) {
        // Method is empty because we don't want to do anything with action "IDLE" of context
    }

    private void onRepainting(Context context) {
        /*final BufferedImage image = context.getProcessedImage();
        drawingArea.resizeSoftly(image.getWidth(), image.getHeight());
        drawingArea.setImage(image);
        drawingArea.repaint();
        drawingArea.revalidate();
        System.out.println("repaint");*/
        onDrawingAreaResize(context);
    }

    private void onOpeningFile(Context context) {
        final int code = imagesOpeningFileChoose.showOpenDialog(this.mainWindow);

        if (code == JFileChooser.ERROR_OPTION) {
            showError("Cannot open file");
        }
    }

    private void onSavingFile(Context context) {
        final int code = imagesSavingFileChooser.showSaveDialog(this.mainWindow);

        if (code == JFileChooser.ERROR_OPTION) {
            showError("Cannot save file");
        }
    }

    private void onExiting(Context context) {
        mainWindow.hideWindow();
        mainWindow.dispose();
    }

    private void onDisplayingError(Context context) {
        showError(context.getErrorMessage());
    }

    private void onDisplayingHelp(Context context) {
        JOptionPane.showMessageDialog(
                mainWindow,
                "It's help",
                "Help",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void onDisplayingAbout(Context context) {
        JOptionPane.showMessageDialog(
                mainWindow,
                "It's about",
                "About",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void showError(String errorMessage) {
        JOptionPane.showMessageDialog(
                mainWindow,
                errorMessage,
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }

    private void onDrawingAreaResize(Context context){
        System.out.println(context.getDrawingAreaWidth() + " " + context.getDrawingAreaHeight());
        if(context.getProcessedImage() == null){
            return;
        }
        BufferedImage resizedImage = new BufferedImage(context.getDrawingAreaWidth(),context.getDrawingAreaHeight(),BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.drawImage(context.getProcessedImage(),0,0,context.getDrawingAreaWidth(),context.getDrawingAreaHeight(),null);
        graphics2D.dispose();
        drawingArea.resizeSoftly(resizedImage.getWidth(), resizedImage.getHeight());
        drawingArea.setImage(resizedImage);
        drawingArea.repaint();
        drawingArea.revalidate();
    }
}
