package ru.nsu.icg.lab2.gui.view.context;

import lombok.Getter;
import lombok.Setter;
import ru.nsu.icg.lab2.gui.view.BufferedImageImpl;

import java.util.ArrayList;
import java.util.List;

public class Context {
    private final List<ContextImageListener> imageListeners = new ArrayList<>();

    @Getter
    @Setter
    private ViewMode viewMode;

    @Getter
    @Setter
    private BufferedImageImpl originalImage;

    @Getter
    @Setter
    private int drawingAreaWidth;

    @Getter
    @Setter
    private int drawingAreaHeight;

    @Getter
    private BufferedImageImpl image;

    public Context(ViewMode viewMode) {
        this.viewMode = viewMode;
    }

    public void addImageListener(ContextImageListener listener) {
        imageListeners.add(listener);
    }

    public void removeImageListener(ContextImageListener listener) {
        imageListeners.remove(listener);
    }

    public void setImage(BufferedImageImpl image) {
        this.image = image;

        System.out.println(image.bufferedImage());

        for (final var it : imageListeners) {
            it.onImageChange(image.bufferedImage());
        }
    }
}
