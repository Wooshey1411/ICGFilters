package ru.nsu.icg.lab2.gui.common.context;

import lombok.Getter;
import lombok.Setter;
import ru.nsu.icg.lab2.gui.common.*;
import ru.nsu.icg.lab2.model.Transformation;

import java.util.ArrayList;
import java.util.List;

public class Context {
    private final List<ContextImageListener> imageListeners = new ArrayList<>();
    private final List<ContextTransformationListener> transformationListeners = new ArrayList<>();
    private final List<ContextViewModeListener> viewModeListeners = new ArrayList<>();
    private final List<ContextDrawingAreaActionListener> drawingAreaListeners = new ArrayList<>();

    @Getter
    private final BufferedImageFactory bufferedImageFactory;

    @Getter
    private ViewMode viewMode;

    @Getter
    private BufferedImageImpl image;

    @Getter
    private Transformation transformation;

    @Getter
    private DrawingAreaAction drawingAreaAction;

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
    @Setter
    private int drawingAreaWidth;

    @Getter
    @Setter
    private int drawingAreaHeight;

    public Context(BufferedImageFactory bufferedImageFactory) {
        this.drawingAreaAction = DrawingAreaAction.SWAP_IMAGE;
        this.bufferedImageFactory = bufferedImageFactory;
    }

    public void addImageListener(ContextImageListener listener) {
        imageListeners.add(listener);
    }

    public void addTransformationListener(ContextTransformationListener listener) {
        transformationListeners.add(listener);
    }

    public void addViewModeListener(ContextViewModeListener listener) {
        viewModeListeners.add(listener);
    }

    public void addDrawingAreaActionListener(ContextDrawingAreaActionListener listener) {
        drawingAreaListeners.add(listener);
    }

    public void removeImageListener(ContextImageListener listener) {
        imageListeners.remove(listener);
    }

    public void removeTransformationListener(ContextTransformationListener listener) {
        transformationListeners.remove(listener);
    }

    public void removeViewModeListener(ContextViewModeListener listener) {
        viewModeListeners.remove(listener);
    }

    public void removeDrawingAreaActionListener(ContextDrawingAreaActionListener listener) {
        drawingAreaListeners.remove(listener);
    }

    public void setImage(BufferedImageImpl image) {
        this.image = image;

        for (final var it : imageListeners) {
            it.onImageChange(this);
        }
    }

    public void setTransformation(Transformation transformation) {
        this.transformation = transformation;

        for (final var it : transformationListeners) {
            it.onTransformationChange(this);
        }
    }

    public void setViewMode(ViewMode viewMode){
        this.viewMode = viewMode;

        for (final var it : viewModeListeners) {
            it.onChangeViewMode(this);
        }
    }

    public void setDrawingAreaAction(DrawingAreaAction drawingAreaAction) {
        this.drawingAreaAction = drawingAreaAction;

        for (final var it : drawingAreaListeners) {
            it.onDrawingAreaActionChange(this);
        }
    }

    public void swapImage(){
        if(image == originalImage){
            if(processedImage == null){
                return;
            }
            setImage(processedImage);
        } else{
            if(originalImage == null){
                return;
            }
            setImage(originalImage);
        }
    }
}
