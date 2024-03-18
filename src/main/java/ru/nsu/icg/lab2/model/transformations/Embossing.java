package ru.nsu.icg.lab2.model.transformations;

import ru.nsu.icg.lab2.model.ImageFactory;
import ru.nsu.icg.lab2.model.ImageInterface;
import ru.nsu.icg.lab2.model.Transformation;

public class Embossing extends Transformation {
    private static final int WINDOW_SIZE = 3;
    private static final double[][] MATRIX = {
            {0, 1 , 0},
            {-1, 0, 1},
            {0, -1, 0}
    };

    private final FilterApplicator filterApplicator;

    public Embossing(ImageFactory imageFactory) {
        super(imageFactory);
        filterApplicator = new FilterApplicator(imageFactory);
        filterApplicator.setWindowSize(WINDOW_SIZE);
        filterApplicator.setMatrix(MATRIX);
        filterApplicator.setCounter((red, green, blue) -> {
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

    @Override
    public ImageInterface apply(ImageInterface oldImage) {
        return filterApplicator.apply(oldImage);
    }
}
