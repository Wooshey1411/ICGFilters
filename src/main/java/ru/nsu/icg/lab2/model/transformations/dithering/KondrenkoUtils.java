package ru.nsu.icg.lab2.model.transformations.dithering;

public final class KondrenkoUtils {
    private KondrenkoUtils() {}

    public static int findNearestColor(int value, int delta) {
        if (value >= 255) {
            return 255;
        } else if (value < 0) {
            return 0;
        } else {
            return Math.round((float) value / delta) * delta;
        }
    }

    public static int calculateDelta(int paletteSize) {
        return 255 / (paletteSize - 1);
    }
}
