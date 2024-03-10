package ru.nsu.icg.lab2.model.transformations;

import lombok.Data;
import ru.nsu.icg.lab2.model.ImageInterface;
import ru.nsu.icg.lab2.model.Transformation;

@Data
public class FloydSteinbergDithering implements Transformation {
    private int redK;
    private int greenK;
    private int blueK;

    @Override
    public void apply(ImageInterface oldImage, ImageInterface newImage) {

    }
}
