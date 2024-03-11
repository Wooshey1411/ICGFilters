package ru.nsu.icg.lab2.model.transformations;

import ru.nsu.icg.lab2.model.ImageInterface;
import ru.nsu.icg.lab2.model.Transformation;

public class BlackAndWhite implements Transformation {
    @Override
    public void apply(ImageInterface oldImage, ImageInterface newImage) {
        int gridSize = oldImage.getHeight() * oldImage.getWidth();
        int[] grid = new int[gridSize];
        oldImage.getARGB(grid);
        for (int pixel = 0; pixel < gridSize; pixel++) {
            int newC =
                    (int) (((grid[pixel] & 0x00FF0000) >> 16) * 0.299) + // r
                            (int) (((grid[pixel] & 0x0000FF00) >> 8) * 0.587) + // g
                            (int) (((grid[pixel] & 0x000000FF)) * 0.114); // b
            grid[pixel] = 0xFF000000 | (newC << 16) | (newC << 8) | newC;
        }
        newImage.setARGB(grid);
    }
}
