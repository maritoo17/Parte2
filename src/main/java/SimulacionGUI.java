import javax.swing.*;
import java.awt.*;

public class SimulacionGUI {
    private JFrame frame;
    private PlatoCultivo plato;
    private int duracionDias;
    private Timer timer;
    private MonteCarloSimulacion simulacion;

    public SimulacionGUI(PlatoCultivo plato, int duracionDias, MonteCarloSimulacion simulacion) {
        this.plato = plato;
        this.duracionDias = duracionDias;
        this.simulacion = simulacion;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Simulación de Montecarlo");
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(new GridLayout(20, 20));

        timer = new Timer(1000, e -> actualizarGUI());
        timer.start();

        frame.setVisible(true);
    }

    private void actualizarGUI() {
        frame.getContentPane().removeAll();

        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                Celda celda = plato.getCelda(i, j);
                int bacterias = celda.getBacterias();
                Color color = getColorByBacteriaCount(bacterias);

                JPanel panel = new JPanel();
                panel.setBackground(color);
                frame.getContentPane().add(panel);
            }
        }

        frame.revalidate();
        frame.repaint();

        simulacion.simulateDay();
        duracionDias--;
        if (duracionDias <= 0) {
            timer.stop();
        }
    }

    private Color getColorByBacteriaCount(int bacterias) {
        if (bacterias >= 20) return Color.RED;
        else if (bacterias >= 15) return Color.MAGENTA;
        else if (bacterias >= 10) return Color.ORANGE;
        else if (bacterias >= 5) return Color.YELLOW;
        else if (bacterias >= 1) return Color.GREEN;
        else return Color.WHITE;
    }
}
