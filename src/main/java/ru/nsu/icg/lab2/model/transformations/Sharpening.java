package ru.nsu.icg.lab2.model.transformations;

import ru.nsu.icg.lab2.model.ImageFactory;
import ru.nsu.icg.lab2.model.ImageInterface;
import ru.nsu.icg.lab2.model.Transformation;

public class Sharpening implements Transformation {
    private static final int WINDOW_SIZE = 3;

    private static final double[][] MATRIX = {
            {0, -1, 0},
            {-1, 5, -1},
            {0, -1, 0}
    };

    private final FilterApplicator filterApplicator;

    public Sharpening(ImageFactory imageFactory) {
        this.filterApplicator = new FilterApplicator(imageFactory);
    }

    @Override
    public ImageInterface apply(ImageInterface oldImage) {
        return filterApplicator.apply(
                oldImage,
                (red, green, blue) -> {
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
                },
                MATRIX,
                WINDOW_SIZE
        );
    }
}
