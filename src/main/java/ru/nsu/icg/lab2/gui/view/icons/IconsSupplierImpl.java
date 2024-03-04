package ru.nsu.icg.lab2.gui.view.icons;

import javax.swing.*;
import java.util.HashMap;
import java.util.Objects;

public class IconsSupplierImpl implements IconsSupplier {
    private final HashMap<IconTypes, ImageIcon> iconsList = new HashMap<>();

    public IconsSupplierImpl(){
        iconsList.put(IconTypes.HAND,loadIcon("hand-icon.png"));
        iconsList.put(IconTypes.DISPLAY,loadIcon("display-icon.png"));
        iconsList.put(IconTypes.ROTATION,loadIcon("rotation-icon.png"));
    }

    @Override
    public ImageIcon getHandIcon() {
        return iconsList.get(IconTypes.HAND);
    }

    @Override
    public ImageIcon getDisplayIcon() {
        return iconsList.get(IconTypes.DISPLAY);
    }

    @Override
    public ImageIcon getRotationIcon() {
        return iconsList.get(IconTypes.ROTATION);
    }

    private ImageIcon loadIcon(String path){
        return new ImageIcon(Objects.requireNonNull(IconsSupplierImpl.class.getResource("/icons/" + path)));
    }
}
