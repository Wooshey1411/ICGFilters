package ru.nsu.icg.lab2.model.transformations;

import lombok.Getter;
import ru.nsu.icg.lab2.model.ImageFactory;
import ru.nsu.icg.lab2.model.ImageInterface;
import ru.nsu.icg.lab2.model.Transformation;

public class GammaCorrection implements Transformation {
    private final int[] gammaCorrectionValues = new int[256];

    private final ImageFactory imageFactory;

    @Getter
    private double gamma;

    public GammaCorrection(ImageFactory imageFactory) {
        this.gamma = 1;
        this.imageFactory = imageFactory;
    }

    public void setGamma(double gamma) {
        this.gamma = gamma;

        for (int i = 0; i < 256; i++) {
            gammaCorrectionValues[i] = (int) (Math.pow(i / 255.0, gamma) * 255);
        }
    }

    @Override
    public ImageInterface apply(ImageInterface oldImage) {
        final int gridSize = oldImage.getHeight() * oldImage.getWidth();
        final int[] grid = new int[gridSize];
        oldImage.getARGB(grid);

        for (int pixel = 0; pixel < gridSize; pixel++) {
            final int oldAlpha = TransformationUtils.getAlpha(grid[pixel]);
            final int oldRed = TransformationUtils.getRed(grid[pixel]);
            final int oldGreen = TransformationUtils.getGreen(grid[pixel]);
            final int oldBlue = TransformationUtils.getBlue(grid[pixel]);

            final int newRed = gammaCorrectionValues[oldRed];
            final int newGreen = gammaCorrectionValues[oldGreen];
            final int newBlue = gammaCorrectionValues[oldBlue];

            grid[pixel] = TransformationUtils.getARGB(
                    oldAlpha,
                    newRed,
                    newGreen,
                    newBlue
                );
            }

        final ImageInterface imageInterface = imageFactory.createImage(oldImage);
        imageInterface.setARGB(grid);

        return imageInterface;
    }
}
