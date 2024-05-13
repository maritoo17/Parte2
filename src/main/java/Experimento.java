import java.io.Serializable;
import java.util.ArrayList;
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
}
