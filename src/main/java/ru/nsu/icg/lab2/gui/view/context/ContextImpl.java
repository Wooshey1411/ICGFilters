package ru.nsu.icg.lab2.gui.view.context;

import lombok.Getter;
import lombok.Setter;
import ru.nsu.icg.lab2.gui.view.BufferedImageImpl;
import ru.nsu.icg.lab2.gui.view.Context;
import ru.nsu.icg.lab2.gui.view.ContextListener;
import ru.nsu.icg.lab2.gui.view.ViewMode;
import ru.nsu.icg.lab2.model.Transformation;

import java.util.ArrayList;
import java.util.List;

public class ContextImpl implements Context {
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

    public ContextImpl(ViewMode viewMode) {
        this.viewMode = viewMode;
    }

    @Override
    public void addListener(ContextListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(ContextListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void setImage(BufferedImageImpl image) {
        this.image = image;

        for (final var it : listeners) {
            it.onImageChange(this);
        }
    }

    @Override
    public void setTransformation(Transformation transformation) {
        this.transformation = transformation;

        for (final var it : listeners) {
            it.onTransformationChange(this);
        }
    }
}
