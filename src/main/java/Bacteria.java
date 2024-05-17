public class Bacteria {
    private int x;
    private int y;
    private int comidaConsumida;

    public Bacteria(int x, int y) {
        this.x = x;
        this.y = y;
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

    public void comer(int cantidad) {
        comidaConsumida += cantidad;
    }

    public void mover(int nuevoX, int nuevoY) {
        this.x = nuevoX;
        this.y = nuevoY;
    }
}