package ru.nsu.icg.lab2.gui.view;

import com.formdev.flatlaf.intellijthemes.FlatArcDarkOrangeIJTheme;
import ru.nsu.icg.lab2.gui.controller.WindowResizeController;
import ru.nsu.icg.lab2.gui.controller.menu.*;
import ru.nsu.icg.lab2.gui.controller.tools.HandController;
import ru.nsu.icg.lab2.gui.controller.tools.OneToOneController;
import ru.nsu.icg.lab2.gui.controller.tools.transformations.GammaCorrectionController;
import ru.nsu.icg.lab2.gui.controller.tools.transformations.GreyTransformationController;
import ru.nsu.icg.lab2.gui.controller.tools.transformations.RotationController;
import ru.nsu.icg.lab2.gui.view.components.MainWindow;
import ru.nsu.icg.lab2.gui.view.components.MenuArea;
import ru.nsu.icg.lab2.gui.view.components.ToolsArea;
import ru.nsu.icg.lab2.gui.view.components.DrawingArea;
import ru.nsu.icg.lab2.gui.view.context.ContextListener;
import ru.nsu.icg.lab2.gui.view.imageio.ImageReader;
import ru.nsu.icg.lab2.gui.view.imageio.ImageWriter;
import ru.nsu.icg.lab2.model.config.ViewConfig;
import ru.nsu.icg.lab2.gui.view.context.Context;
import ru.nsu.icg.lab2.gui.view.context.ViewMode;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ViewImpl implements View, ContextListener {
    private final Context context;
    private final MenuArea menuArea;
    private final ToolsArea toolsArea;
    private final DrawingArea drawingArea;
    private final MainWindow mainWindow;

    public ViewImpl(ViewConfig viewConfig, Context context, ImageReader imageReader, ImageWriter imageWriter) {
        FlatArcDarkOrangeIJTheme.setup();

        this.context = context;
        this.context.addListener(this);
        final IconsSupplier iconsSupplier = new IconsSupplier();

        final OpenController openController = new OpenController(context, this, imageReader);
        final SaveController saveController = new SaveController(context, this, imageWriter);
        final ExitController exitController = new ExitController(this);
        final HandController handController = new HandController();
        final OneToOneController oneToOneController = new OneToOneController();
        final RotationController rotationController = new RotationController(context);
        final GreyTransformationController greyTransformationController = new GreyTransformationController(context);
        final GammaCorrectionController gammaCorrectionController = new GammaCorrectionController(context, this);
        final HelpController helpController = new HelpController(this);
        final AboutController aboutController = new AboutController(this);
        final WindowResizeController windowResizeController = new WindowResizeController(context, this);

        drawingArea = new DrawingArea();

        menuArea = new MenuArea(
                openController,
                saveController,
                exitController,
                handController,
                oneToOneController,
                rotationController,
                greyTransformationController,
                gammaCorrectionController,
                helpController,
                aboutController
        );

        toolsArea = new ToolsArea(
                iconsSupplier,
                handController,
                oneToOneController,
                rotationController,
                greyTransformationController,
                gammaCorrectionController
        );

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
        final BufferedImageImpl image = context.getImage();

        if(image == null){
            return;
        }

        BufferedImage resizedImage = new BufferedImage(
                context.getDrawingAreaWidth(),
                context.getDrawingAreaHeight(),
                BufferedImage.TYPE_INT_ARGB
        );
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR
        );
        graphics2D.drawImage(
                image.bufferedImage(),
                0,
                0,
                context.getDrawingAreaWidth(),
                context.getDrawingAreaHeight(),
                null
        );
        graphics2D.dispose();
        drawingArea.resizeSoftly(resizedImage.getWidth(), resizedImage.getHeight());
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
        final BufferedImageImpl image = context.getImage();

        if(context.getViewMode() == ViewMode.ONE_TO_ONE) {
            drawingArea.resizeSoftly(image.getWidth(), image.getHeight());
            drawingArea.setImage(image.bufferedImage());
            drawingArea.repaint();
            drawingArea.revalidate();
            mainWindow.pack();
        } else {
            resize();
        }
        // TODO: Во время установки изображения смотрим на режим отображения и выбираем соотв. Сделать лучше как-то?
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
    public void onImageChange(Context context) {
        repaint();
    }

    @Override
    public void onTransformationChange(Context context) {
        context.getTransformation().apply(context.getOriginalImage(), context.getImage());
        repaint();
    }
}
