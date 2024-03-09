package ru.nsu.icg.lab2.gui.view.context;

import lombok.Getter;
import lombok.Setter;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class ContextImpl {
    private final List<ContextImageListener> imageListeners = new ArrayList<>();

    @Getter
    @Setter
    private ViewMode viewMode;

    @Getter
    @Setter
    private BufferedImage originalImage;

    @Getter
    private BufferedImage image;

    public void addImageListener(ContextImageListener listener) {
        imageListeners.add(listener);
    }

    public void removeImageListener(ContextImageListener listener) {
        imageListeners.remove(listener);
    }

    public void setImage(BufferedImage image) {
        this.image = image;

        for (final var it : imageListeners) {
            it.onImageChange(image);
        }
    }
}
