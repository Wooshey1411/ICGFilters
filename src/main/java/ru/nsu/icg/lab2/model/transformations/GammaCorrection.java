package ru.nsu.icg.lab2.model.transformations;

import lombok.Data;
import ru.nsu.icg.lab2.model.ImageInterface;
import ru.nsu.icg.lab2.model.Transformation;

import java.awt.*;

@Data
public class GammaCorrection implements Transformation {
    private double gamma;

    public GammaCorrection() {
        this.gamma = 1;
    }

    @Override
    public void apply(ImageInterface oldImage, ImageInterface newImage) {
        // TODO: сделать нормальную реализацию
        for (int y = 0; y < oldImage.getHeight(); y++) {
            for (int x = 0; x < oldImage.getWidth(); x++) {
                final Color oldColor = new Color(oldImage.getRGB(x, y));
                final double oldRed = oldColor.getRed() / 255.0;
                final double oldGreen = oldColor.getGreen() / 255.0;
                final double oldBlue = oldColor.getBlue() / 255.0;
                final float newRed = (float)Math.pow(oldRed, gamma);
                final float newGreen = (float)Math.pow(oldGreen, gamma);
                final float newBlue = (float)Math.pow(oldBlue, gamma);
                final Color newColor = new Color(newRed, newGreen, newBlue);
                newImage.setRGB(x, y, newColor.getRGB());
            }
        }
    }
}
