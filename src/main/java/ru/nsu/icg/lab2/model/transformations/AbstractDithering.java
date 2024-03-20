package ru.nsu.icg.lab2.model.transformations;

import lombok.Getter;
import lombok.Setter;
import ru.nsu.icg.lab2.model.ImageFactory;
import ru.nsu.icg.lab2.model.ImageInterface;
import ru.nsu.icg.lab2.model.Transformation;

@Getter
public abstract class AbstractDithering extends Transformation {

    public enum FilterCreator{
        SIROTKIN,
        VOROBEV,
        KONDRENKO
    }

    @Setter
    protected int redK;
    @Setter
    protected int greenK;
    @Setter
    protected int blueK;
    @Setter
    protected FilterCreator creator;


    protected AbstractDithering(ImageFactory imageFactory) {
        super(imageFactory);
    }

    @Override
    public abstract ImageInterface apply(ImageInterface oldImage);
}
