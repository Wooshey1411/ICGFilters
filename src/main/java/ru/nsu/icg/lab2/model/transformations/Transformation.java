package ru.nsu.icg.lab2.model.transformations;

import ru.nsu.icg.lab2.model.ImageInterface;

public interface Transformation {
    void apply(ImageInterface oldImage, ImageInterface newImage);
}
