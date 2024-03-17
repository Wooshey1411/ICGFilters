package ru.nsu.icg.lab2.model;

public interface ImageInterface {
    int getWidth();

    int getHeight();

    int getARGB(int x, int y);

    // Returns new array with pixels
    int[] getARGB();

    // Returns new array if rgbArray is null or just returns rgbArray
    int[] getARGB(int[] rgbArray);

    // pixel(x, y) = rgbArray[offset + (y - startY) * scanSize + (x - startX)];
    int[] getARGB(int startX, int startY, int w, int h, int[] rgbArray, int offset, int scanSize);

    void setARGB(int x, int y, int rgb);

    void setARGB(int[] rgbArray);

    // pixel(x, y) = rgbArray[offset + (y - startY) * scanSize + (x - startX)];
    void setARGB(int startX, int startY, int w, int h, int[] rgbArray, int offset, int scanSize);
}
