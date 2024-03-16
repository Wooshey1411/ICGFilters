package ru.nsu.icg.lab2.model.transformations;

import ru.nsu.icg.lab2.model.ImageFactory;
import ru.nsu.icg.lab2.model.ImageInterface;
import ru.nsu.icg.lab2.model.Transformation;

public class Blur implements Transformation {
    private final ImageFactory imageFactory;

    private int windowSize = 15;


    private double sigma = 1.5;


    public Blur(ImageFactory imageFactory) {
        this.imageFactory = imageFactory;
    }
    @Override
    public ImageInterface apply(ImageInterface oldImage) {
        double[][] matrix = new double[15][15];
        int borderStep = (windowSize - 1) / 2;
        for (int y = 0; y < windowSize; y++){
            for (int x = 0; x < windowSize; x++){
                int xMoved = x - borderStep;
                int yMoved = y - borderStep;
                matrix[y][x] = (1.0 / (2 * Math.PI * sigma * sigma)) *
                        Math.exp(-((double) (xMoved * xMoved + yMoved * yMoved) /(2 * sigma * sigma)));
            }
        }

        FilterApplicator applicator = new FilterApplicator(matrix, windowSize, imageFactory);
        return applicator.apply(oldImage);

    }
}
