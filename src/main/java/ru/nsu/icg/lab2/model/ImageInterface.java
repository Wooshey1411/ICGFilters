package ru.nsu.icg.lab2.model;

public interface ImageInterface {
    int getWidth();

    int getHeight();

    int getRGB(int x, int y);

    int[] getRGB(int[] rgbArray);

    // pixel(x, y) = rgbArray[offset + (y - startY) * scanSize + (x - startX)];
    int[] getRGB(int startX, int startY, int w, int h, int[] rgbArray, int offset, int scanSize);

    void setRGB(int x, int y, int rgb);

    void setRGB(int[] rgbArray);

    // pixel(x, y) = rgbArray[offset + (y - startY) * scanSize + (x - startX)];
    void setRGB(int startX, int startY,  int w,  int h, int[] rgbArray, int offset, int scanSize);
}
