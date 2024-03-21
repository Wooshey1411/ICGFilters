package ru.nsu.icg.lab2.gui.view;

import com.formdev.flatlaf.intellijthemes.FlatArcDarkOrangeIJTheme;
import ru.nsu.icg.lab2.gui.common.*;
import ru.nsu.icg.lab2.gui.common.context.ContextAdapter;
import ru.nsu.icg.lab2.gui.common.context.ContextImageReader;
import ru.nsu.icg.lab2.gui.controller.DrawingAreaController;
import ru.nsu.icg.lab2.gui.controller.FolderImagesScrollingController;
import ru.nsu.icg.lab2.gui.controller.TransformationsController;
import ru.nsu.icg.lab2.gui.controller.WindowResizeController;
import ru.nsu.icg.lab2.gui.controller.menu.*;
import ru.nsu.icg.lab2.model.dto.Tool;
import ru.nsu.icg.lab2.model.dto.view.ViewConfig;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.List;

public class ViewImpl extends ContextAdapter implements View {
    private final Context context;
    private final DrawingArea drawingArea;
    private final MainWindow mainWindow;
    private final JTextPane helpTextPane;
    private final JTextPane aboutTextPane;

    public ViewImpl(ViewConfig viewConfig,
                    List<Tool> tools,
                    Context context,
                    ContextImageReader imageReader,
                    ImageWriter imageWriter) {
        FlatArcDarkOrangeIJTheme.setup();

        this.context = context;
        context.addImageListener(this);
        context.addViewModeListener(this);

        final KeyListener keyListener = new FolderImagesScrollingController(context, this, imageReader);

        final TransformationsController transformationsController = new TransformationsController(this);
        context.addTransformationListener(transformationsController);

        final OpenController openController = new OpenController(this, imageReader);
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
        final DrawingAreaController drawingAreaController = new DrawingAreaController(context, this);

        drawingArea = new DrawingArea(drawingAreaController, viewConfig.textAreaConfig());

        final MenuArea menuArea = new MenuArea(
                openController,
                saveController,
                exitController,
                helpController,
                aboutController,
                toolControllersFactory.getToolControllers(),
                viewConfig.menuAreaConfig()
        );

        final ToolsArea toolsArea = new ToolsArea(
                toolControllersFactory.getToolControllers(),
                viewConfig.toolsAreaConfig(),
                keyListener
        );

        mainWindow = new MainWindow(
                viewConfig.mainWindowConfig(),
                exitController,
                menuArea.getMenuBar(),
                toolsArea,
                drawingArea,
                context,
                windowResizeController
        );

        context.addViewModeListener(menuArea);
        context.addViewModeListener(toolsArea);
        context.addDrawingAreaActionListener(menuArea);
        context.addDrawingAreaActionListener(toolsArea);
        context.addImageListener(toolsArea);
        context.addImageListener(menuArea);
        helpTextPane = createHelpTextPane();
        aboutTextPane = createAboutTextArea();
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

        if (image == null) {
            return;
        }

        final double k = image.getWidth() * 1.0 / image.getHeight();

        int newHeight;
        int newWidth;
        if (context.getDrawingAreaWidth() * 1.0 / context.getDrawingAreaHeight() >= k) {
            newHeight = context.getDrawingAreaHeight();
            newWidth = (int) (newHeight * k);
        } else {
            newWidth = context.getDrawingAreaWidth();
            newHeight = (int) (newWidth / k);
        }

        final BufferedImage resizedImage = new BufferedImage(
                newWidth,
                newHeight,
                BufferedImage.TYPE_INT_ARGB
        );

        final Object hintValue;
        switch (context.getInterpolationMethod()) {
            case BICUBIC -> hintValue = RenderingHints.VALUE_INTERPOLATION_BICUBIC;
            case BILINEAR -> hintValue = RenderingHints.VALUE_INTERPOLATION_BILINEAR;
            case NEAREST_NEIGHBOR -> hintValue = RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR;
            default -> throw new IllegalArgumentException("Unexpected interpolation method");
        }

        final Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hintValue);
        graphics2D.drawImage(image.bufferedImage(), 0, 0, newWidth, newHeight, null);
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
        final BufferedImageImpl image = context.getImage();
        if (image == null) {
            return;
        }
        if (context.getViewMode() == ViewMode.ONE_TO_ONE) {
            drawingArea.resizeSoftly(image.getWidth(), image.getHeight());
            drawingArea.setImage(image.bufferedImage());
            drawingArea.repaint();
            drawingArea.revalidate();
        } else {
            resize();
        }
    }

    @Override
    public void showHelp() {
        JOptionPane.showMessageDialog(
                null,
                helpTextPane,
                "Help",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    @Override
    public void showAbout() {
        JOptionPane.showMessageDialog(
                null,
                aboutTextPane,
                "About",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    @Override
    public boolean showConfirmationDialog(String title, JPanel content) {
        return JOptionPane.showConfirmDialog(
                mainWindow,
                content,
                title,
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION;
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
    public void setWaitCursor() {
        mainWindow.setWaitCursor();
    }

    @Override
    public void setDefaultCursor() {
        mainWindow.setDefaultCursor();
    }

    @Override
    public void onImageChange(Context context) {
        repaint();
    }

    @Override
    public void onChangeViewMode(Context context) {
        if (context.getViewMode() == ViewMode.ON_WINDOW_SIZE) {
            context.setDrawingAreaHeight(mainWindow.getWindowedDrawingAreaHeight());
            context.setDrawingAreaWidth(mainWindow.getWindowedDrawingAreaWidth());
            resize();
            mainWindow.disableScrolls();
        } else {
            mainWindow.enableScrolls();
            repaint();
        }
    }

    private static JTextPane createHelpTextPane() {
        final JTextPane result = createHelpAboutTextPane();
        result.setText(getHelpText());
        return result;
    }

    private static JTextPane createAboutTextArea() {
        final JTextPane result = createHelpAboutTextPane();
        result.setText(getAboutText());
        return result;
    }

    private static String getAboutText() {
        return """
               This program was created by Vorobyov Andrey, Kondrenko Kirill and Sirotkin Michael,
               students of group 21203 in Novosibirsk State University in March 2024 as task for the course
               "engineering and computer graphics"
               """;
    }

    private static JTextPane createHelpAboutTextPane() {
        final JTextPane result = new JTextPane();
        result.setEditable(false);
        result.setBackground(null);
        result.setContentType("text/html");
        return result;
    }

    private static String getHelpText() {
        // TODO: implement (it was taken from paint)
        return """
                 <html>
                     <p><b><i>Simple Paint</i></b> represents a simple program for drawing simple shapes and filling, it has 3 areas</p>
                     <ul>
                     <li><b>Canvas</b> — is a working area where all drawings and fillings are visible</li>
                     <li><b>Menu</b> contains main functionality and consists of</li>
                     <ul>
                         <li>
                             <i>File</i>
                             <ul>
                                 <li><u>Open</u> — opens file, supported formats are: PNG, JPEG, BMP, GIF</li>
                                 <li><u>Save</u> — saves file as PNG-image</li>
                                 <li><u>Exit</u> — exits program without any saving</li>
                             </ul>
                         </li>
                         <li>
                             <i>Edit</i>
                             <ul>
                                 <li><u>Line</u> — draws line between 2 points of mouse-pressing</li>
                                 <li><u>Polygon</u> — draws polygon where mouse is pressed</li>
                                 <li><u>Star</u> — draws star where mouse is pressed, inner radius is equal to outer radius divided by 2</li>
                                 <li><u>Fill</u> — fills 4-connected area that contains point of mouse-pressing</li>
                                 <li><u>Clear</u> — clears <b>Canvas</b></li>
                                 <li><u>Select color</u> — opens dialog window to select current color</li>
                                 <li><u>Select thickness</u> — opens dialog window to select current thickness</li>
                                 <li><u>Select number of vertices</u> — opens dialog window to select current number of vertices</li>
                                 <li><u>Select rotation</u> — opens dialog window to select current rotation</li>
                                 <li><u>Select radius</u> — opens dialog window to select current radius</li>
                             </ul>
                         </li>
                         <li>
                             <i>Info</i>
                             <ul>
                                 <li><u>Help</u> — shows help</li>
                                 <li><u>About</u> — shows about</li>
                             </ul>
                         </li>
                     </ul>
                     <li><b>Tools</b> contains all functionality of <b>Menu</b> and adds additional tools (such as <i>change color to red</i>)</li>
                     </ul>
                     <p>There are <b>parameters</b> that are defined while the program is running</p>
                     <ul>
                         <li><i>color</i> — which color to use during drawing shapes and filling</li>
                         <li><i>thickness</i> — thickness of shapes</li>
                         <li><i>number of vertices</i> — number of vertices for shapes (such as polygons and stars)</li>
                         <li><i>rotation</i> — how many degrees will the figure be rotated clockwise</li>
                         <li><i>radius</i> — determines radius of shapes, for polygons and stars it means radius of the circumcircle</li>
                     </ul>
                </html>""";
    }
}
