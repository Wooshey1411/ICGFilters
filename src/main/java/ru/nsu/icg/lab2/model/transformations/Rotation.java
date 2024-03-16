package ru.nsu.icg.lab2.model.transformations;

import lombok.Getter;
import lombok.Setter;
import ru.nsu.icg.lab2.model.ImageFactory;
import ru.nsu.icg.lab2.model.ImageInterface;
import ru.nsu.icg.lab2.model.Transformation;

import java.util.Arrays;

public class Rotation implements Transformation {
    private final ImageFactory imageFactory;
    @Setter
    @Getter
    private int degrees;

    public Rotation(ImageFactory imageFactory) {
        this.imageFactory = imageFactory;
    }

    @Override
    public ImageInterface apply(ImageInterface oldImage) {
        System.out.println( "rotation " + degrees);
        int width = oldImage.getWidth();
        int height = oldImage.getHeight();
        int centerX = width / 2;
        int centerY = height / 2;
        double sin = Math.sin(((double) degrees / 180.0) * Math.PI);
        double cos = Math.cos(((double) degrees / 180.0) * Math.PI);
        System.out.println("sin " + sin + " cos " + cos);
        int topLeftX = (int) ((-centerX) * cos) - (int) (centerY * sin);
        int topLeftY = (int) ((-centerX) * sin) + (int) (centerY * cos);
        System.out.println("top left (" + topLeftX + ", " + topLeftY + ")");
        int topRightX = (int) (centerX * cos) - (int) (centerY * sin);
        int topRightY = (int) (centerX * sin) + (int) (centerY * cos);
        System.out.println("top right (" + topRightX + ", " + topRightY + ")");
        int botLeftX = (int) ((-centerX) * cos) + (int) (centerY * sin);
        int botLeftY = (int) ((-centerX) * sin) - (int) (centerY * cos);
        System.out.println("bot left (" + botLeftX + ", " + botLeftY + ")");
        int botRightX = (int) (centerX * cos) + (int) (centerY * sin);
        int botRightY = (int) (centerX * sin) - (int) (centerY * cos);
        System.out.println("bot right (" + botRightX + ", " + botRightY + ")");

        int newHeight = Math.max(Math.abs(topLeftY - botRightY), Math.abs(topRightY - botLeftY)) + 1;
        int newWidth = Math.max(Math.abs(botRightX - topLeftX), Math.abs(topRightX - botLeftX)) + 1;
        System.out.println("new height " + newHeight);
        System.out.println("new width " + newWidth);
        int gridSize = newHeight * newWidth;
        int[] grid = new int[height * width];
        oldImage.getARGB(grid);
        int[] newGrid = new int[gridSize];
        Arrays.fill(newGrid, 0xFFFFFFFF);

        int newCenterX = newWidth / 2;
        int newCenterY = newHeight / 2;


        //without borders
        for (int y = 0; y < height; y++){
            for (int x = 0; x < width ; x++){
                int index = y * width + x;
                int xR = x - centerX;
                int yR = -y + centerY;
                int newX = (int) (xR * cos) - (int) (yR * sin);
                int newY = (int) (xR * sin) + (int) (yR * cos);
                System.out.println("x " + xR +" y " + yR + " newX " + newX + " newY " + newY);
                int newIndex = newWidth * (-(newY - newCenterY)) + (newX + newCenterX);
                newGrid[newIndex] = grid[index];
            }
        }


        ImageInterface imageInterface = imageFactory.createImage(newWidth,newHeight);
        imageInterface.setARGB(newGrid);
        return imageInterface;
    }
}
