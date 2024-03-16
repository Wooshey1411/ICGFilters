package ru.nsu.icg.lab2.model.transformations;

import lombok.Data;
import ru.nsu.icg.lab2.model.ImageFactory;
import ru.nsu.icg.lab2.model.ImageInterface;
import ru.nsu.icg.lab2.model.Transformation;

import java.util.Arrays;

@Data
public class EdgeDetection implements Transformation {
    public enum FilterType{
        ROBERTS, SOBEL
    }


    private FilterType type = FilterType.SOBEL;


    private int brightnessBorder = 128;

    private final ImageFactory imageFactory;

    public EdgeDetection(ImageFactory imageFactory) {
        this.imageFactory = imageFactory;
    }


    private ImageInterface applyRoberts(ImageInterface oldImage){
        int borderStep = 1;
        int width = oldImage.getWidth();
        int height = oldImage.getHeight();
        int gridSize = height * width;
        int[] grid = new int[gridSize];
        oldImage.getARGB(grid);
        int[] newGrid = new int[gridSize];
        Arrays.fill(newGrid, 0xFF000000);

        //without borders
        for (int y = 0; y < height - borderStep; y++){
            for (int x = 0; x < width - borderStep; x++){
                int index = y * width + x;
                int colorTopLeft = grid[index];
                int colorTopRight = grid[index + 1];
                int colorBottomLeft = grid[index + width];
                int colorBottomRight = grid[index + width + 1];
                int red = 0;
                int green = 0;
                int blue = 0;

                int colorXRed = ((colorBottomRight & 0x00FF0000) >> 16) - ((colorTopLeft & 0x00FF0000) >> 16);
                int colorYRed = ((colorBottomLeft & 0x00FF0000) >> 16) - ((colorTopRight & 0x00FF0000) >> 16);
                int colorXGreen = ((colorBottomRight & 0x0000FF00) >> 8) - ((colorTopLeft & 0x0000FF00) >> 8);
                int colorYGreen = ((colorBottomLeft & 0x0000FF00) >> 8) - ((colorTopRight & 0x0000FF00) >> 8);
                int colorXBlue = ((colorBottomRight & 0x000000FF)) - ((colorTopLeft & 0x000000FF));
                int colorYBlue = ((colorBottomLeft & 0x000000FF)) - ((colorTopRight & 0x000000FF));
                int deltaRed = (int) Math.sqrt(colorXRed * colorXRed + colorYRed * colorYRed);
                int deltaGreen = (int) Math.sqrt(colorXGreen * colorXGreen + colorYGreen * colorYGreen);
                int deltaBlue = (int) Math.sqrt(colorXBlue * colorXBlue + colorYBlue * colorYBlue);

                if (deltaRed > brightnessBorder || deltaGreen > brightnessBorder || deltaBlue > brightnessBorder){
                    red = 255;
                    green = 255;
                    blue = 255;
                }

                newGrid[index] |= (red << 16) | (green << 8) | blue;
            }
        }

        ImageInterface imageInterface = imageFactory.createImage(oldImage.getWidth(),oldImage.getHeight());
        imageInterface.setARGB(newGrid);
        return imageInterface;
    }


    private ImageInterface applySobel(ImageInterface oldImage){
        int borderStep = 1;
        int width = oldImage.getWidth();
        int height = oldImage.getHeight();
        int gridSize = height * width;
        int[] grid = new int[gridSize];
        oldImage.getARGB(grid);
        int[] newGrid = new int[gridSize];
        Arrays.fill(newGrid, 0xFF000000);

        int[][] matrixX = {
                {-1, -2 , -1},
                {0, 0, 0},
                {1, 2, 1}
        };

        int[][] matrixY = {
                {-1, 0, 1},
                {-2, 0, 2},
                {-1, 0, 1}
        };

        //without borders
        for (int y = borderStep; y < height - borderStep; y++){
            for (int x = borderStep; x < width - borderStep; x++){
                int index = y * width + x;
                int redX = 0;
                int greenX = 0;
                int blueX = 0;
                int redY = 0;
                int greenY = 0;
                int blueY = 0;
                for (int inX = - borderStep; inX <= borderStep; inX++){
                    for (int inY = -borderStep; inY <= borderStep; inY++){
                        int inXIndex = inX + borderStep;
                        int inYIndex = inY + borderStep;
                        int gridIndex = index + inY * width + inX;
                        int pixelColor = grid[gridIndex];
                        int colorRed = (pixelColor & 0x00FF0000) >> 16;
                        int colorGreen = (pixelColor & 0x0000FF00) >> 8;
                        int colorBlue = pixelColor & 0x000000FF;
                        redX += colorRed * matrixX[inYIndex][inXIndex];
                        greenX += colorGreen * matrixX[inYIndex][inXIndex];
                        blueX += colorBlue * matrixX[inYIndex][inXIndex];
                        redY += colorRed * matrixY[inYIndex][inXIndex];
                        greenY +=  colorGreen * matrixY[inYIndex][inXIndex];
                        blueY +=  colorBlue * matrixY[inYIndex][inXIndex];
                    }
                }
                int deltaRed = (int) Math.sqrt(redX * redX + redY * redY);
                int deltaBlue = (int) Math.sqrt(blueX * blueX + blueY * blueY);
                int deltaGreen = (int) Math.sqrt(greenX * greenX + greenY * greenY);
                int red = 0;
                int green = 0;
                int blue = 0;

                if (deltaRed > brightnessBorder || deltaGreen > brightnessBorder || deltaBlue > brightnessBorder){
                    red = 255;
                    green = 255;
                    blue = 255;
                }

                newGrid[index] |= (red << 16) | (green << 8) | blue;
            }
        }

        ImageInterface imageInterface = imageFactory.createImage(oldImage.getWidth(),oldImage.getHeight());
        imageInterface.setARGB(newGrid);
        return imageInterface;
    }


    @Override
    public ImageInterface apply(ImageInterface oldImage) {
        if (type == FilterType.SOBEL){
            return applySobel(oldImage);
        }
        return applyRoberts(oldImage);
    }
}
