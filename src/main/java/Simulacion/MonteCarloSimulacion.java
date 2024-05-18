package Simulacion;

import Simulacion.Bacteria;
import Simulacion.Celda;
import Simulacion.PlatoCultivo;

import java.util.*;

public class MonteCarloSimulacion {
    private PlatoCultivo plato;
    private List<Bacteria> bacterias;
    private int duration;
    private Random random;

    public MonteCarloSimulacion(int numBacterias, int comidaTotal, int duration) {
        this.plato = new PlatoCultivo();
        this.bacterias = new ArrayList<>();
        this.duration = duration;
        this.random = new Random();

        int centroInicio = 8;
        int centroFin = 12;
        int bacteriasInicialesPorCelda = numBacterias / 16;

        for (int i = centroInicio; i < centroFin; i++) {
            for (int j = centroInicio; j < centroFin; j++) {
                plato.getCelda(i, j).setBacterias(bacteriasInicialesPorCelda);
                for (int k = 0; k < bacteriasInicialesPorCelda; k++) {
                    bacterias.add(new Bacteria(i, j));
                }
            }
        }

        int comidaPorCelda = comidaTotal / (plato.getCeldas().length * plato.getCeldas()[0].length);
        for (int i = 0; i < plato.getCeldas().length; i++) {
            for (int j = 0; j < plato.getCeldas()[0].length; j++) {
                plato.getCelda(i, j).setComida(comidaPorCelda);
            }
        }
    }

    public void simulateDay() {
        List<Bacteria> nuevasBacterias = new ArrayList<>();
        List<Bacteria> bacteriasActuales = new ArrayList<>(bacterias);

        for (Bacteria bacteria : bacteriasActuales) {
            int x = bacteria.getX();
            int y = bacteria.getY();
            Celda celdaActual = plato.getCelda(x, y);

            for (int i = 0; i < 10; i++) {
                if (celdaActual.getComida() >= 100) {
                    procesarBacteria(celdaActual, bacteria, 20, 3, 60);
                } else if (celdaActual.getComida() > 9) {
                    procesarBacteria(celdaActual, bacteria, 10, 6, 20);
                } else {
                    procesarBacteria(celdaActual, bacteria, 0, 20, 60);
                }
            }

            if (bacteria.getComidaConsumida() >= 150) {
                for (int i = 0; i < 3; i++) {
                    nuevasBacterias.add(new Bacteria(x, y));
                }
            } else if (bacteria.getComidaConsumida() >= 100) {
                for (int i = 0; i < 2; i++) {
                    nuevasBacterias.add(new Bacteria(x, y));
                }
            } else if (bacteria.getComidaConsumida() >= 50) {
                nuevasBacterias.add(new Bacteria(x, y));
            }
        }

        bacterias.addAll(nuevasBacterias);
    }

    private void procesarBacteria(Celda celdaActual, Bacteria bacteria, int comida, int probMuerte, int probMover) {
        int numeroAleatorio = random.nextInt(100);

        if (comida > 0) {
            celdaActual.setComida(celdaActual.getComida() - comida);
            bacteria.comer(comida);
        }

        if (numeroAleatorio < probMuerte) {
            bacterias.remove(bacteria);
        } else if (numeroAleatorio >= probMover) {
            moverBacteria(bacteria);
        }
    }

    private void moverBacteria(Bacteria bacteria) {
        int x = bacteria.getX();
        int y = bacteria.getY();
        int nuevoX = x;
        int nuevoY = y;

        int numeroAleatorio = random.nextInt(100);

        if (numeroAleatorio >= 60 && numeroAleatorio < 65 && x > 0) nuevoX = x - 1;
        else if (numeroAleatorio >= 65 && numeroAleatorio < 70 && x < 19) nuevoX = x + 1;
        else if (numeroAleatorio >= 70 && numeroAleatorio < 75 && y > 0) nuevoY = y - 1;
        else if (numeroAleatorio >= 75 && numeroAleatorio < 80 && y < 19) nuevoY = y + 1;
        else if (numeroAleatorio >= 80 && numeroAleatorio < 85 && x > 0 && y > 0) { nuevoX = x - 1; nuevoY = y - 1; }
        else if (numeroAleatorio >= 85 && numeroAleatorio < 90 && x < 19 && y > 0) { nuevoX = x + 1; nuevoY = y - 1; }
        else if (numeroAleatorio >= 90 && numeroAleatorio < 95 && x > 0 && y < 19) { nuevoX = x - 1; nuevoY = y + 1; }
        else if (numeroAleatorio >= 95 && numeroAleatorio < 100 && x < 19 && y < 19) { nuevoX = x + 1; nuevoY = y + 1; }

        plato.getCelda(bacteria.getX(), bacteria.getY()).setBacterias(plato.getCelda(bacteria.getX(), bacteria.getY()).getBacterias() - 1);
        plato.getCelda(nuevoX, nuevoY).setBacterias(plato.getCelda(nuevoX, nuevoY).getBacterias() + 1);
        bacteria.mover(nuevoX, nuevoY);
    }

    public PlatoCultivo getPlato() {
        return plato;
    }
}
