package ru.nsu.icg.lab2.model.transformations;

import lombok.Setter;
import ru.nsu.icg.lab2.model.ImageFactory;
import ru.nsu.icg.lab2.model.ImageInterface;
import ru.nsu.icg.lab2.model.Transformation;

public class WaveFilter implements Transformation {

    @Setter
    private double ampX;

    @Setter
    private double ampY;

    @Setter
    private double freqX;

    @Setter
    private double freqY;

    @Setter
    private boolean isXFirst;
    private final ImageFactory imageFactory;

    public WaveFilter(ImageFactory imageFactory) {
        this.imageFactory = imageFactory;
        ampX = 10;
        freqX = 0.1;
        ampY = 0;
        freqY = 0;
        isXFirst = true;
    }

    @Override
    public ImageInterface apply(ImageInterface oldImage) {
        int width = oldImage.getWidth();
        int height = oldImage.getHeight();
        int gridSize = width * height;
        int[] grid = new int[gridSize];
        oldImage.getARGB(grid);
        int[] newImage = new int[gridSize];
        if(isXFirst) {
            waveOnX(grid, width, height, newImage);
            if (ampX != 0 && ampY != 0) {
                System.arraycopy(newImage, 0, grid, 0, gridSize);
            }
            waveOnY(grid, width, height, newImage);
        } else {
            waveOnY(grid, width, height, newImage);
            if (ampX != 0 && ampY != 0) {
                System.arraycopy(newImage, 0, grid, 0, gridSize);
            }
            waveOnX(grid, width, height, newImage);
        }

        if (ampX == 0 && ampY == 0) {
            newImage = grid;
        }
        ImageInterface imageInterface = imageFactory.createImage(oldImage.getWidth(), oldImage.getHeight());
        imageInterface.setARGB(newImage);
        return imageInterface;
    }

    private void waveOnX(int[] grid, int width, int height, int[] newGrid) {
        if(ampX == 0){
            return;
        }
        for (int y = 0; y < height; y++) {
            int yDimSize = y * width;
            for (int x = 0; x < width; x++) {
                int newX = x + (int) (ampX * Math.cos(freqX * y));
                if (newX >= 0 && newX < width) {
                    newGrid[yDimSize + newX] = grid[yDimSize + x];
                }
            }
        }
    }

    private void waveOnY(int[] grid, int width, int height, int[] newGrid) {
        if(ampY == 0){
            return;
        }
        for (int y = 0; y < height; y++) {
            int yDimSize = y * width;
            for (int x = 0; x < width; x++) {
                int newY = y + (int) (ampY * Math.sin(freqY * x));
                if (newY >= 0 && newY < height) {
                    newGrid[newY * width + x] = grid[yDimSize + x];
                }
            }
        }

    }
}
