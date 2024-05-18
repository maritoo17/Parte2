package Simulacion;

public class Celda {
    private int bacterias;
    private int comida;

    public Celda() {
        this.bacterias = 0;
        this.comida = 0;
    }

    public Celda(int bacterias, int comida) {
        this.bacterias = bacterias;
        this.comida = comida;
    }

    public int getBacterias() {
        return bacterias;
    }

    public void setBacterias(int bacterias) {
        this.bacterias = bacterias;
    }

    public int getComida() {
        return comida;
    }

    public void setComida(int comida) {
        this.comida = comida;
    }
}
