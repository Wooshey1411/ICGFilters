package ru.nsu.icg.lab2.gui.view;

import ru.nsu.icg.lab2.gui.view.context.ContextImpl;

public interface ContextListener {
    void onImageChange(ContextImpl context);

    void onTransformationChange(ContextImpl context);
}
