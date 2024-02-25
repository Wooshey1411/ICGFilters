package ru.nsu.icg.lab2.model.image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageSaverImpl implements ImageSaver {
    @Override
    public void saveAsPNG(BufferedImage image, File file) throws IOException {
        ImageIO.write(image, "png", file);
    }
}
