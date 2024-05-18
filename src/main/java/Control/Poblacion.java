package Control;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;

public class Poblacion implements Serializable {
    private String nombre;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private int bacteriasIniciales;
    private double temperatura;
    private String luminosidad;
    private int comidaInicial;
    private int diaIncremento;
    private int comidaIncremento;
    private int duracionDias;
    private int comidaFinal;
    private int[] comidaPorDia;

    public Poblacion(String nombre, LocalDate fechaInicio, LocalDate fechaFin, int bacteriasIniciales,
                     double temperatura, String luminosidad, int comidaInicial, int diaIncremento,
                     int comidaIncremento, int comidaFinal, int duracionDias, int patronComida) {
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.bacteriasIniciales = bacteriasIniciales;
        this.temperatura = temperatura;
        this.luminosidad = luminosidad;
        this.comidaInicial = comidaInicial;
        this.diaIncremento = diaIncremento;
        this.comidaIncremento = comidaIncremento;
        this.comidaFinal = comidaFinal;
        this.comidaPorDia = new int[duracionDias];
        calcularComidaPorDia(patronComida);
    }

    private void calcularComidaPorDia(int patronComida) {
        switch (patronComida) {
            case 1: // Constante
                Arrays.fill(comidaPorDia, comidaInicial);
                break;
            case 2: // Lineal
                double incrementoDiario = (double) (comidaFinal - comidaInicial) / (comidaPorDia.length - 1);
                for (int i = 0; i < comidaPorDia.length; i++) {
                    comidaPorDia[i] = comidaInicial + (int) Math.round(i * incrementoDiario);
                    if (comidaPorDia[i] > 300000) {
                        throw new IllegalArgumentException("La cantidad de comida diaria no puede ser mayor a 300000");
                    }
                }
                break;
            case 3: // Incremental
                comidaPorDia[0] = comidaInicial;
                for (int i = 1; i < comidaPorDia.length; i++) {
                    if (i % diaIncremento == 0) {
                        comidaPorDia[i] = comidaPorDia[i - 1] + comidaIncremento;
                    } else {
                        comidaPorDia[i] = comidaPorDia[i - 1];
                    }
                    if (comidaPorDia[i] > 300000) {
                        throw new IllegalArgumentException("La cantidad de comida diaria no puede ser mayor a 300000");
                    }
                }
                break;
            case 4: // Alternante
                comidaPorDia[0] = comidaInicial;
                for (int i = 1; i < comidaPorDia.length; i++) {
                    if (i % 2 == 0) {
                        comidaPorDia[i] = comidaPorDia[i - 1] + comidaIncremento;
                    } else {
                        comidaPorDia[i] = comidaPorDia[i - 1];
                    }
                    if (comidaPorDia[i] > 300000) {
                        throw new IllegalArgumentException("La cantidad de comida diaria no puede ser mayor a 300000");
                    }
                }
                break;
            default:
                throw new IllegalArgumentException("Patrón de comida no reconocido");
        }
    }

    public String getNombre() {
        return nombre;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public int getBacteriasIniciales() {
        return bacteriasIniciales;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public String getLuminosidad() {
        return luminosidad;
    }

    public int getComidaInicial() {
        return comidaInicial;
    }

    public int getDiaIncremento() {
        return diaIncremento;
    }

    public int getComidaIncremento() {
        return comidaIncremento;
    }

    public int getComidaFinal() {
        return comidaFinal;
    }

    public int[] getComidaPorDia() {
        return comidaPorDia;
    }

    public int getDuracionDias() {
        return duracionDias;
    }

    public void setDuracionDias(int duracionDias) {
        this.duracionDias = duracionDias;
    }
}
