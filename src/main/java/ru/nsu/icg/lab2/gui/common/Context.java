package ru.nsu.icg.lab2.gui.common;

import lombok.Getter;
import lombok.Setter;
import ru.nsu.icg.lab2.model.Transformation;

import java.util.ArrayList;
import java.util.List;

public class Context {
    private final List<ContextListener> listeners = new ArrayList<>();
    @Setter
    private ViewModeChangeListener viewModeChangeListener;
    @Getter
    private final BufferedImageFactory bufferedImageFactory;
    @Getter
    private ViewMode viewMode;

    @Getter
    @Setter
    private InterpolationMethod interpolationMethod;

    @Getter
    @Setter
    private BufferedImageImpl originalImage;

    @Getter
    @Setter
    private BufferedImageImpl processedImage;

    @Getter
    private BufferedImageImpl currentImage;

    @Getter
    @Setter
    private int drawingAreaWidth;

    @Getter
    @Setter
    private int drawingAreaHeight;

    @Getter
    private Transformation transformation;

    public Context(ViewMode viewMode, BufferedImageFactory bufferedImageFactory) {
        this.viewMode = viewMode;
        this.bufferedImageFactory = bufferedImageFactory;
    }

    public void addListener(ContextListener listener) {
        listeners.add(listener);
    }

    public void removeListener(ContextListener listener) {
        listeners.remove(listener);
    }

    public void setCurrentImage(BufferedImageImpl image) {
        this.currentImage = image;

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

    public void setViewMode(ViewMode viewMode){
        this.viewMode = viewMode;
        viewModeChangeListener.onChangeViewMode(this);
    }
}
