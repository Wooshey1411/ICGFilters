package ru.nsu.icg.lab2.model.transformations;

import ru.nsu.icg.lab2.model.ImageFactory;
import ru.nsu.icg.lab2.model.ImageInterface;
import ru.nsu.icg.lab2.model.Transformation;

public class Watercoloring implements Transformation {

    private final ImageFactory imageFactory;

    public Watercoloring(ImageFactory imageFactory) {
        this.imageFactory = imageFactory;
    }
    @Override
    public ImageInterface apply(ImageInterface oldImage) {
        return null;
    }
}
