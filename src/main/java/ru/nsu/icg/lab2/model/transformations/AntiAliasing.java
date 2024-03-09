package ru.nsu.icg.lab2.model.transformations;

import lombok.Data;
import ru.nsu.icg.lab2.model.ImageInterface;

@Data
public class AntiAliasing implements Transformation {
    private int filterSize;

    @Override
    public void apply(ImageInterface oldImage, ImageInterface newImage) {

    }
}
