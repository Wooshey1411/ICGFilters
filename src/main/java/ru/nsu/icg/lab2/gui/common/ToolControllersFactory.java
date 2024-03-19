package ru.nsu.icg.lab2.gui.common;

import ru.nsu.icg.lab2.gui.common.context.Context;
import ru.nsu.icg.lab2.gui.controller.ToolController;
import ru.nsu.icg.lab2.model.ImageFactory;
import ru.nsu.icg.lab2.model.dto.Tool;

import java.util.ArrayList;
import java.util.List;

public class ToolControllersFactory {
    private final List<ToolController> controllers;

    public ToolControllersFactory(Context context, View view, ImageFactory imageFactory, List<Tool> tools) {
        this.controllers = new ArrayList<>();

        final Class<?>[] parameters = new Class<?>[]{Context.class, View.class, ImageFactory.class, Tool.class};

        try {
            for (Tool tool : tools) {
                final var clazz = Class.forName(tool.controllerClassPath());
                final var constructor = clazz.getDeclaredConstructor(parameters);
                controllers.add((ToolController) constructor.newInstance(context, view, imageFactory, tool));
            }
        } catch (Exception exception) {
            throw new RuntimeException("Error during initializing tool-controllers-factory", exception);
        }
    }

    public List<ToolController> getToolControllers() {
        return List.copyOf(controllers);
    }
}
