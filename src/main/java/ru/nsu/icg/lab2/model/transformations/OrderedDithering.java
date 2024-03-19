package ru.nsu.icg.lab2.model.transformations;

import lombok.Getter;
import lombok.Setter;
import ru.nsu.icg.lab2.model.ImageFactory;
import ru.nsu.icg.lab2.model.ImageInterface;
import ru.nsu.icg.lab2.model.Transformation;

@Getter
@Setter
public class OrderedDithering extends Transformation {

    private int redK = 8;

    private int greenK = 8;

    private int blueK = 4;


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

    //нормализация матриц
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
                MATRIX8[i][j] = (MATRIX8[i][j] / 128.0) - 0.5;
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


    @Override
    public ImageInterface apply(ImageInterface oldImage) {

        return applySirotkin(oldImage);
    }








    //Sirotkin version

    private static double colorRedDiv;


    private static double colorGreenDiv;


    private static double colorBlueDiv;


    private int roundColorRed(double red){
        if (red > 1.0){
            return 255;
        }
        return (int) ((255 / (redK - 1)) * Math.round(red / colorRedDiv));
    }


    private int roundColorGreen(double green){
        if (green > 1.0){
            return 255;
        }
        return (int) ((255 / (greenK - 1)) * Math.round(green / colorGreenDiv));
    }


    private int roundColorBlue(double blue){
        if (blue > 1.0){
            return 255;
        }
        return (int) ((255 / (blueK - 1)) * Math.round(blue / colorBlueDiv));
    }


    private double[][] chooseMatrix(int k){
        if (k == 2){
            return MATRIX2;
        }
        else if (k <= 16){
            return MATRIX4;
        }
        else if (k <= 64){
            return MATRIX8;
        }
        return MATRIX16;
    }


    private ImageInterface applySirotkin(ImageInterface oldImage){
        colorRedDiv = 1.0 / (redK - 1);
        colorGreenDiv = 1.0 / (greenK - 1);
        colorBlueDiv = 1.0 / (blueK - 1);
        double[][] matrixRed = chooseMatrix(redK);
        double[][] matrixGreen = chooseMatrix(greenK);
        double[][] matrixBlue = chooseMatrix(blueK);
        int matrixRedSize = matrixRed.length;
        int matrixGreenSize = matrixGreen.length;
        int matrixBlueSize = matrixBlue.length;
        int width = oldImage.getWidth();
        int height = oldImage.getHeight();
        int gridSize = height * width;
        int[] grid = new int[gridSize];
        oldImage.getARGB(grid);
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
                int red = roundColorRed((((pixelColor & 0x00FF0000) >> 16) / 255.0) + colorRedDiv * matrixRed[matrixRedIndexY][matrixRedIndexX]);
                int green = roundColorGreen((((pixelColor & 0x0000FF00) >> 8) / 255.0) + colorGreenDiv * matrixGreen[matrixGreenIndexY][matrixGreenIndexX]);
                int blue = roundColorBlue(((pixelColor & 0x000000FF) / 255.0) + colorBlueDiv * matrixBlue[matrixBlueIndexY][matrixBlueIndexX]);
                newGrid[index] = (pixelColor & 0xFF000000) | (red << 16) | (green << 8) | blue;
            }
        }



        return getImageFactory().createImage(oldImage, newGrid);
    }

}
