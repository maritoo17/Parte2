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
        this.comidaInicial = comidaInicial * 1000;
        this.diaIncremento = diaIncremento;
        this.comidaIncremento = comidaIncremento * 1000;
        this.comidaFinal = comidaFinal * 1000;
        this.comidaPorDia = new int[duracionDias];
        calcularComidaPorDia(patronComida);
    }

    private void calcularComidaPorDia(int patronComida) {
        switch (patronComida) {
            case 1:
                Arrays.fill(comidaPorDia, comidaInicial);
                break;
            case 2:
                double incrementoDiario = (double) (comidaFinal - comidaInicial) / (comidaPorDia.length - 1);
                for (int i = 0; i < comidaPorDia.length; i++) {
                    comidaPorDia[i] = comidaInicial + (int) Math.round(i * incrementoDiario);
                    if (comidaPorDia[i] > 300000) {
                        throw new IllegalArgumentException("La cantidad de comida diaria no puede ser mayor a 300000");
                    }
                }
                break;
            case 3:
                comidaPorDia[0] = comidaInicial;
                for (int i = 1; i < comidaPorDia.length; i++) {
                    if (i % 2 == 1) {
                        comidaPorDia[i] = comidaPorDia[i - 1];
                    } else {
                        double incrementoCadaDosDias = (double) (comidaFinal - comidaInicial) / Math.ceil((comidaPorDia.length - 1) / 2.0);
                        comidaPorDia[i] = (int) Math.round(comidaPorDia[i - 1] + incrementoCadaDosDias);
                        if (comidaPorDia[i] > 300000) {
                            throw new IllegalArgumentException("La cantidad de comida diaria no puede ser mayor a 300000");
                        }
                    }
                }
                break;
            default:
                calcularComidaLineal();
        }
    }

    private void calcularComidaLineal() {
        comidaPorDia[0] = comidaInicial;
        if (diaIncremento > 0) {
            double incrementoDiario = (double) (comidaIncremento - comidaInicial) / diaIncremento;
            for (int i = 1; i <= diaIncremento; i++) {
                comidaPorDia[i] = comidaPorDia[i - 1] + (int) Math.round(incrementoDiario);
            }
        }
        int díasRestantes = comidaPorDia.length - diaIncremento - 1;
        if (díasRestantes > 0) {
            double decrementoDiario = (double) (comidaFinal - comidaPorDia[diaIncremento]) / díasRestantes;
            for (int i = diaIncremento + 1; i < comidaPorDia.length; i++) {
                comidaPorDia[i] = comidaPorDia[i - 1] + (int) Math.round(decrementoDiario);
            }
        }
        comidaPorDia[comidaPorDia.length - 1] = comidaFinal;
    }

    // Getters and Setters
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
}
