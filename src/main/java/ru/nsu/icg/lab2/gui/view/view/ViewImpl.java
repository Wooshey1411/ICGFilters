package ru.nsu.icg.lab2.gui.view.view;

import com.formdev.flatlaf.intellijthemes.FlatArcDarkOrangeIJTheme;
import ru.nsu.icg.lab2.gui.controller.WindowResizeController;
import ru.nsu.icg.lab2.gui.controller.menu.*;
import ru.nsu.icg.lab2.gui.controller.tools.UndoController;
import ru.nsu.icg.lab2.gui.controller.tools.HandController;
import ru.nsu.icg.lab2.gui.controller.tools.OneToOneController;
import ru.nsu.icg.lab2.gui.controller.tools.WindowSizeController;
import ru.nsu.icg.lab2.gui.controller.tools.transformations.*;
import ru.nsu.icg.lab2.gui.view.*;
import ru.nsu.icg.lab2.gui.view.components.*;
import ru.nsu.icg.lab2.model.ViewConfig;
import ru.nsu.icg.lab2.gui.view.context.ContextImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ViewImpl implements View, ContextListener {
    private final Context context;
    private final DrawingArea drawingArea;
    private final MainWindow mainWindow;

    public ViewImpl(ViewConfig viewConfig, Context context, ImageReader imageReader, ImageWriter imageWriter) {
        FlatArcDarkOrangeIJTheme.setup();

        this.context = context;
        final IconsSupplier iconsSupplier = new IconsSupplier();

        final OpenController openController = new OpenController(context, this, imageReader);
        final SaveController saveController = new SaveController(context, this, imageWriter);
        final ExitController exitController = new ExitController(this);

        final HandController handController = new HandController();
        final UndoController undoController = new UndoController(context);
        final OneToOneController oneToOneController = new OneToOneController();
        final WindowSizeController windowSizeController = new WindowSizeController();
        final RotationController rotationController = new RotationController(context, this);
        final BlackAndWhiteController blackAndWhiteController = new BlackAndWhiteController(context);
        final InversionController inversionController = new InversionController(context);
        final GammaCorrectionController gammaCorrectionController = new GammaCorrectionController(context, this);
        final SharpeningController sharpeningController = new SharpeningController(context);
        final EdgeDetectionController edgeDetectionController = new EdgeDetectionController(context);
        final EmbossingController embossingController = new EmbossingController(context);
        final BlurController blurController = new BlurController(context, this);
        final WatercoloringController watercoloringController = new WatercoloringController(context);
        final FloydSteinbergDitheringController floydSteinbergDitheringController = new FloydSteinbergDitheringController(context, this);
        final OrderedDitheringController orderedDitheringController = new OrderedDitheringController(context, this);
        final WaveFilterController waveFilterController = new WaveFilterController(context);

        final HelpController helpController = new HelpController(this);
        final AboutController aboutController = new AboutController(this);
        final WindowResizeController windowResizeController = new WindowResizeController(context, this);

        drawingArea = new DrawingArea();

        final MenuArea menuArea = new MenuArea(
                openController,
                saveController,
                exitController,
                handController,
                undoController,
                oneToOneController,
                windowSizeController,
                rotationController,
                blackAndWhiteController,
                inversionController,
                gammaCorrectionController,
                sharpeningController,
                edgeDetectionController,
                embossingController,
                blurController,
                watercoloringController,
                floydSteinbergDitheringController,
                orderedDitheringController,
                waveFilterController,
                helpController,
                aboutController
        );

        final ToolsArea toolsArea = new ToolsArea(
                iconsSupplier,
                handController,
                undoController,
                oneToOneController,
                windowSizeController,
                rotationController,
                blackAndWhiteController,
                inversionController,
                gammaCorrectionController,
                sharpeningController,
                edgeDetectionController,
                embossingController,
                blurController,
                watercoloringController,
                floydSteinbergDitheringController,
                orderedDitheringController,
                waveFilterController
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
    public void onImageChange(ContextImpl context) {
        repaint();
    }

    @Override
    public void onTransformationChange(ContextImpl context) {
        context.getTransformation().apply(context.getOriginalImage(), context.getImage());
        repaint();
    }
}
