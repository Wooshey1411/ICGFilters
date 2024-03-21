package ru.nsu.icg.lab2.model.transformations;

import lombok.Getter;
import lombok.Setter;
import ru.nsu.icg.lab2.model.ImageFactory;
import ru.nsu.icg.lab2.model.ImageInterface;
import ru.nsu.icg.lab2.model.Transformation;

import java.util.Arrays;

@Setter
@Getter
public class Rotation extends Transformation {
    private int degrees;

    public Rotation(ImageFactory imageFactory) {
        super(imageFactory);
        degrees = 0;
    }

    @Override
    public ImageInterface apply(ImageInterface oldImage) {
        int width = oldImage.getWidth();
        int height = oldImage.getHeight();
        int centerX = width / 2;
        int centerY = height / 2;
        double sin = Math.sin(((double) degrees / 180.0) * Math.PI);
        double cos = Math.cos(((double) degrees / 180.0) * Math.PI);

        int topLeftX = (int) ((-centerX) * cos) - (int) (centerY * sin);
        int topLeftY = (int) ((-centerX) * sin) + (int) (centerY * cos);

        int topRightX = (int) (centerX * cos) - (int) (centerY * sin);
        int topRightY = (int) (centerX * sin) + (int) (centerY * cos);

        int botLeftX = (int) ((-centerX) * cos) + (int) (centerY * sin);
        int botLeftY = (int) ((-centerX) * sin) - (int) (centerY * cos);

        int botRightX = (int) (centerX * cos) + (int) (centerY * sin);
        int botRightY = (int) (centerX * sin) - (int) (centerY * cos);

        int newHeight = Math.max(Math.abs(topLeftY - botRightY), Math.abs(topRightY - botLeftY)) + 1;
        int newWidth = Math.max(Math.abs(botRightX - topLeftX), Math.abs(topRightX - botLeftX)) + 1;
        int newGridSize = newHeight * newWidth;
        final int[] oldGrid = oldImage.getGrid();
        final int[] newGrid = new int[newGridSize];
        Arrays.fill(newGrid, 0xFFFFFFFF);

        int newCenterX = newWidth / 2;
        int newCenterY = newHeight / 2;

        for (int y = 0; y < newHeight; y++){
            for (int x = 0; x < newWidth ; x++){
                int index = y * newWidth + x;
                int xR = x - newCenterX;
                int yR = -y + newCenterY;
                int newX = (int) (xR * cos) + (int) (yR * sin);
                int newY = (int) -(xR * sin) + (int) (yR * cos);
                int oldY = (-(newY - centerY));
                int oldX = (newX + centerX);
                if (oldX < 0 || oldX >= width || oldY < 0 || oldY >= height){
                    newGrid[index] |= 0x00FFFFFF;
                    continue;
                }
                int oldIndex = width * oldY + oldX;
                newGrid[index] = oldGrid[oldIndex];
            }
        }

        return getImageFactory().createImage(newWidth, newHeight, newGrid);
    }
}
