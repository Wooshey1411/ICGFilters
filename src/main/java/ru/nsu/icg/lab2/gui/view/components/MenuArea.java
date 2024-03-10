package ru.nsu.icg.lab2.gui.view.components;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

@Getter
public class MenuArea extends JPanel {
    // TODO: вынести это в конфигурационный файл
    private static final Font FONT = new Font("Go", Font.BOLD, 14);
    private static final Color MENU_BACKGROUND_COLOR = new Color(0.85f, 0.85f, 0.85f);
    private static final Color BUTTONS_FONT_COLOR = new Color(0.14f, 0.13f, 0.13f);

    private final JMenuBar menuBar;

    public MenuArea(
            ActionListener openListener,
            ActionListener saveListener,
            ActionListener exitListener,
            ActionListener handListener,
            ActionListener undoListener,
            ActionListener oneToOneListener,
            ActionListener windowSizeListener,
            ActionListener rotationListener,
            ActionListener blackAndWhiteListener,
            ActionListener inversionListener,
            ActionListener gammaCorrectionListener,
            ActionListener sharpeningListener,
            ActionListener edgeDetectionListener,
            ActionListener embossingListener,
            ActionListener blurListener,
            ActionListener watercoloringListener,
            ActionListener floydSteinbergDitheringListener,
            ActionListener orderedDitheringListener,
            ActionListener waveFilterListener,
            ActionListener helpListener,
            ActionListener aboutListener
    ) {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBackground(MENU_BACKGROUND_COLOR);

        menuBar = new JMenuBar();
        menuBar.setBackground(MENU_BACKGROUND_COLOR);

        menuBar.add(createFileMenu(
                openListener,
                saveListener,
                exitListener
        ));
        menuBar.add(createEditMenu(
                handListener,
                undoListener,
                oneToOneListener,
                windowSizeListener,
                rotationListener,
                blackAndWhiteListener,
                inversionListener,
                gammaCorrectionListener,
                sharpeningListener,
                edgeDetectionListener,
                embossingListener,
                blurListener,
                watercoloringListener,
                floydSteinbergDitheringListener,
                orderedDitheringListener,
                waveFilterListener
        ));
        menuBar.add(createInfoMenu(
                helpListener,
                aboutListener
        ));
    }

    private static JMenu createFileMenu(ActionListener openListener,
                                        ActionListener saveListener,
                                        ActionListener exitListener) {
        final JMenu result = createMenu("File");
        result.add(createMenuItem("Open", openListener));
        result.add(createMenuItem("Save", saveListener));
        result.add(createMenuItem("Exit", exitListener));
        return result;
    }

    private static JMenu createEditMenu(ActionListener handListener,
                                        ActionListener undoListener,
                                        ActionListener oneToOneListener,
                                        ActionListener windowSizeListener,
                                        ActionListener rotationListener,
                                        ActionListener blackAndWhiteListener,
                                        ActionListener inversionListener,
                                        ActionListener gammaCorrectionListener,
                                        ActionListener sharpeningListener,
                                        ActionListener edgeDetectionListener,
                                        ActionListener embossingListener,
                                        ActionListener blurListener,
                                        ActionListener watercoloringListener,
                                        ActionListener floydSteinbergDitheringListener,
                                        ActionListener orderedDitheringListener,
                                        ActionListener waveFilterListener) {
        final JMenu result = createMenu("Edit");
        result.add(createMenuItem("Hand", handListener));
        result.add(createMenuItem("Undo", undoListener));
        result.add(createMenuItem("One-To-One", oneToOneListener));
        result.add(createMenuItem("To window size", windowSizeListener));
        result.add(createMenuItem("Rotation", rotationListener));
        result.add(createMenuItem("Black-And-White", blackAndWhiteListener));
        result.add(createMenuItem("Inversion", inversionListener));
        result.add(createMenuItem("Gamma correction", gammaCorrectionListener));
        result.add(createMenuItem("Sharpening", sharpeningListener));
        result.add(createMenuItem("Edge detection", edgeDetectionListener));
        result.add(createMenuItem("Embossing", embossingListener));
        result.add(createMenuItem("Blur", blurListener));
        result.add(createMenuItem("Watercoloring", watercoloringListener));
        result.add(createMenuItem("Floyd-Steinberg dithering", floydSteinbergDitheringListener));
        result.add(createMenuItem("Ordered dithering", orderedDitheringListener));
        result.add(createMenuItem("Wave filter", waveFilterListener));
        return result;
    }

    private static JMenu createInfoMenu(ActionListener helpListener,
                                        ActionListener aboutListener) {
        final JMenu result = createMenu("Info");
        result.add(createMenuItem("Help", helpListener));
        result.add(createMenuItem("About", aboutListener));
        return result;
    }

    private static JMenu createMenu(String label) {
        JMenu result = new JMenu(label);
        result.setBackground(MENU_BACKGROUND_COLOR);
        result.setForeground(BUTTONS_FONT_COLOR);
        result.setFont(FONT);
        return result;
    }

    private static JMenuItem createMenuItem(String label, ActionListener actionListener) {
        final JMenuItem result = new JMenuItem(label);
        initButton(result, actionListener);
        return result;
    }

    private static void initButton(AbstractButton button, ActionListener actionListener) {
        button.setBorderPainted(false);
        button.setFont(FONT);
        button.setForeground(MENU_BACKGROUND_COLOR);
        button.addActionListener(actionListener);
    }
}
