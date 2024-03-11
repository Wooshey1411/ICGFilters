package ru.nsu.icg.lab2.gui.model;

import ru.nsu.icg.lab2.model.ImageInterface;

import java.awt.image.BufferedImage;

public record BufferedImageImpl(BufferedImage bufferedImage) implements ImageInterface {
    @Override
    public int getWidth() {
        return bufferedImage.getWidth();
    }

    @Override
    public int getHeight() {
        return bufferedImage.getHeight();
    }

    @Override
    public int getARGB(int x, int y) {
        return bufferedImage.getRGB(x, y);
    }

    @Override
    public int[] getARGB(int[] rgbArray) {
        return bufferedImage.getRGB(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), rgbArray, 0, bufferedImage.getWidth());
    }

    @Override
    public int[] getARGB(int startX, int startY, int w, int h, int[] rgbArray, int offset, int scanSize) {
        return bufferedImage.getRGB(startX, startY, w, h, rgbArray, offset, scanSize);
    }

    @Override
    public void setARGB(int x, int y, int rgb) {
        bufferedImage.setRGB(x, y, rgb);
    }

    @Override
    public void setARGB(int[] rgbArray) {
        bufferedImage.setRGB(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), rgbArray, 0, bufferedImage.getWidth());
    }

    @Override
    public void setARGB(int startX, int startY, int w, int h, int[] rgbArray, int offset, int scanSize) {
        bufferedImage.setRGB(startX, startY, w, h, rgbArray, offset, scanSize);
    }
}
