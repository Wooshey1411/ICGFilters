package ru.nsu.icg.lab2.model.transformations;

import lombok.Getter;
import lombok.Setter;
import ru.nsu.icg.lab2.model.ImageFactory;
import ru.nsu.icg.lab2.model.ImageInterface;

import java.util.Arrays;

@Getter
@Setter
public class FloydSteinbergDithering extends AbstractDithering {

    public FloydSteinbergDithering(ImageFactory imageFactory) {
        super(imageFactory);
        redK = 2;
        blueK = 2;
        greenK = 2;
        creator = FilterCreator.VOROBEV;
    }

    @Override
    public ImageInterface apply(ImageInterface oldImage) {
        switch (creator){
            case SIROTKIN -> {return null;}
            case VOROBEV -> {return VorobevVariant.apply(oldImage, redK, blueK, greenK, getImageFactory());}
            case KONDRENKO -> {return null;}
            default -> throw new IllegalArgumentException("No such creator in Floyd-Steinberg dithering");
        }
    }


    // ACHTUNG !!!!!!!!!!!!!!!!! Не ПИЗДИТЬ и НЕ РЕФАКТОРИТЬ. Забудьте про этот код навсегда! У кого увижу такое - ВЫЕБУ
    private static class VorobevVariant{
        public static ImageInterface apply(ImageInterface oldImage, int redK, int blueK, int greenK, ImageFactory imageFactory){

            int width = oldImage.getWidth();
            int height = oldImage.getHeight();

            int[] redVariants = new int[redK];
            int[] blueVariants = new int[blueK];
            int[] greenVariants = new int[greenK];
            fillVariantArray(redVariants);
            fillVariantArray(blueVariants);
            fillVariantArray(greenVariants);

            int[] redErrors = new int[(width+2) << 1];
            int[] greenErrors = new int[(width+2) << 1];
            int[] blueErrors = new int[(width+2) << 1];
            Arrays.fill(redErrors, 0);
            Arrays.fill(greenErrors, 0);
            Arrays.fill(blueErrors, 0);

            int[] grid = oldImage.getGrid();
            int[] newGrid = new int[width*height];
            Arrays.fill(newGrid, 0);

            int currErrorShift = 1;
            int nextErrorShift = width+1;
            for (int y = 0; y < height; y++){
                int dimYShift = y*width;
                int leftRedError = 0;
                int leftGreenError = 0;
                int leftBlueError = 0;
                for (int x = 0; x < width; x++){
                    int currPixel;
                    int newPixel;
                    int quantError;
                    int currPixelPos = dimYShift + x;
                    //**********Alpha**********\\

                    newGrid[currPixelPos] |= (TransformationUtils.getAlpha(grid[currPixelPos]) << 24);

                    //**********RED**********\\

                    currPixel = TransformationUtils.getRed(grid[currPixelPos]) + (int) Math.round(( (redErrors[currErrorShift + x] + leftRedError) / 16.0));
                    redErrors[currErrorShift + x] = 0;
                    newPixel = findNearest(redVariants,currPixel);
                    newGrid[currPixelPos] |= (newPixel << 16);
                    quantError = currPixel - newPixel;
                    leftRedError = quantError * 7;
                    redErrors[nextErrorShift + x] += quantError * 5;
                    redErrors[nextErrorShift + x - 1] += quantError * 3;
                    redErrors[nextErrorShift + x + 1] += quantError;

                    //**********GREEN**********\\

                    currPixel = TransformationUtils.getGreen(grid[currPixelPos]) + (int) Math.round(( (greenErrors[currErrorShift + x] + leftGreenError) / 16.0));
                    greenErrors[currErrorShift + x] = 0;
                    newPixel = findNearest(greenVariants,currPixel);
                    newGrid[currPixelPos] |= (newPixel << 8);
                    quantError = currPixel - newPixel;
                    leftGreenError = quantError * 7;
                    greenErrors[nextErrorShift + x] += quantError * 5;
                    greenErrors[nextErrorShift + x - 1] += quantError * 3;
                    greenErrors[nextErrorShift + x + 1] += quantError;

                    //**********BLUE**********\\

                    currPixel = TransformationUtils.getBlue(grid[currPixelPos]) + (int) Math.round(( (blueErrors[currErrorShift + x] + leftBlueError) / 16.0));
                    blueErrors[currErrorShift + x] = 0;
                    newPixel = findNearest(blueVariants,currPixel);
                    quantError = currPixel - newPixel;
                    newGrid[currPixelPos] |= (newPixel);
                    leftBlueError = quantError * 7;
                    blueErrors[nextErrorShift + x] += quantError * 5;
                    blueErrors[nextErrorShift + x - 1] += quantError * 3;
                    blueErrors[nextErrorShift + x + 1] += quantError;


                    currErrorShift ^= nextErrorShift;
                    nextErrorShift ^= currErrorShift;
                    currErrorShift ^= nextErrorShift;
                }
            }
            return imageFactory.createImage(oldImage,newGrid);
        }


        private static void fillVariantArray(int[] array){
            double step = 255.0/(array.length-1);
            for (int i = 0; i < array.length-1; i++){
                array[i] = (int)Math.round(i*step);
            }
            array[array.length-1] = 255;
        }

        private static int findNearest(int[] array, int value){
            int error = Math.abs(array[0] - value);
            int index = 0;
            for (int i = 1; i < array.length; i++){
                    int internalError = Math.abs(array[i] - value);
                    if(internalError > error){
                        break;
                    }
                    error = internalError;
                    index = i;
            }
            return array[index];
        }
    }
}
