import java.awt.geom.Rectangle2D;

//
public class Mandelbrot extends FractalGenerator {
    public static final int MAX_ITERATIONS = 2000;

    // установка начального диапазона
    @Override
    public void getInitialRange(Rectangle2D.Double plane) {
        plane.x = -2;
        plane.y = -1.5;
        plane.width = 3;
        plane.height = 3;
    }

    // итеративная функция для фрактала Мандельброта
    @Override
    public int numIterations(double x, double y) {
        double re = 0, im = 0;
        for (int i = 0; i <= MAX_ITERATIONS; i++) {
            double nextRe = re*re - im*im + x;
            double nextIm = 2*re*im+y;

            re = nextRe;
            im = nextIm;

            if (nextRe*nextRe + nextIm*nextIm > 4) {
                return i;
            }
        }
        return -1;
    }
}
