import javax.swing.*;
import java.awt.*;

public class SimulacionGUI {
    private JFrame frame;
    private PlatoCultivo plato;

    public SimulacionGUI(PlatoCultivo plato) {
        this.plato = plato;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Simulación de Montecarlo");
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new GridLayout(20, 20));

        actualizarGUI();

        frame.setVisible(true);
    }

    private void actualizarGUI() {
        frame.getContentPane().removeAll();

        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                Celda celda = plato.getCelda(i, j);
                int bacterias = celda.getBacterias();
                Color color = Color.WHITE;

                if (bacterias >= 20) color = Color.RED;
                else if (bacterias >= 15) color = Color.MAGENTA;
                else if (bacterias >= 10) color = Color.ORANGE;
                else if (bacterias >= 5) color = Color.YELLOW;
                else if (bacterias >= 1) color = Color.GREEN;

                JPanel panel = new JPanel();
                panel.setBackground(color);
                frame.getContentPane().add(panel);
            }
        }

        frame.revalidate();
        frame.repaint();
    }

    public void actualizarSimulacion(PlatoCultivo plato) {
        this.plato = plato;
        actualizarGUI();
    }
}
