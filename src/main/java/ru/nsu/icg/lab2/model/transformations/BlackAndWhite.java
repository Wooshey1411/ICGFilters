package ru.nsu.icg.lab2.model.transformations;

import ru.nsu.icg.lab2.model.ImageInterface;
import ru.nsu.icg.lab2.model.Transformation;

import java.awt.*;

public class BlackAndWhite implements Transformation {
    @Override
    public void apply(ImageInterface oldImage, ImageInterface newImage) {
        // TODO: сделать нормальную реализацию
        for (int y = 0; y < oldImage.getHeight(); y++) {
            for (int x = 0; x < oldImage.getWidth(); x++) {
                final Color oldColor = new Color(oldImage.getRGB(x, y));
                final int oldRed = oldColor.getRed();
                final int oldGreen = oldColor.getGreen();
                final int oldBlue = oldColor.getBlue();
                final int result = (int)(0.299 * oldRed + 0.587 * oldGreen + 0.114 * oldBlue);
                final Color newColor = new Color(result, result, result);
                newImage.setRGB(x, y, newColor.getRGB());
            }
        }
    }
}
