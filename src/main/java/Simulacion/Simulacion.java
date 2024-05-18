package Simulacion;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Simulacion {
    private PlatoCultivo plato;
    private List<Bacteria> bacterias;
    private Random random;
    private int diaActual;

    public Simulacion(int numBacterias, int comidaTotal) {
        plato = new PlatoCultivo();
        bacterias = new ArrayList<>();
        random = new Random();
        diaActual = 0;

        // Inicializar las bacterias en el centro del plato
        int centroInicio = 8;
        int centroFin = 12;
        int bacteriasInicialesPorCelda = numBacterias / 16;

        for (int i = centroInicio; i < centroFin; i++) {
            for (int j = centroInicio; j < centroFin; j++) {
                plato.getCelda(i, j).setBacterias(bacteriasInicialesPorCelda);
                for (int k = 0; k < bacteriasInicialesPorCelda; k++) {
                    bacterias.add(new Bacteria(i, j, Color.GREEN));
                }
            }
        }

        // Inicializar la comida en todas las celdas del plato
        int comidaPorCelda = comidaTotal / (plato.getTamaño() * plato.getTamaño());
        for (int i = 0; i < plato.getTamaño(); i++) {
            for (int j = 0; j < plato.getTamaño(); j++) {
                plato.getCelda(i, j).setComida(comidaPorCelda);
            }
        }
    }

    public void ejecutarSimulacionDiaria() {
        List<Bacteria> nuevasBacterias = new ArrayList<>();
        List<Bacteria> bacteriasActuales = new ArrayList<>(bacterias);

        for (Bacteria bacteria : bacteriasActuales) {
            int x = bacteria.getX();
            int y = bacteria.getY();
            Celda celdaActual = plato.getCelda(x, y);

            for (int i = 0; i < 10; i++) {
                int comidaEnCelda = celdaActual.getComida();
                if (comidaEnCelda >= 100) {
                    procesarBacteria(celdaActual, bacteria, 20, 3, 60, 100);
                } else if (comidaEnCelda > 9) {
                    procesarBacteria(celdaActual, bacteria, 10, 6, 20, 100);
                } else {
                    procesarBacteria(celdaActual, bacteria, 0, 20, 60, 100);
                }
            }

            int comidaConsumida = bacteria.getComidaConsumida();
            if (comidaConsumida >= 150) {
                for (int i = 0; i < 3; i++) {
                    nuevasBacterias.add(new Bacteria(x, y, Color.GREEN));
                }
            } else if (comidaConsumida >= 100) {
                for (int i = 0; i < 2; i++) {
                    nuevasBacterias.add(new Bacteria(x, y, Color.GREEN));
                }
            } else if (comidaConsumida >= 50) {
                nuevasBacterias.add(new Bacteria(x, y, Color.GREEN));
            }
        }

        bacterias.addAll(nuevasBacterias);
        diaActual++;
    }

    private void procesarBacteria(Celda celdaActual, Bacteria bacteria, int comida, int probMuerte, int probMoverInicio, int probMoverFin) {
        int numeroAleatorio = random.nextInt(100);

        if (comida > 0) {
            celdaActual.setComida(celdaActual.getComida() - comida);
            bacteria.comer(comida);
        }

        if (numeroAleatorio < probMuerte) {
            bacterias.remove(bacteria);
        } else if (numeroAleatorio >= probMoverInicio && numeroAleatorio < probMoverFin) {
            moverBacteriaHaciaComida(bacteria);
        }
    }

    private void moverBacteriaHaciaComida(Bacteria bacteria) {
        int x = bacteria.getX();
        int y = bacteria.getY();
        int nuevoX = x;
        int nuevoY = y;
        int maxComida = plato.getCelda(x, y).getComida();

        int[][] direcciones = {
                { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 },
                { -1, -1 }, { 1, -1 }, { -1, 1 }, { 1, 1 }
        };

        for (int[] direccion : direcciones) {
            int dx = x + direccion[0];
            int dy = y + direccion[1];

            if (dx >= 0 && dx < plato.getTamaño() && dy >= 0 && dy < plato.getTamaño()) {
                int comida = plato.getCelda(dx, dy).getComida();
                if (comida > maxComida) {
                    maxComida = comida;
                    nuevoX = dx;
                    nuevoY = dy;
                }
            }
        }

        plato.getCelda(bacteria.getX(), bacteria.getY()).setBacterias(plato.getCelda(bacteria.getX(), bacteria.getY()).getBacterias() - 1);
        plato.getCelda(nuevoX, nuevoY).setBacterias(plato.getCelda(nuevoX, nuevoY).getBacterias() + 1);
        bacteria.mover(nuevoX, nuevoY);
    }

    public PlatoCultivo getPlato() {
        return plato;
    }

    public int getDiaActual() {
        return diaActual;
    }

    public List<Bacteria> getBacterias() {
        return bacterias;
    }
}
