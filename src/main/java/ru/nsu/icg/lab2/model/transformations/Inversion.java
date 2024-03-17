package ru.nsu.icg.lab2.model.transformations;

import ru.nsu.icg.lab2.model.ImageFactory;
import ru.nsu.icg.lab2.model.ImageInterface;
import ru.nsu.icg.lab2.model.Transformation;

public class Inversion implements Transformation {
    private final ImageFactory imageFactory;

    public Inversion(ImageFactory imageFactory) {
        this.imageFactory = imageFactory;
    }

    @Override
    public ImageInterface apply(ImageInterface oldImage) {
        final int gridSize = oldImage.getGridSize();
        final int[] grid = oldImage.getARGB();

        for (int pixel = 0; pixel < gridSize; pixel++) {
            final int oldAlpha = TransformationUtils.getAlpha(grid[pixel]);
            final int oldRed = TransformationUtils.getRed(grid[pixel]);
            final int oldGreen = TransformationUtils.getGreen(grid[pixel]);
            final int oldBlue = TransformationUtils.getBlue(grid[pixel]);

            final int newRed = getInvertedValue(oldRed);
            final int newGreen = getInvertedValue(oldGreen);
            final int newBlue = getInvertedValue(oldBlue);

            grid[pixel] = TransformationUtils.getARGB(
                    oldAlpha,
                    newRed,
                    newGreen,
                    newBlue
            );
        }

        return imageFactory.createImage(oldImage, grid);
    }

    private static int getInvertedValue(final int value) {
        return 255 - value;
    }
}
