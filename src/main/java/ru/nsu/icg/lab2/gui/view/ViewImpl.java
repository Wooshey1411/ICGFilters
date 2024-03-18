package ru.nsu.icg.lab2.gui.view;

import com.formdev.flatlaf.intellijthemes.FlatArcDarkOrangeIJTheme;
import ru.nsu.icg.lab2.gui.controller.DrawingAreaController;
import ru.nsu.icg.lab2.gui.controller.WindowResizeController;
import ru.nsu.icg.lab2.gui.controller.menu.*;
import ru.nsu.icg.lab2.gui.common.*;
import ru.nsu.icg.lab2.model.dto.Tool;
import ru.nsu.icg.lab2.model.dto.ViewConfig;

import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ViewImpl implements View, ContextListener,ViewModeChangeListener {
    private final Context context;
    private final DrawingArea drawingArea;
    private final MainWindow mainWindow;

    public ViewImpl(ViewConfig viewConfig, List<Tool> tools, Context context, ImageReader imageReader, ImageWriter imageWriter) {
        FlatArcDarkOrangeIJTheme.setup();

        this.context = context;
        context.setViewModeChangeListener(this);

        final OpenController openController = new OpenController(context, this, imageReader);
        final SaveController saveController = new SaveController(context, this, imageWriter);
        final ExitController exitController = new ExitController(this);

        final ToolControllersFactory toolControllersFactory = new ToolControllersFactory(
                context,
                this,
                context.getBufferedImageFactory(),
                tools
        );

        final HelpController helpController = new HelpController(this);
        final AboutController aboutController = new AboutController(this);
        final WindowResizeController windowResizeController = new WindowResizeController(context, this);
        final DrawingAreaController drawingAreaController = new DrawingAreaController(context,this);

        drawingArea = new DrawingArea(drawingAreaController);

        final MenuArea menuArea = new MenuArea(
                openController,
                saveController,
                exitController,
                helpController,
                aboutController,
                toolControllersFactory.getToolControllers()
        );

        final ToolsArea toolsArea = new ToolsArea(toolControllersFactory.getToolControllers());

        mainWindow = new MainWindow(
                viewConfig.windowName(),
                viewConfig.windowMinWidth(),
                viewConfig.windowMinHeight(),
                viewConfig.windowPrefWidth(),
                viewConfig.windowPrefHeight(),
                exitController,
                menuArea.getMenuBar(),
                toolsArea,
                drawingArea,
                context,
                windowResizeController
        );
    }

    @Override
    public void show() {
        mainWindow.setVisible(true);
    }

    @Override
    public void hide() {
        mainWindow.setVisible(false);
    }

    @Override
    public void destroy() {
        mainWindow.dispose();
    }

    @Override
    public void resize() {
        final BufferedImageImpl image = context.getCurrentImage();

        if (image == null) {
            return;
        }
        int newHeight;
        int newWidth;

        double k = image.getWidth()*1.0/image.getHeight();
        if(context.getDrawingAreaWidth()*1.0 / context.getDrawingAreaHeight() >= k ) {
            newHeight = context.getDrawingAreaHeight();
            newWidth = (int) (newHeight * k);
        } else{
            newWidth = context.getDrawingAreaWidth();
            newHeight = (int)(newWidth / k);
        }

        BufferedImage resizedImage = new BufferedImage(
                newWidth,
                newHeight,
                BufferedImage.TYPE_INT_ARGB
        );

        Object hintValue;
        switch (context.getInterpolationMethod()){
            case BICUBIC -> hintValue = RenderingHints.VALUE_INTERPOLATION_BICUBIC;
            case BILINEAR -> hintValue = RenderingHints.VALUE_INTERPOLATION_BILINEAR;
            case NEAREST_NEIGHBOR -> hintValue = RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR;
            default -> throw new IllegalArgumentException("Unexpected interpolation method");
        }

        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                hintValue
        );
        graphics2D.drawImage(
                image.bufferedImage(),
                0,
                0,
                newWidth,
                newHeight,
                null
        );
        graphics2D.dispose();
        drawingArea.resizeSoftly(newWidth, newHeight);
        drawingArea.setImage(resizedImage);
        drawingArea.repaint();
        drawingArea.revalidate();
    }

    @Override
    public void showError(String errorMessage) {
        JOptionPane.showMessageDialog(
                mainWindow,
                errorMessage,
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }

    @Override
    public void repaint() {
        final BufferedImageImpl image = context.getCurrentImage();
        if(image == null){
            return;
        }
        if (context.getViewMode() == ViewMode.ONE_TO_ONE) {
            drawingArea.resizeSoftly(image.getWidth(), image.getHeight());
            drawingArea.setImage(image.bufferedImage());
            drawingArea.repaint();
            drawingArea.revalidate();
            return;
        }
        if (context.getViewMode() == ViewMode.ON_WINDOW_SIZE){
            resize();
        }
    }

    @Override
    public void showHelp() {
        JOptionPane.showMessageDialog(
                mainWindow,
                "It's help",
                "Help",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    @Override
    public void showAbout() {
        JOptionPane.showMessageDialog(
                mainWindow,
                "It's about",
                "About",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    @Override
    public boolean showConfirmationDialog(String title, JPanel content) {
        return JOptionPane.showConfirmDialog(mainWindow,
                content,
                title,
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        ) == JOptionPane.OK_OPTION;
    }

    @Override
    public JFrame getFrame() {
        return mainWindow;
    }

    @Override
    public JScrollPane getMainScrollPane() {
        return mainWindow.getScrollPane();
    }

    @Override
    public void onImageChange(Context context) {
        repaint();
    }

    @Override
    public void onTransformationChange(Context context) {
        // TODO: перенести
        if(context.getCurrentImage() == null){
            return;
        }
        mainWindow.setWaitCursor();
        context.setProcessedImage(new BufferedImageImpl(((BufferedImageImpl) context.getTransformation().apply(context.getOriginalImage())).bufferedImage()));
        context.setCurrentImage(context.getProcessedImage());
        repaint();
        mainWindow.resetCursor();
    }

    @Override
    public void onChangeViewMode(Context context) {
        if(context.getViewMode() == ViewMode.ON_WINDOW_SIZE){
            context.setDrawingAreaHeight(mainWindow.getWindowedDrawingAreaHeight());
            context.setDrawingAreaWidth(mainWindow.getWindowedDrawingAreaWidth());
            resize();
            mainWindow.disableScrolls();
        } else {
            mainWindow.enableScrolls();
            repaint();
        }
    }
}
