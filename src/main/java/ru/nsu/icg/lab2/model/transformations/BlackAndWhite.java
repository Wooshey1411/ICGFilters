package ru.nsu.icg.lab2.model.transformations;

import ru.nsu.icg.lab2.model.ImageFactory;
import ru.nsu.icg.lab2.model.ImageInterface;
import ru.nsu.icg.lab2.model.Transformation;

public class BlackAndWhite implements Transformation {
    private final ImageFactory imageFactory;

    public BlackAndWhite(ImageFactory imageFactory) {
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

            final int newComponent = getBlackAndWhite(oldRed, oldGreen, oldBlue);

            grid[pixel] = TransformationUtils.getARGB(
                    oldAlpha,
                    newComponent,
                    newComponent,
                    newComponent
            );
        }

        return imageFactory.createImage(oldImage, grid);
    }

    private static int getBlackAndWhite(int red, int green, int blue) {
        return (int)(0.299 * red + 0.587 * green + 0.114 * blue);
    }
}
