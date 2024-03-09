package ru.nsu.icg.lab2.gui.view.imageio;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public interface ImageReader extends ImageIO {
    BufferedImage read(File file) throws IOException;

    BufferedImage readResource(String resourceName) throws IOException;
}
