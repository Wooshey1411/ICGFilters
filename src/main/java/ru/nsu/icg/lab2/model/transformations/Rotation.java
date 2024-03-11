package ru.nsu.icg.lab2.model.transformations;

import lombok.Getter;
import lombok.Setter;
import ru.nsu.icg.lab2.model.ImageFactory;
import ru.nsu.icg.lab2.model.ImageInterface;
import ru.nsu.icg.lab2.model.Transformation;

public class Rotation implements Transformation {
    private final ImageFactory imageFactory;
    @Setter
    @Getter
    private int degrees;

    public Rotation(ImageFactory imageFactory) {
        this.imageFactory = imageFactory;
    }

    @Override
    public void apply(ImageInterface oldImage, ImageInterface newImage) {
        // TODO: implement
    }
}
