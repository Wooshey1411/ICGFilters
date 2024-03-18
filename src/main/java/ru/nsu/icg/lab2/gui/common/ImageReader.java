package ru.nsu.icg.lab2.gui.common;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class ImageReader {
    private static final String[] SUPPORTED_FORMATS = {"GIF", "PNG", "JPEG", "JPG", "BMP"};

    public BufferedImage read(File file) throws IOException {
        return ImageIO.read(file);
    }

    public BufferedImage readResource(String resourceName) throws IOException {
        try (InputStream inputStream = this.getClass().getResourceAsStream(resourceName)) {
            if (inputStream == null) {
                throw new IOException(String.format("Resource \"%s\" not found", resourceName));
            }

            return ImageIO.read(inputStream);
        }
    }

    public String[] getSupportedFormats() {
        return SUPPORTED_FORMATS.clone();
    }
}
