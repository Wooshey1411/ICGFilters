package ru.nsu.icg.lab2.model.transformations;

import ru.nsu.icg.lab2.model.ImageInterface;

public class GreyTransformation implements Transformation {
    @Override
    public void apply(ImageInterface oldImage, ImageInterface newImage) {
        for (int y = 0; y < oldImage.getHeight(); y++) {
            for (int x = 0; x < oldImage.getWidth(); x++) {
                newImage.setRGB(x, y, 0);
            }
        }
    }
}
