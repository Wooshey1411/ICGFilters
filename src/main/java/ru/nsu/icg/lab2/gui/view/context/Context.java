package ru.nsu.icg.lab2.gui.view.context;

import lombok.Getter;
import lombok.Setter;
import ru.nsu.icg.lab2.gui.view.BufferedImageImpl;
import ru.nsu.icg.lab2.model.transformations.Transformation;

import java.util.ArrayList;
import java.util.List;

public class Context {
    private final List<ContextListener> listeners = new ArrayList<>();

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

    @Getter
    private Transformation transformation;

    public Context(ViewMode viewMode) {
        this.viewMode = viewMode;
    }

    public void addListener(ContextListener listener) {
        listeners.add(listener);
    }

    public void removeListener(ContextListener listener) {
        listeners.remove(listener);
    }

    public void setImage(BufferedImageImpl image) {
        this.image = image;

        for (final var it : listeners) {
            it.onImageChange(this);
        }
    }

    public void setTransformation(Transformation transformation) {
        this.transformation = transformation;

        for (final var it : listeners) {
            it.onTransformationChange(this);
        }
    }
}
