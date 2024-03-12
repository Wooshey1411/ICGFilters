package ru.nsu.icg.lab2.model.transformations;

import lombok.Data;
import ru.nsu.icg.lab2.model.ImageFactory;
import ru.nsu.icg.lab2.model.ImageInterface;
import ru.nsu.icg.lab2.model.Transformation;

@Data
public class EdgeDetection implements Transformation {
    private int filterSize;

    private final ImageFactory imageFactory;

    public EdgeDetection(ImageFactory imageFactory) {
        this.imageFactory = imageFactory;
    }

    @Override
    public ImageInterface apply(ImageInterface oldImage) {
        return null;
    }
}
