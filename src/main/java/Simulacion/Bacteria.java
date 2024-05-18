package Simulacion;

import java.awt.Color;

public class Bacteria {
    private int x;
    private int y;
    private int comidaConsumida;
    private Color color;

    public Bacteria(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.comidaConsumida = 0;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getComidaConsumida() {
        return comidaConsumida;
    }

    public void comer(int comida) {
        this.comidaConsumida += comida;
    }

    public void mover(int nuevoX, int nuevoY) {
        this.x = nuevoX;
        this.y = nuevoY;
    }

    public Color getColor() {
        return color;
    }
}