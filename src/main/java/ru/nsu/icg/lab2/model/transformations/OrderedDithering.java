package ru.nsu.icg.lab2.model.transformations;

import lombok.Data;
import ru.nsu.icg.lab2.model.ImageFactory;
import ru.nsu.icg.lab2.model.ImageInterface;
import ru.nsu.icg.lab2.model.Transformation;

@Data
public class OrderedDithering implements Transformation {
    private int redK;
    private int greenK;
    private int blueK;

    private final ImageFactory imageFactory;

    public OrderedDithering(ImageFactory imageFactory) {
        this.imageFactory = imageFactory;
    }
    @Override
    public ImageInterface apply(ImageInterface oldImage) {
        return null;
    }
}
