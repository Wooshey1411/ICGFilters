package ru.nsu.icg.lab2.gui.view;

import lombok.Getter;

import javax.swing.*;
import java.net.URL;

@Getter
public class IconsSupplier {
    private final ImageIcon handIcon;
    private final ImageIcon undoIcon;
    private final ImageIcon oneToOneIcon;
    private final ImageIcon windowSizeIcon;
    private final ImageIcon rotationIcon;
    private final ImageIcon blackAndWhiteIcon;
    private final ImageIcon inversionIcon;
    private final ImageIcon gammaCorrectionIcon;
    private final ImageIcon sharpeningIcon;
    private final ImageIcon edgeDetectionIcon;
    private final ImageIcon embossingIcon;
    private final ImageIcon blurIcon;
    private final ImageIcon watercoloringIcon;
    private final ImageIcon floydSteinbergDitheringIcon;
    private final ImageIcon orderedDitheringIcon;
    private final ImageIcon waveFilterIcon;
    private final ImageIcon glassEffectIcon;
    private final ImageIcon fisheyeEffectIcon;

    public IconsSupplier() {
        handIcon = loadIcon("hand.png");
        undoIcon = loadIcon("arrows.png");
        oneToOneIcon = loadIcon("one-to-one.png");
        windowSizeIcon = loadIcon("display.png");
        rotationIcon = loadIcon("rotation.png");
        blackAndWhiteIcon = loadIcon("black-and-white.png");
        inversionIcon = loadIcon("inversion.png");
        gammaCorrectionIcon = loadIcon("gamma-correction.png");
        sharpeningIcon = loadIcon("sharpening.png");
        edgeDetectionIcon = loadIcon("edge-detection.png");
        embossingIcon = loadIcon("embossing.png");
        blurIcon = loadIcon("blur.png");
        watercoloringIcon = loadIcon("watercoloring.png");
        floydSteinbergDitheringIcon = loadIcon("dithering-1.png");
        orderedDitheringIcon = loadIcon("dithering-2.png");
        waveFilterIcon = loadIcon("wave-filter.png");
        glassEffectIcon = loadIcon("glass.png");
        fisheyeEffectIcon = loadIcon("fisheye.png");
    }

    private static ImageIcon loadIcon(String path) {
        final URL url = IconsSupplier.class.getResource("/icons/" + path);

        if (url == null) {
            throw new RuntimeException("Icon not found: " + path);
        }

        return new ImageIcon(url);
    }
}
