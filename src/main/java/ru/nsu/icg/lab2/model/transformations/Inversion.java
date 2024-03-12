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
        int gridSize = oldImage.getHeight() * oldImage.getWidth();
        int[] grid = new int[gridSize];
        oldImage.getARGB(grid);
        for (int pixel = 0; pixel < gridSize; pixel++) {
            grid[pixel] = (grid[pixel] & 0xFF000000) |
                    ((255 - ((grid[pixel] & 0x00FF0000) >> 16)) << 16) |
                    ((255 - ((grid[pixel] & 0x0000FF00) >> 8)) << 8) |
                    ((255 - ((grid[pixel] & 0x000000FF))));
        }
        ImageInterface imageInterface = imageFactory.createImage(oldImage.getWidth(),oldImage.getHeight());
        imageInterface.setARGB(grid);
        return imageInterface;
    }
}
