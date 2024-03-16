package ru.nsu.icg.lab2.model.transformations;

import ru.nsu.icg.lab2.model.ImageFactory;
import ru.nsu.icg.lab2.model.ImageInterface;
import ru.nsu.icg.lab2.model.Transformation;

import java.util.Arrays;

public class Embossing implements Transformation {

    private final ImageFactory imageFactory;

    public Embossing(ImageFactory imageFactory) {
        this.imageFactory = imageFactory;
    }
    @Override
    public ImageInterface apply(ImageInterface oldImage) {
        int borderStep = 1;
        int width = oldImage.getWidth();
        int height = oldImage.getHeight();
        int gridSize = height * width;
        int[] grid = new int[gridSize];
        oldImage.getARGB(grid);
        int[] newGrid = new int[gridSize];
        Arrays.fill(newGrid, 0xFF000000);

        int[][] matrixX = {
                {0, 1 , 0},
                {-1, 0, 1},
                {0, -1, 0}
        };

        //without borders
        for (int y = borderStep; y < height - borderStep; y++){
            for (int x = borderStep; x < width - borderStep; x++){
                int index = y * width + x;
                int red = 128;
                int green = 128;
                int blue = 128;
                for (int inX = - borderStep; inX <= borderStep; inX++) {
                    for (int inY = -borderStep; inY <= borderStep; inY++) {
                        int inXIndex = inX + borderStep;
                        int inYIndex = inY + borderStep;
                        int gridIndex = index + inY * width + inX;
                        int pixelColor = grid[gridIndex];
                        red += ((pixelColor & 0x00FF0000) >> 16) * matrixX[inYIndex][inXIndex];
                        green += ((pixelColor & 0x0000FF00) >> 8) * matrixX[inYIndex][inXIndex];
                        blue += (pixelColor & 0x000000FF) * matrixX[inYIndex][inXIndex];
                    }
                }
                if (red > 255){
                    red = 255;
                }
                if (blue > 255){
                    blue = 255;
                }
                if (green > 255){
                    green = 255;
                }
                newGrid[index] |= (red << 16) | (green << 8) | blue;
            }
        }

        ImageInterface imageInterface = imageFactory.createImage(oldImage.getWidth(),oldImage.getHeight());
        imageInterface.setARGB(newGrid);
        return imageInterface;
    }
}
