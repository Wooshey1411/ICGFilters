package ru.nsu.icg.lab2.gui.view;

import lombok.Getter;

import javax.swing.*;
import java.util.Objects;

@Getter
public class IconsSupplier {
    private final ImageIcon handIcon;
    private final ImageIcon oneToOneIcon;
    private final ImageIcon rotationIcon;
    private final ImageIcon greyTransformationIcon;

    public IconsSupplier() {
        handIcon = loadIcon("hand-icon.png");
        oneToOneIcon = loadIcon("one-to-one-icon.png");
        rotationIcon = loadIcon("rotation-icon.png");
        greyTransformationIcon = loadIcon("grey-transformation-icon.png");
    }

    private static ImageIcon loadIcon(String path) {
        return new ImageIcon(Objects.requireNonNull(IconsSupplier.class.getResource("/icons/" + path)));
    }
}
