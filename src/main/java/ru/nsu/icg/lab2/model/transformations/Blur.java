package ru.nsu.icg.lab2.model.transformations;

import ru.nsu.icg.lab2.model.ImageFactory;
import ru.nsu.icg.lab2.model.ImageInterface;
import ru.nsu.icg.lab2.model.Transformation;

import java.util.Arrays;

public class Blur implements Transformation {
    private final ImageFactory imageFactory;

    private int windowSize = 15;


    private double sigma = 1.5;


    private double[][] matrix = new double[15][15];

    public Blur(ImageFactory imageFactory) {
        this.imageFactory = imageFactory;
    }
    @Override
    public ImageInterface apply(ImageInterface oldImage) {
        int borderStep = (windowSize - 1) / 2;
        for (int y = 0; y < windowSize; y++){
            for (int x = 0; x < windowSize; x++){
                int xMoved = x - borderStep;
                int yMoved = y - borderStep;
                matrix[y][x] = (1.0 / (2 * Math.PI * sigma * sigma)) *
                        Math.exp(-((double) (xMoved * xMoved + yMoved * yMoved) /(2 * sigma * sigma)));
            }
        }
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
                newGrid[index] |= (red << 16) | (green << 8) | blue;
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
                newGrid[index] |= (red << 16) | (green << 8) | blue;
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
                newGrid[index] |= (red << 16) | (green << 8) | blue;
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
                newGrid[index] |= (red << 16) | (green << 8) | blue;
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
                newGrid[index] |= (red << 16) | (green << 8) | blue;
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
                newGrid[index] |= (red << 16) | (green << 8) | blue;
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
                newGrid[index] |= (red << 16) | (green << 8) | blue;
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
                newGrid[index] |= (red << 16) | (green << 8) | blue;
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
                newGrid[index] |= (red << 16) | (green << 8) | blue;
            }
        }

        ImageInterface imageInterface = imageFactory.createImage(oldImage.getWidth(),oldImage.getHeight());
        imageInterface.setARGB(newGrid);
        return imageInterface;

    }
}
