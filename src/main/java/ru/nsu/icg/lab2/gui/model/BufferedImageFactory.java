package ru.nsu.icg.lab2.gui.model;

import lombok.Data;
import ru.nsu.icg.lab2.model.ImageFactory;
import ru.nsu.icg.lab2.model.ImageInterface;

import java.awt.image.BufferedImage;

@Data
public class BufferedImageFactory implements ImageFactory {
    private int type;

    @Override
    public ImageInterface createImage(int width, int height) {
        return new BufferedImageImpl(new BufferedImage(width, height, type));
    }

    @Override
    public ImageInterface createImage(ImageInterface imageInterface) {
        return createImage(imageInterface.getWidth(), imageInterface.getHeight());
    }
}
