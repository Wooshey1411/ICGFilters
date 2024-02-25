package ru.nsu.icg.lab2.model.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public interface ImageReader {
    BufferedImage read(File file) throws IOException;

    BufferedImage readResource(String resourceName) throws IOException;

    String[] getSupportedFormats();
}
