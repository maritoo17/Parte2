import java.io.*;

public class FileManager {
    public static void guardarExperimento(Experimento experimento, String rutaArchivo) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(rutaArchivo))) {
            oos.writeObject(experimento);
        }
    }

    public static Experimento cargarExperimento(String rutaArchivo) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(rutaArchivo))) {
            return (Experimento) ois.readObject();
        }
    }
}
