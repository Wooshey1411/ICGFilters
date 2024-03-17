package ru.nsu.icg.lab2.model.transformations;

public class TransformationUtils {
    public static int getAlpha(int argb) {
        return (argb & 0xFF000000) >> 24;
    }

    public static int getRed(int argb) {
        return (argb & 0x00FF0000) >> 16;
    }

    public static int getGreen(int argb) {
        return (argb & 0x0000FF00) >> 8;
    }

    public static int getBlue(int argb) {
        return argb & 0x000000FF;
    }

    public static int getARGB(int alpha, int red, int green, int blue) {
        return (alpha << 24) | (red << 16) | (green << 8) | blue;
    }
}
