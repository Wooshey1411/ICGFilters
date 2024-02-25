package ru.nsu.icg.lab2.model.image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class ImageReaderImpl implements ImageReader {
    private static final String[] SUPPORTED_FORMATS = {"gif", "png", "jpeg", "jpg", "jpeg", "bmp", "wbmp"};

    @Override
    public BufferedImage read(File file) throws IOException {
        return ImageIO.read(file);
    }

    @Override
    public BufferedImage readResource(String resourceName) throws IOException {
        try (InputStream inputStream = this.getClass().getResourceAsStream(resourceName)) {
            if (inputStream == null) {
                throw new IOException(String.format("Resource \"%s\" not found", resourceName));
            }

            return ImageIO.read(inputStream);
        }
    }

    @Override
    public String[] getSupportedFormats() {
        return SUPPORTED_FORMATS.clone();
    }
}
