package ru.nsu.icg.lab2.model.context;

import lombok.Getter;
import lombok.Setter;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Context {
    private final List<ContextListener> listeners;

    @Getter
    @Setter
    private BufferedImage originalImage;

    @Getter
    @Setter
    private BufferedImage processedImage;

    @Getter
    @Setter
    private String errorMessage;

    @Getter
    private ContextAction action;

    public Context() {
        listeners = new ArrayList<>();
    }

    public void addListener(ContextListener listener) {
        listeners.add(listener);
    }

    public void removeListener(ContextListener listener) {
        listeners.remove(listener);
    }

    public void setAction(ContextAction action) {
        this.action = action;

        for (final ContextListener listener : listeners) {
            listener.onContextActionChange(this, action);
        }
    }

    public void pushError(String errorMessage) {
        this.errorMessage = errorMessage;
        setAction(ContextAction.DISPLAY_ERROR);
    }
}
