package ru.nsu.icg.lab2.model.transformations;

import lombok.Data;
import ru.nsu.icg.lab2.model.ImageFactory;
import ru.nsu.icg.lab2.model.ImageInterface;
import ru.nsu.icg.lab2.model.Transformation;

@Data
public class GammaCorrection implements Transformation {
    private double gamma;

    private final ImageFactory imageFactory;
    public GammaCorrection(ImageFactory imageFactory) {
        this.gamma = 1;
        this.imageFactory = imageFactory;
    }

    @Override
    public ImageInterface apply(ImageInterface oldImage) {

        int gridSize = oldImage.getHeight() * oldImage.getWidth();
        int[] grid = new int[gridSize];
        oldImage.getARGB(grid);
        for (int pixel = 0; pixel < gridSize; pixel++) {
            grid[pixel] = (grid[pixel] & 0xFF000000) |
                    ((int) (Math.pow((((grid[pixel] & 0x00FF0000) >> 16) / 255.0), gamma) * 255) << 16) |
                    ((int) (Math.pow((((grid[pixel] & 0x0000FF00) >> 8) / 255.0), gamma) * 255) << 8) |
                    (int) (Math.pow((((grid[pixel] & 0x000000FF)) / 255.0), gamma) * 255);
        }

        ImageInterface imageInterface = imageFactory.createImage(oldImage.getWidth(),oldImage.getHeight());
        imageInterface.setARGB(grid);
        return imageInterface;
    }
}
