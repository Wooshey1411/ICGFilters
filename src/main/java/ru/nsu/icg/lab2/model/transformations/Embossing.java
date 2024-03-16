package ru.nsu.icg.lab2.model.transformations;

import ru.nsu.icg.lab2.model.ImageFactory;
import ru.nsu.icg.lab2.model.ImageInterface;
import ru.nsu.icg.lab2.model.Transformation;

public class Embossing implements Transformation {

    private final ImageFactory imageFactory;

    public Embossing(ImageFactory imageFactory) {
        this.imageFactory = imageFactory;
    }
    @Override
    public ImageInterface apply(ImageInterface oldImage) {
        double[][] matrix = {
                {0, 1 , 0},
                {-1, 0, 1},
                {0, -1, 0}
        };

        FilterApplicator applicator = new FilterApplicator(matrix, 3, imageFactory);

        return applicator.apply(oldImage, (red, green, blue) -> {
            red += 128;
            green += 128;
            blue += 128;
            red = Math.abs(red);
            green = Math.abs(green);
            blue = Math.abs(blue);
            if (red > 255){
                red = 255;
            }
            if (blue > 255){
                blue = 255;
            }
            if (green > 255){
                green = 255;
            }
            return (red << 16) | (green << 8) | blue;
        });
    }
}
