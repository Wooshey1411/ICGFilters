package ru.nsu.icg.lab2.gui.view;

import ru.nsu.icg.lab2.model.Transformation;

public interface Context {
    ViewMode getViewMode();

    void setViewMode(ViewMode viewMode);

    BufferedImageImpl getImage();

    void setImage(BufferedImageImpl image);

    BufferedImageImpl getOriginalImage();

    void setOriginalImage(BufferedImageImpl originalImage);

    Transformation getTransformation();

    void setTransformation(Transformation transformation);

    int getDrawingAreaWidth();

    void setDrawingAreaWidth(int drawingAreaWidth);

    int getDrawingAreaHeight();

    void setDrawingAreaHeight(int drawingAreaHeight);

    void addListener(ContextListener contextListener);

    void removeListener(ContextListener listener);
}
