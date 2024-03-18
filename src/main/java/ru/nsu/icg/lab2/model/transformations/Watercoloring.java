package ru.nsu.icg.lab2.model.transformations;

import lombok.Getter;
import lombok.Setter;
import ru.nsu.icg.lab2.model.ImageFactory;
import ru.nsu.icg.lab2.model.ImageInterface;
import ru.nsu.icg.lab2.model.Transformation;

import java.util.Arrays;

public class Watercoloring extends Transformation {
    private final Sharpening sharpening;

    @Getter
    @Setter
    private int windowSize = 5;

    public Watercoloring(ImageFactory imageFactory) {
        super(imageFactory);
        sharpening = new Sharpening(imageFactory);
    }

    @Override
    public ImageInterface apply(ImageInterface oldImage) {
        int borderStep = (windowSize - 1) / 2;
        int width = oldImage.getWidth();
        int height = oldImage.getHeight();
        int gridSize = height * width;
        int[] grid = new int[gridSize];
        oldImage.getARGB(grid);
        int[] newGrid = new int[gridSize];
        Arrays.fill(newGrid, 0xFF000000);
        int middle = (windowSize * windowSize + 1) / 2 ;
        int[] red = new int[windowSize * windowSize];
        int[] green = new int[windowSize * windowSize];
        int[] blue = new int[windowSize * windowSize];

        //without borders
        for (int y = borderStep; y < height - borderStep; y++){
            for (int x = borderStep; x < width - borderStep; x++){
                int index = y * width + x;
                int i = 0;
                for (int inX = - borderStep; inX <= borderStep; inX++){
                    for (int inY = -borderStep; inY <= borderStep; inY++){
                        int gridIndex = index + inY * width + inX;
                        int pixelColor = grid[gridIndex];
                        red[i] = (pixelColor & 0x00FF0000) >> 16;
                        green[i] = (pixelColor & 0x0000FF00) >> 8;
                        blue[i] = pixelColor & 0x000000FF;
                        i++;
                    }
                }
                Arrays.sort(red);
                Arrays.sort(green);
                Arrays.sort(blue);
                newGrid[index] |= (red[middle] << 16) | (green[middle] << 8) | blue[middle];
            }
        }

        return sharpening.apply(getImageFactory().createImage(oldImage, newGrid));
    }
}
