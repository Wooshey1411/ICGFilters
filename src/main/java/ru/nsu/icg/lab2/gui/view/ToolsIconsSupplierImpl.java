package ru.nsu.icg.lab2.gui.view;

import lombok.NonNull;

import javax.swing.*;
import java.util.HashMap;
import java.util.Objects;

public class ToolsIconsSupplierImpl implements ToolsIconsSupplier {

    private final HashMap<ToolsIcons, ImageIcon> iconsList = new HashMap<>();

    public ToolsIconsSupplierImpl(){
        iconsList.put(ToolsIcons.HAND,loadIcon("hand-icon.png"));
        iconsList.put(ToolsIcons.DISPLAY,loadIcon("display-icon.png"));
        iconsList.put(ToolsIcons.ROTATION,loadIcon("rotation-icon.png"));
    }
    @Override
    @NonNull
    public ImageIcon getHandIcon() {
        return iconsList.get(ToolsIcons.HAND);
    }

    @Override
    public ImageIcon getDisplayIcon() {
        return iconsList.get(ToolsIcons.DISPLAY);
    }

    @Override
    public ImageIcon getRotationIcon() {
        return iconsList.get(ToolsIcons.ROTATION);
    }


    private ImageIcon loadIcon(@NonNull String path){
       return new ImageIcon(Objects.requireNonNull(ToolsIconsSupplierImpl.class.getResource(path)));
    }
}
