import java.io.Serializable;
import java.util.Date;

public class Poblacion implements Serializable {
    private String nombre;
    private Date fechaInicio;
    private Date fechaFin;
    private int bacteriasIniciales;
    private double temperatura;
    private String luminosidad;
    private int comidaInicial;
    private int diaIncremento;
    private int comidaIncremento;
    private int comidaFinal;
    private int[] comidaPorDia = new int[30];

    public Poblacion(String nombre, Date fechaInicio, Date fechaFin, int bacteriasIniciales,
                     double temperatura, String luminosidad, int comidaInicial, int diaIncremento,
                     int comidaIncremento, int comidaFinal) {
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
        this.comidaPorDia[0] = comidaInicial;
    }

    public String getNombre() {
        return nombre;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public Date getFechaFin() {
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

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public void setBacteriasIniciales(int bacteriasIniciales) {
        this.bacteriasIniciales = bacteriasIniciales;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public void setLuminosidad(String luminosidad) {
        this.luminosidad = luminosidad;
    }

    public void setComidaInicial(int comidaInicial) {
        this.comidaInicial = comidaInicial;
    }

    public void setDiaIncremento(int diaIncremento) {
        this.diaIncremento = diaIncremento;
    }

    public void setComidaIncremento(int comidaIncremento) {
        this.comidaIncremento = comidaIncremento;
    }

    public void setComidaFinal(int comidaFinal) {
        this.comidaFinal = comidaFinal;
    }

    public void setComidaPorDia(int[] comidaPorDia) {
        this.comidaPorDia = comidaPorDia;
    }

    public void calcularComidaPorDia() {
        comidaPorDia[0] = comidaInicial;

        if (diaIncremento > 0) {
            double incrementoDiario = (double) (comidaIncremento - comidaInicial) / diaIncremento;
            for (int i = 1; i <= diaIncremento; i++) {
                comidaPorDia[i] = comidaPorDia[i - 1] + (int) Math.round(incrementoDiario);
            }
        }

        int díasRestantes = 29 - diaIncremento;
        if (díasRestantes > 0) {
            double incrementoDiarioFinal = (double) (comidaFinal - comidaPorDia[diaIncremento]) / díasRestantes;
            for (int i = diaIncremento + 1; i < 30; i++) {
                comidaPorDia[i] = comidaPorDia[i - 1] + (int) Math.round(incrementoDiarioFinal);
            }
        }

        comidaPorDia[29] = comidaFinal;
    }
}
