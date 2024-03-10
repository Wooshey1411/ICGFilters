package ru.nsu.icg.lab2.model.transformations;

import lombok.Data;
import ru.nsu.icg.lab2.model.ImageInterface;
import ru.nsu.icg.lab2.model.Transformation;

@Data
public class GammaCorrection implements Transformation {
    private double gamma;

    public GammaCorrection() {
        this.gamma = 1;
    }

    @Override
    public void apply(ImageInterface oldImage, ImageInterface newImage) {

        int gridSize = oldImage.getHeight()*oldImage.getWidth();
        int[] grid = new int[gridSize];
        oldImage.getRGB(grid);
        for (int pixel = 0; pixel < gridSize ; pixel++){
                grid[pixel] = 0xFF000000 |
                        ((int)(Math.pow((((grid[pixel] & 0x00FF0000) >> 16)/255.0),gamma) * 255) << 16) |
                        ((int)(Math.pow((((grid[pixel] & 0x0000FF00) >> 8)/255.0),gamma) * 255) << 8) |
                        (int)(Math.pow((((grid[pixel] & 0x000000FF))/255.0),gamma) * 255);
        }

        newImage.setRGB(grid);
    }
}
