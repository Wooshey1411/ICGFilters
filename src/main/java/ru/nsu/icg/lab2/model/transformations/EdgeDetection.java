package ru.nsu.icg.lab2.model.transformations;

import lombok.Data;
import ru.nsu.icg.lab2.model.ImageInterface;
import ru.nsu.icg.lab2.model.Transformation;

@Data
public class EdgeDetection implements Transformation {
    private int filterSize;

    @Override
    public void apply(ImageInterface oldImage, ImageInterface newImage) {

    }
}
