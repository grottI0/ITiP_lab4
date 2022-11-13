import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;

public class FractalExplorer {
    private int display;
    private JImageDisplay image;
    private FractalGenerator fractalGenerator;
    private Rectangle2D.Double planeRange;

    public static void main(String[] args) {
        FractalExplorer fractalExplorer = new FractalExplorer(800);
        fractalExplorer.createAndShowGUI();
        fractalExplorer.drawFractal();
    }

    // конструктор
    public FractalExplorer(int display) {
        this.display = display;
        this.planeRange = new Rectangle2D.Double(0, 0, 0, 0);
        this.fractalGenerator = new Mandelbrot();
        fractalGenerator.getInitialRange(planeRange);
    }

    // создает графический интерфейс
    public void createAndShowGUI() {
        image = new JImageDisplay(display, display);

        JButton button = new JButton("Reset");
        JFrame frame = new JFrame("Fractal generator");
        frame.setLayout(new BorderLayout());

        image.addMouseListener(new MouseListener());
        button.addActionListener(new ActionListener());

        frame.add(image, BorderLayout.CENTER);
        frame.add(button, BorderLayout.SOUTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }

    // отрисовывает фрактал
    private void drawFractal() {
        for (int x = 0; x < display; x++) {
            double xCoord = FractalGenerator.getCoord (planeRange.x, planeRange.x + planeRange.width, display, x);
            for (int y = 0; y < display; y++) {
                double yCoord = FractalGenerator.getCoord (planeRange.y, planeRange.y + planeRange.height, display, y);
                int numIterations = fractalGenerator.numIterations(xCoord, yCoord);

                if (numIterations == -1) {
                    image.drawPixel(x, y, 0);
                } else {
                    float hue = 0.7f + (float) numIterations / 200f;
                    int rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
                    image.drawPixel(x, y, rgbColor);

                image.repaint();
                }
            }
        }
    }

    // отслеживает нажатия кнопки
    private class ActionListener implements java.awt.event.ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            image.clearImage();
            fractalGenerator.getInitialRange(planeRange);
            drawFractal();
        }
    }

    // отслеживает клики мыши
    private class MouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            double x = FractalGenerator.getCoord(planeRange.x,planeRange.x + planeRange.width, display, e.getX());
            double y = FractalGenerator.getCoord(planeRange.y,planeRange.y + planeRange.width, display, e.getY());
            fractalGenerator.recenterAndZoomRange(planeRange, x, y, 0.5);
            drawFractal();
        }
    }
}
