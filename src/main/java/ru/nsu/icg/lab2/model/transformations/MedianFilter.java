package ru.nsu.icg.lab2.model.transformations;

import lombok.Getter;
import lombok.Setter;
import ru.nsu.icg.lab2.model.ImageFactory;
import ru.nsu.icg.lab2.model.ImageInterface;
import ru.nsu.icg.lab2.model.Transformation;

import java.util.Arrays;

@Setter
@Getter
public class MedianFilter extends Transformation {
    private int windowSize = 5;

    public MedianFilter(ImageFactory imageFactory) {
        super(imageFactory);
    }

    @Override
    public ImageInterface apply(ImageInterface oldImage) {
        final int windowVolume = windowSize * windowSize;
        final int radius = (windowSize - 1) / 2;
        final int middle = (windowVolume + 1) / 2 ;
        final int width = oldImage.getWidth();
        final int height = oldImage.getHeight();
        final int[] oldGrid = oldImage.getGrid();
        final int[] newGrid = new int[oldGrid.length];
        final int[] red = new int[windowVolume];
        final int[] green = new int[windowVolume];
        final int[] blue = new int[windowVolume];

        // Process center of image
        for (int y = radius; y < height - radius; y++){
            for (int x = radius; x < width - radius; x++){
                final int index = y * width + x;
                final int oldAlpha = TransformationUtils.getAlpha(oldGrid[index]);

                getSortedNeighbours(
                        radius,
                        index,
                        width,
                        oldGrid,
                        red,
                        green,
                        blue
                );

                newGrid[index] = TransformationUtils.getARGB(
                        oldAlpha,
                        red[middle],
                        green[middle],
                        blue[middle]
                );
            }
        }

        // Process borders
        for (int y = 0; y < radius; y++) {
            System.arraycopy(oldGrid, y * width, newGrid, y * width, width);
        }
        for (int y = height - radius; y < height; y++) {
            System.arraycopy(oldGrid, y * width, newGrid, y * width, width);
        }
        for (int x = 0; x < radius; x++) {
            for (int y = radius; y < height - radius; y++) {
                newGrid[y * width + x] = oldGrid[y * width + x];
            }
        }
        for (int x = width - radius; x < width; x++) {
            for (int y = radius; y < height - radius; y++) {
                newGrid[y * width + x] = oldGrid[y * width + x];
            }
        }

        return getImageFactory().createImage(oldImage, newGrid);
    }

    private static void getSortedNeighbours(
            int radius,
            int offset,
            int width,
            int[] grid,
            int[] red,
            int[] green,
            int[] blue
    ) {
        int componentIndex = 0;
        for (int inX = - radius; inX <= radius; inX++){
            for (int inY = -radius; inY <= radius; inY++){
                final int gridIndex = offset + inY * width + inX;
                final int pixelColor = grid[gridIndex];
                red[componentIndex] = TransformationUtils.getRed(pixelColor);
                green[componentIndex] = TransformationUtils.getGreen(pixelColor);
                blue[componentIndex] = TransformationUtils.getBlue(pixelColor);
                componentIndex++;
            }
        }

        Arrays.sort(red);
        Arrays.sort(green);
        Arrays.sort(blue);
    }
}
