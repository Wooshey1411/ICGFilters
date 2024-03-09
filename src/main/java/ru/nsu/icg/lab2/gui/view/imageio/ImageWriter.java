package ru.nsu.icg.lab2.gui.view.imageio;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public interface ImageWriter extends ImageIO {
    void save(BufferedImage image, File file) throws IOException;
}
