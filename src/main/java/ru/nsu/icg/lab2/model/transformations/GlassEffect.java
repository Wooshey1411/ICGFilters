package ru.nsu.icg.lab2.model.transformations;

import lombok.Getter;
import lombok.Setter;
import ru.nsu.icg.lab2.model.ImageFactory;
import ru.nsu.icg.lab2.model.ImageInterface;
import ru.nsu.icg.lab2.model.Transformation;

import java.util.Random;

public class GlassEffect extends Transformation {
    private final Random random;

    @Getter
    @Setter
    private int spreadInPercent;

    public GlassEffect(ImageFactory imageFactory) {
        super(imageFactory);
        random = new Random(0);
        spreadInPercent = 1;
    }

    @Override
    public ImageInterface apply(ImageInterface oldImage) {
        final int height = oldImage.getHeight();
        final int width = oldImage.getWidth();
        final int[] grid = oldImage.getGrid();

        int trueSpread = (int)(Integer.min(height,width)*spreadInPercent*1.0/1000);

        if (trueSpread == 0){
            return getImageFactory().createImage(oldImage,grid);
        }

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                final int offset = random.nextInt(0, trueSpread);
                final int x1 = Integer.min(x + offset, width - 1);
                final int y1 = Integer.min(y + offset, height - 1);
                final int i = x + y * width;
                final int j = x1 + y1 * width;
                grid[i] = grid[j];
            }
        }

        return getImageFactory().createImage(oldImage, grid);
    }
}
