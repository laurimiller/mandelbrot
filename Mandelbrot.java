import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


/**
 * Created by moppe_000 on 11-May-16.
 */
public class Mandelbrot {


    public static int[] Colors (int max){

        int[] colors = new int[max];
        for (int i = 0; i<max; i++) {
            colors[i] = Color.HSBtoRGB(i/256f, 1, i/(i+8f));
        }
        for(int i = 0; i < colors.length / 2; i++)
        {
            int temp = colors[i];
            colors[i] = colors[colors.length - i - 1];
            colors[colors.length - i - 1] = temp;
        }
        return colors;
    }

    public static BufferedImage set (double zoom_diff, double zoom, int height, int width, int max, int[] colors, BufferedImage image,
                               int black, double shiftX, double shiftY) throws IOException {
        for (int i = 0 ; i < zoom_diff; i++) {
            for (int row = 0; row < height; row++) {    //Im
                for (int col = 0; col < width; col++) { //Re
                    

                    double c_re = shiftX + ((col - width / 2.0) * 4.0 / width) / zoom;
                    double c_im = shiftY + ((row - height / 2.0) * 4.0 / width) / zoom;

                    double x = 0, y = 0;
                    int iterations = 0;
                    while (x * x + y * y < 4 && iterations < max) {
                        double x_new = x * x - y * y + c_re;
                        y = 2 * x * y + c_im;
                        x = x_new;
                        iterations++;
                    }
                    if (iterations < max) image.setRGB(col, row, colors[iterations]);
                    else image.setRGB(col, row, black);
                }
            }
            zoom = zoom + zoom_diff;
            zoom_diff = zoom_diff * 1.1;
            ImageIO.write(image, "png", new File("mandelbrot_seeria90" + Integer.toString(i) + ".png"));
        }
		return image;
    }
}