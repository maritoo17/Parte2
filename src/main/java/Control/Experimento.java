package Control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Experimento implements Serializable {
    private List<Poblacion> poblaciones;
    private String archivoRuta;

    public Experimento() {
        poblaciones = new ArrayList<>();
    }

    public void addPoblacion(Poblacion poblacion) {
        poblaciones.add(poblacion);
    }

    public void removePoblacion(String nombre) {
        poblaciones.removeIf(p -> p.getNombre().equals(nombre));
    }

    public List<Poblacion> getPoblaciones() {
        return poblaciones;
    }

    public String getArchivoRuta() {
        return archivoRuta;
    }

    public void setArchivoRuta(String archivoRuta) {
        this.archivoRuta = archivoRuta;
    }

    public void ordenarPorFechaInicio() {
        Collections.sort(poblaciones, Comparator.comparing(Poblacion::getFechaInicio));
    }

    public void ordenarPorNombre() {
        Collections.sort(poblaciones, Comparator.comparing(Poblacion::getNombre));
    }

    public void ordenarPorNumeroBacterias() {
        Collections.sort(poblaciones, Comparator.comparingInt(Poblacion::getBacteriasIniciales));
    }
}