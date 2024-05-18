package Simulacion;

import javax.swing.*;
import java.awt.*;

public class SimulacionGUI {
    private JFrame frame;
    private PlatoCultivo plato;
    private int duracionDias;
    private Timer timer;
    private Simulacion simulacion;
    private JLabel[][] cells;

    public SimulacionGUI(PlatoCultivo plato, int duracionDias, Simulacion simulacion) {
        this.plato = plato;
        this.duracionDias = duracionDias;
        this.simulacion = simulacion;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Simulación de Bacterias");
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(new GridLayout(plato.getTamaño(), plato.getTamaño()));

        cells = new JLabel[plato.getTamaño()][plato.getTamaño()];
        for (int i = 0; i < plato.getTamaño(); i++) {
            for (int j = 0; j < plato.getTamaño(); j++) {
                cells[i][j] = new JLabel();
                cells[i][j].setOpaque(true);
                cells[i][j].setBackground(getColorByBacteriaCount(plato.getCelda(i, j).getBacterias()));
                cells[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                frame.getContentPane().add(cells[i][j]);
            }
        }

        timer = new Timer(1000, e -> actualizarGUI());
        timer.start();

        frame.setVisible(true);
    }

    private void actualizarGUI() {
        simulacion.ejecutarSimulacionDiaria();

        for (int i = 0; i < plato.getTamaño(); i++) {
            for (int j = 0; j < plato.getTamaño(); j++) {
                cells[i][j].setBackground(getColorByBacteriaCount(plato.getCelda(i, j).getBacterias()));
            }
        }

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