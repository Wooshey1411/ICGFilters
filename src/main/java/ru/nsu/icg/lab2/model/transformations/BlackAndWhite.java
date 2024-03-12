package ru.nsu.icg.lab2.model.transformations;

import lombok.Getter;
import lombok.Setter;
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
        int gridSize = oldImage.getHeight() * oldImage.getWidth();
        int[] grid = new int[gridSize];
        grid = oldImage.getARGB(null);
        for (int pixel = 0; pixel < gridSize; pixel++) {
            int newC =
                    (int) (((grid[pixel] & 0x00FF0000) >> 16) * 0.299) + // r
                            (int) (((grid[pixel] & 0x0000FF00) >> 8) * 0.587) + // g
                            (int) (((grid[pixel] & 0x000000FF)) * 0.114); // b
            grid[pixel] = (grid[pixel] & 0xFF000000) | (newC << 16) | (newC << 8) | newC;
        }
        ImageInterface imageInterface = imageFactory.createImage(oldImage.getWidth(),oldImage.getHeight());
        imageInterface.setARGB(grid);
        return imageInterface;
    }
}
