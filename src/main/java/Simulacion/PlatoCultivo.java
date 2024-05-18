package Simulacion;

public class PlatoCultivo {
    private final int tamaño = 20;
    private Celda[][] celdas;

    public PlatoCultivo() {
        celdas = new Celda[tamaño][tamaño];
        for (int i = 0; i < tamaño; i++) {
            for (int j = 0; j < tamaño; j++) {
                celdas[i][j] = new Celda();
            }
        }
    }

    public PlatoCultivo(int bacteriasIniciales, int comidaInicial) {
        celdas = new Celda[tamaño][tamaño];
        int bacteriasPorCelda = bacteriasIniciales / (tamaño * tamaño);
        for (int i = 0; i < tamaño; i++) {
            for (int j = 0; j < tamaño; j++) {
                celdas[i][j] = new Celda(bacteriasPorCelda, comidaInicial);
            }
        }
    }

    public Celda[][] getCeldas() {
        return celdas;
    }

    public Celda getCelda(int x, int y) {
        return celdas[x][y];
    }

    public int getTamaño() {
        return tamaño;
    }
}
