package ru.nsu.icg.lab2.model.transformations;

import ru.nsu.icg.lab2.model.ImageFactory;
import ru.nsu.icg.lab2.model.ImageInterface;

import java.util.Arrays;

public class FilterApplicator {


    public interface RGBCounter{
        int color(int red, int green, int blue);
    }



    private final double[][] matrix;

    private final int windowSize;


    private final ImageFactory imageFactory;


    public FilterApplicator(double[][] matrix, int windowSize, ImageFactory imageFactory) {
        this.matrix = matrix;
        this.windowSize = windowSize;
        this.imageFactory = imageFactory;
    }

    public ImageInterface apply(ImageInterface oldImage, RGBCounter counter){
        int borderStep = (windowSize - 1) / 2;
        int width = oldImage.getWidth();
        int height = oldImage.getHeight();
        int gridSize = height * width;
        int[] grid = new int[gridSize];
        oldImage.getARGB(grid);
        int[] newGrid = new int[gridSize];
        Arrays.fill(newGrid, 0xFF000000);

        //without borders
        for (int y = borderStep; y < height - borderStep; y++){
            for (int x = borderStep; x < width - borderStep; x++){
                int index = y * width + x;
                int red = 0;
                int green = 0;
                int blue = 0;
                for (int inX = - borderStep; inX <= borderStep; inX++){
                    for (int inY = -borderStep; inY <= borderStep; inY++){
                        int inXIndex = inX + borderStep;
                        int inYIndex = inY + borderStep;
                        int gridIndex = index + inY * width + inX;
                        int pixelColor = grid[gridIndex];
                        red += (int) ((((pixelColor & 0x00FF0000) >> 16) * matrix[inYIndex][inXIndex]));
                        green += (int) ((((pixelColor & 0x0000FF00) >> 8) * matrix[inYIndex][inXIndex]));
                        blue += (int) ((((pixelColor & 0x000000FF)) * matrix[inYIndex][inXIndex]));
                    }
                }
                newGrid[index] |= counter.color(red, green, blue);
            }
        }

        //process borders
        //top left corner
        for (int y = 0; y < borderStep; y++){
            for (int x = 0; x < borderStep; x++){
                int index = y * width + x;
                int red = 0;
                int green = 0;
                int blue = 0;
                for (int inX = - borderStep; inX <= borderStep; inX++){
                    for (int inY = -borderStep; inY <= borderStep; inY++){
                        int inXIndex = inX + borderStep;
                        int inYIndex = inY + borderStep;
                        int gridIndex = Math.abs(inY + y) * width + Math.abs(inX + x);
                        int pixelColor = grid[gridIndex];
                        red += (int) ((((pixelColor & 0x00FF0000) >> 16) * matrix[inYIndex][inXIndex]));
                        green += (int) ((((pixelColor & 0x0000FF00) >> 8) * matrix[inYIndex][inXIndex]));
                        blue += (int) ((((pixelColor & 0x000000FF)) * matrix[inYIndex][inXIndex]));
                    }
                }
                newGrid[index] |= counter.color(red, green, blue);
            }
        }

        //between top left corner and top right corner
        for (int y = 0; y < borderStep; y++){
            for (int x = borderStep; x < width - borderStep; x++){
                int index = y * width + x;
                int red = 0;
                int green = 0;
                int blue = 0;
                for (int inX = - borderStep; inX <= borderStep; inX++){
                    for (int inY = -borderStep; inY <= borderStep; inY++){
                        int inXIndex = inX + borderStep;
                        int inYIndex = inY + borderStep;
                        int gridIndex = Math.abs(inY + y) * width + inX + x;
                        int pixelColor = grid[gridIndex];
                        red += (int) ((((pixelColor & 0x00FF0000) >> 16) * matrix[inYIndex][inXIndex]));
                        green += (int) ((((pixelColor & 0x0000FF00) >> 8) * matrix[inYIndex][inXIndex]));
                        blue += (int) ((((pixelColor & 0x000000FF)) * matrix[inYIndex][inXIndex]));
                    }
                }
                newGrid[index] |= counter.color(red, green, blue);
            }
        }

        //top right corner
        for (int y = 0; y < borderStep; y++){
            for (int x = width - borderStep; x < width; x++){
                int index = y * width + x;
                int red = 0;
                int green = 0;
                int blue = 0;
                for (int inX = - borderStep; inX <= borderStep; inX++){
                    for (int inY = -borderStep; inY <= borderStep; inY++){
                        int inXIndex = inX + borderStep;
                        int inYIndex = inY + borderStep;
                        int gridIndex = Math.abs(inY + y) * width + ((inX + x) % width);
                        int pixelColor = grid[gridIndex];
                        red += (int) ((((pixelColor & 0x00FF0000) >> 16) * matrix[inYIndex][inXIndex]));
                        green += (int) ((((pixelColor & 0x0000FF00) >> 8) * matrix[inYIndex][inXIndex]));
                        blue += (int) ((((pixelColor & 0x000000FF)) * matrix[inYIndex][inXIndex]));
                    }
                }
                newGrid[index] |= counter.color(red, green, blue);
            }
        }

        //between top right corner and bottom right corner
        for (int y = borderStep; y < height - borderStep; y++){
            for (int x = width - borderStep; x < width; x++){
                int index = y * width + x;
                int red = 0;
                int green = 0;
                int blue = 0;
                for (int inX = - borderStep; inX <= borderStep; inX++){
                    for (int inY = -borderStep; inY <= borderStep; inY++){
                        int inXIndex = inX + borderStep;
                        int inYIndex = inY + borderStep;
                        int gridIndex = (y + inY) * width + ((inX + x) % width);
                        int pixelColor = grid[gridIndex];
                        red += (int) ((((pixelColor & 0x00FF0000) >> 16) * matrix[inYIndex][inXIndex]));
                        green += (int) ((((pixelColor & 0x0000FF00) >> 8) * matrix[inYIndex][inXIndex]));
                        blue += (int) ((((pixelColor & 0x000000FF)) * matrix[inYIndex][inXIndex]));
                    }
                }
                newGrid[index] |= counter.color(red, green, blue);
            }
        }

        //bottom right corner
        for (int y = height - borderStep; y < height; y++){
            for (int x = width - borderStep; x < width; x++){
                int index = y * width + x;
                int red = 0;
                int green = 0;
                int blue = 0;
                for (int inX = - borderStep; inX <= borderStep; inX++){
                    for (int inY = -borderStep; inY <= borderStep; inY++){
                        int inXIndex = inX + borderStep;
                        int inYIndex = inY + borderStep;
                        int gridIndex = ((y + inY) % height)  * width + ((inX + x) % width);
                        int pixelColor = grid[gridIndex];
                        red += (int) ((((pixelColor & 0x00FF0000) >> 16) * matrix[inYIndex][inXIndex]));
                        green += (int) ((((pixelColor & 0x0000FF00) >> 8) * matrix[inYIndex][inXIndex]));
                        blue += (int) ((((pixelColor & 0x000000FF)) * matrix[inYIndex][inXIndex]));
                    }
                }
                newGrid[index] |= counter.color(red, green, blue);
            }
        }

        //between bottom left corner and bottom right corner
        for (int y = height - borderStep; y < height; y++){
            for (int x = borderStep; x < width - borderStep; x++){
                int index = y * width + x;
                int red = 0;
                int green = 0;
                int blue = 0;
                for (int inX = - borderStep; inX <= borderStep; inX++){
                    for (int inY = -borderStep; inY <= borderStep; inY++){
                        int inXIndex = inX + borderStep;
                        int inYIndex = inY + borderStep;
                        int gridIndex = ((y + inY) % height)  * width + inX + x;
                        int pixelColor = grid[gridIndex];
                        red += (int) ((((pixelColor & 0x00FF0000) >> 16) * matrix[inYIndex][inXIndex]));
                        green += (int) ((((pixelColor & 0x0000FF00) >> 8) * matrix[inYIndex][inXIndex]));
                        blue += (int) ((((pixelColor & 0x000000FF)) * matrix[inYIndex][inXIndex]));
                    }
                }
                newGrid[index] |= counter.color(red, green, blue);
            }
        }

        //bottom left corner
        for (int y = height - borderStep; y < height; y++){
            for (int x = 0; x < borderStep; x++){
                int index = y * width + x;
                int red = 0;
                int green = 0;
                int blue = 0;
                for (int inX = - borderStep; inX <= borderStep; inX++){
                    for (int inY = -borderStep; inY <= borderStep; inY++){
                        int inXIndex = inX + borderStep;
                        int inYIndex = inY + borderStep;
                        int gridIndex = ((y + inY) % height)  * width + Math.abs(inX + x);
                        int pixelColor = grid[gridIndex];
                        red += (int) ((((pixelColor & 0x00FF0000) >> 16) * matrix[inYIndex][inXIndex]));
                        green += (int) ((((pixelColor & 0x0000FF00) >> 8) * matrix[inYIndex][inXIndex]));
                        blue += (int) ((((pixelColor & 0x000000FF)) * matrix[inYIndex][inXIndex]));
                    }
                }
                newGrid[index] |= counter.color(red, green, blue);
            }
        }

        //between bottom left corner and top left corner
        for (int y = borderStep; y < height - borderStep; y++){
            for (int x = 0; x < borderStep; x++){
                int index = y * width + x;
                int red = 0;
                int green = 0;
                int blue = 0;
                for (int inX = - borderStep; inX <= borderStep; inX++){
                    for (int inY = -borderStep; inY <= borderStep; inY++){
                        int inXIndex = inX + borderStep;
                        int inYIndex = inY + borderStep;
                        int gridIndex = (y + inY) * width + Math.abs(inX + x);
                        int pixelColor = grid[gridIndex];
                        red += (int) ((((pixelColor & 0x00FF0000) >> 16) * matrix[inYIndex][inXIndex]));
                        green += (int) ((((pixelColor & 0x0000FF00) >> 8) * matrix[inYIndex][inXIndex]));
                        blue += (int) ((((pixelColor & 0x000000FF)) * matrix[inYIndex][inXIndex]));
                    }
                }
                newGrid[index] |= counter.color(red, green, blue) ;
            }
        }

        ImageInterface imageInterface = imageFactory.createImage(oldImage.getWidth(),oldImage.getHeight());
        imageInterface.setARGB(newGrid);
        return imageInterface;
    }
}
