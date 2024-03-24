package ru.nsu.icg.lab2.model.transformations.dithering;

import ru.nsu.icg.lab2.model.ImageFactory;
import ru.nsu.icg.lab2.model.ImageInterface;
import ru.nsu.icg.lab2.model.transformations.AbstractDithering;

public class OrderedDithering extends AbstractDithering {
    private static final double[][] MATRIX2 = {
            {0, 2},
            {3, 1}
    };

    private static final double[][] MATRIX4 = {
            {0, 8, 2, 10},
            {12, 4, 14, 6},
            {3, 11, 1, 9},
            {15, 7, 13, 5}
    };

    private static final double[][] MATRIX8 = {
            {0, 32, 8, 40, 2, 34, 10, 42},
            {48, 16, 56, 24, 50, 18, 58, 26},
            {12, 44, 4, 36, 14, 46, 6, 38},
            {60, 28, 52, 20, 62, 30, 54, 22},
            {3, 35, 11, 43, 1, 33, 9, 41},
            {51, 19, 59, 27, 49, 17, 57, 25},
            {15, 47, 7, 39, 13, 45, 5, 37},
            {63, 31, 55, 23, 61, 29, 53, 21}
    };

    private static final double[][] MATRIX16 = {
            {0, 128, 32, 160, 8, 136, 40, 168, 2, 130, 34, 162, 10, 138, 42, 170},
            {192, 64, 224, 96, 200, 72, 232, 104, 194, 66, 226, 98, 202, 74, 234, 106},
            {48, 176, 16, 144, 56, 184, 24, 152, 50, 178, 18, 146, 58, 186, 26, 154 },
            {240, 112, 208, 80, 248, 120, 216, 88, 242, 114, 210, 82, 250, 122, 218, 90 },
            {12, 140, 44, 172, 4, 132, 36, 164, 14, 142, 46, 174, 6, 134, 38, 166},
            {204, 76, 236, 108, 196, 68, 228, 100, 206, 78, 238, 110, 198, 70, 230, 102 },
            {60, 188, 28, 156, 52, 180, 20, 148, 62, 190, 30, 158, 54, 182, 22, 150 },
            {252, 124, 220, 92, 244, 116, 212, 84, 254, 126, 222, 94, 246, 118, 214, 86 },
            {3, 131, 35, 163, 11, 139, 43, 171, 1, 129, 33, 161, 9, 137, 41, 169 },
            {195, 67, 227, 99, 203, 75, 235, 107, 193, 65, 225, 97, 201, 73, 233, 105 },
            {51, 179, 19, 147, 59, 187, 27, 155, 49, 177, 17, 145, 57, 185, 25, 153 },
            {243, 115, 211, 83, 251, 123, 219, 91, 241, 113, 209, 81, 249, 121, 217, 89 },
            {15, 143, 47, 175, 7, 135, 39, 167, 13, 141, 45, 173, 5, 133, 37, 165 },
            {207, 79, 239, 111, 199, 71, 231, 103, 205, 77, 237, 109, 197, 69, 229, 101 },
            {63, 191, 31, 159, 55, 183, 23, 151, 61, 189, 29, 157, 53, 181, 21, 149 },
            {255, 127, 223, 95, 247, 119, 215, 87, 253, 125, 221, 93, 245, 117, 213, 85 }
    };

    // Matrix normalization
    static {
        for (int i = 0; i < 2; i++ ){
            for (int j = 0; j < 2; j++){
                MATRIX2[i][j] = (MATRIX2[i][j] / 4.0) - 0.5;
            }
        }

        for (int i = 0; i < 4; i++ ){
            for (int j = 0; j < 4; j++){
                MATRIX4[i][j] = (MATRIX4[i][j] / 16.0) - 0.5;
            }
        }

        for (int i = 0; i < 8; i++ ){
            for (int j = 0; j < 8; j++){
                MATRIX8[i][j] = (MATRIX8[i][j] / 64.0) - 0.5;
            }
        }

        for (int i = 0; i < 16; i++ ){
            for (int j = 0; j < 16; j++){
                MATRIX16[i][j] = (MATRIX16[i][j] / 256.0) - 0.5;
            }
        }
    }

    public OrderedDithering(ImageFactory imageFactory) {
        super(imageFactory);
    }

    private static double[][] chooseMatrix(int k){
        if (k == 2){
            return MATRIX2;
        }
        else if (k <= 16){
            return MATRIX4;
        }
        else if (k <= 32){
            return MATRIX8;
        }
        return MATRIX16;
    }

    @Override
    public ImageInterface apply(ImageInterface oldImage) {
        final FilterCreator creator = getCreator();
        final int redK = getRedK();
        final int greenK = getGreenK();
        final int blueK = getBlueK();

        switch (creator){
            case SIROTKIN -> {return SirotkinVariant.apply(oldImage, redK, blueK, greenK, getImageFactory());}
            case VOROBEV -> {return null;}
            case KONDRENKO -> {return KondrenkoVariant.apply(oldImage, redK, blueK, greenK, getImageFactory());}
            default -> throw new IllegalArgumentException("No such creator of ordered dithering");
        }
    }

    private static class SirotkinVariant {
        private static ImageInterface apply(ImageInterface oldImage, int redK, int blueK, int greenK, ImageFactory imageFactory){
            double colorRedDiv = 255.0 / (redK - 1);
            double colorGreenDiv = 255.0 / (greenK - 1);
            double colorBlueDiv = 255.0 / (blueK - 1);
            double[][] matrixRed = chooseMatrix(redK);
            double[][] matrixGreen = chooseMatrix(greenK);
            double[][] matrixBlue = chooseMatrix(blueK);
            int matrixRedSize = matrixRed.length;
            int matrixGreenSize = matrixGreen.length;
            int matrixBlueSize = matrixBlue.length;
            int width = oldImage.getWidth();
            int height = oldImage.getHeight();
            int gridSize = height * width;
            int[] grid = oldImage.getGrid();
            int[] newGrid = new int[gridSize];
            for (int y = 0; y < height; y++){
                for (int x = 0 ; x < width; x++){
                    int index = y * width + x;
                    int matrixRedIndexX = (x % matrixRedSize);
                    int matrixRedIndexY = (y % matrixRedSize);
                    int matrixGreenIndexX = (x % matrixGreenSize);
                    int matrixGreenIndexY = (y % matrixGreenSize);
                    int matrixBlueIndexX = (x % matrixBlueSize);
                    int matrixBlueIndexY = (y % matrixBlueSize);
                    int pixelColor = grid[index];
                    int red = (int) ((((pixelColor & 0x00FF0000) >> 16) ) + colorRedDiv * matrixRed[matrixRedIndexY][matrixRedIndexX]);
                    red = getClosedPalletColor(red, redK);
                    int green = (int) ((((pixelColor & 0x0000FF00) >> 8)) + colorGreenDiv * matrixGreen[matrixGreenIndexY][matrixGreenIndexX]);
                    green = getClosedPalletColor(green, greenK);
                    int blue = (int) (((pixelColor & 0x000000FF)) + colorBlueDiv * matrixBlue[matrixBlueIndexY][matrixBlueIndexX]);
                    blue = getClosedPalletColor(blue, blueK);
                    newGrid[index] = (pixelColor & 0xFF000000) |(red << 16) | (green << 8) | blue;
                }
            }

            return imageFactory.createImage(oldImage, newGrid);
        }

        private static int getClosedPalletColor(int color, int palletSize){
            if (color >= 255){
                return 255;
            }
            else if (color < 0){
                return 0;
            }
            double palletStep = 255.0 / (palletSize - 1);

            return (int) ((color * palletSize / 255) * palletStep);


        }
    }

    private static class KondrenkoVariant {
        private static ImageInterface apply(ImageInterface oldImage, int redK, int blueK, int greenK, ImageFactory imageFactory){




            double colorRedDiv = 255.0 / (redK - 1);
            double colorGreenDiv = 255.0 / (greenK - 1);
            double colorBlueDiv = 255.0 / (blueK - 1);
            double[][] matrixRed = chooseMatrix(redK);
            double[][] matrixGreen = chooseMatrix(greenK);
            double[][] matrixBlue = chooseMatrix(blueK);
            int matrixRedSize = matrixRed.length;
            int matrixGreenSize = matrixGreen.length;
            int matrixBlueSize = matrixBlue.length;
            int width = oldImage.getWidth();
            int height = oldImage.getHeight();
            int gridSize = height * width;
            int[] grid = oldImage.getGrid();
            int[] newGrid = new int[gridSize];
            for (int y = 0; y < height; y++){
                for (int x = 0 ; x < width; x++){
                    int index = y * width + x;
                    int matrixRedIndexX = (x % matrixRedSize);
                    int matrixRedIndexY = (y % matrixRedSize);
                    int matrixGreenIndexX = (x % matrixGreenSize);
                    int matrixGreenIndexY = (y % matrixGreenSize);
                    int matrixBlueIndexX = (x % matrixBlueSize);
                    int matrixBlueIndexY = (y % matrixBlueSize);
                    int pixelColor = grid[index];
                    int red = (int) ((((pixelColor & 0x00FF0000) >> 16) ) + colorRedDiv * matrixRed[matrixRedIndexY][matrixRedIndexX]);
                    red = getClosedPalletColor(red, redK);
                    int green = (int) ((((pixelColor & 0x0000FF00) >> 8)) + colorGreenDiv * matrixGreen[matrixGreenIndexY][matrixGreenIndexX]);
                    green = getClosedPalletColor(green, greenK);
                    int blue = (int) (((pixelColor & 0x000000FF)) + colorBlueDiv * matrixBlue[matrixBlueIndexY][matrixBlueIndexX]);
                    blue = getClosedPalletColor(blue, blueK);
                    newGrid[index] = (pixelColor & 0xFF000000) |(red << 16) | (green << 8) | blue;
                }
            }

            return imageFactory.createImage(oldImage, newGrid);
        }

        private static int getClosedPalletColor(int color, int palletSize){
            if (color >= 255){
                return 255;
            }
            else if (color < 0){
                return 0;
            }
            double palletStep = 255.0 / (palletSize - 1);

            return (int) ((color * palletSize / 255) * palletStep);


        }
    }
}
