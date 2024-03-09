package ru.nsu.icg.lab2.gui.view;

import com.formdev.flatlaf.intellijthemes.FlatArcDarkOrangeIJTheme;
import ru.nsu.icg.lab2.gui.controller.WindowResizeController;
import ru.nsu.icg.lab2.gui.controller.menu.*;
import ru.nsu.icg.lab2.gui.controller.tools.HandController;
import ru.nsu.icg.lab2.gui.controller.tools.OneToOneController;
import ru.nsu.icg.lab2.gui.controller.tools.transformations.GreyTransformationController;
import ru.nsu.icg.lab2.gui.controller.tools.transformations.RotationController;
import ru.nsu.icg.lab2.gui.view.components.MainWindow;
import ru.nsu.icg.lab2.gui.view.components.MenuArea;
import ru.nsu.icg.lab2.gui.view.components.ToolsArea;
import ru.nsu.icg.lab2.gui.view.components.DrawingArea;
import ru.nsu.icg.lab2.gui.view.context.ContextImageListener;
import ru.nsu.icg.lab2.gui.view.files.ImageOpeningChooser;
import ru.nsu.icg.lab2.gui.view.files.ImageSavingChooser;
import ru.nsu.icg.lab2.gui.view.imageio.ImageReader;
import ru.nsu.icg.lab2.gui.view.imageio.ImageWriter;
import ru.nsu.icg.lab2.model.config.ViewConfig;
import ru.nsu.icg.lab2.gui.view.context.Context;
import ru.nsu.icg.lab2.gui.view.context.ViewMode;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class ViewImpl implements View, ContextImageListener {
    private final Context context;
    private final MenuArea menuArea;
    private final ToolsArea toolsArea;
    private final DrawingArea drawingArea;
    private final MainWindow mainWindow;

    public ViewImpl(ViewConfig viewConfig, Context context, ImageReader imageReader, ImageWriter imageWriter) {
        FlatArcDarkOrangeIJTheme.setup();

        this.context = context;
        this.context.addImageListener(this);
        final IconsSupplier iconsSupplier = new IconsSupplier();

        final OpenController openController = new OpenController(context, this, imageReader);
        final SaveController saveController = new SaveController(context, this, imageWriter);
        final ExitController exitController = new ExitController(this);
        final HandController handController = new HandController();
        final OneToOneController oneToOneController = new OneToOneController();
        final RotationController rotationController = new RotationController(context);
        final GreyTransformationController greyTransformationController = new GreyTransformationController(context);
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
                helpController,
                aboutController
        );

        toolsArea = new ToolsArea(iconsSupplier, context);

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
    public void onImageChange(BufferedImage bufferedImage) {
        final BufferedImageImpl image = context.getImage();
        System.out.println(image.bufferedImage());

        if(context.getViewMode() == ViewMode.ONE_TO_ONE) {
            System.out.println("1");
            drawingArea.resizeSoftly(image.getWidth(), image.getHeight());
            drawingArea.setImage(image.bufferedImage());
            drawingArea.repaint();
            drawingArea.revalidate();
            mainWindow.pack();
        } else {
            System.out.println("2");
            resize();
        }
        // TODO: Во время установки изображения смотрим на режим отображения и выбираем соотв. Сделать лучше как-то?
    }
}
