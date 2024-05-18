import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Comparator;

public class AppGUI {
    private JFrame frame;
    private DefaultListModel<String> listaModelo;
    private JList<String> listaPoblaciones;
    private Experimento experimento;
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public AppGUI() {
        prepareGUI();
        experimento = new Experimento();
    }

    private void prepareGUI() {
        frame = new JFrame("Gestor de Experimentos con Bacterias");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 1000);
        frame.getContentPane().setBackground(new Color(230, 240, 255));

        listaModelo = new DefaultListModel<>();
        listaPoblaciones = new JList<>(listaModelo);
        Font newFont = new Font("Arial", Font.PLAIN, 18);
        listaPoblaciones.setFont(newFont);
        listaPoblaciones.setBackground(Color.WHITE);
        listaPoblaciones.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5),
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1)
        ));
        frame.add(new JScrollPane(listaPoblaciones), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(230, 240, 255));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.add(buttonPanel, BorderLayout.SOUTH);

        JButton openButton = createStyledButton("Abrir", null);
        JButton newButton = createStyledButton("Nuevo Experimento", null);
        JButton addButton = createStyledButton("Añadir Población", null);
        JButton deleteButton = createStyledButton("Eliminar Población", new Color(255, 0, 0));
        JButton detailButton = createStyledButton("Detalles de Población", null);
        JButton saveButton = createStyledButton("Guardar", null);
        JButton saveAsButton = createStyledButton("Guardar Como", new Color(0, 71, 171));
        JButton simulateButton = createStyledButton("Simular", new Color(34, 139, 34));
        JButton monteCarloButton = createStyledButton("Simulación Montecarlo", new Color(255, 140, 0));

        // ComboBox for sorting options
        String[] sortingOptions = {"Ordenar por Fecha de Inicio", "Ordenar por Nombre", "Ordenar por Número de Bacterias"};
        JComboBox<String> sortingComboBox = new JComboBox<>(sortingOptions);
        sortingComboBox.addActionListener(this::sortList);

        buttonPanel.add(openButton);
        buttonPanel.add(newButton);
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(detailButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(saveAsButton);
        buttonPanel.add(simulateButton);
        buttonPanel.add(monteCarloButton);
        buttonPanel.add(sortingComboBox);

        simulateButton.addActionListener(e -> iniciarSimulacion());
        monteCarloButton.addActionListener(e -> ejecutarSimulacionMontecarlo());

        openButton.addActionListener(this::openExperiment);
        newButton.addActionListener(e -> {
            experimento = new Experimento();
            listaModelo.clear();
        });
        addButton.addActionListener(this::addPoblacion);
        deleteButton.addActionListener(this::deletePoblacion);
        detailButton.addActionListener(this::showDetails);
        saveButton.addActionListener(e -> saveExperiment(false));
        saveAsButton.addActionListener(e -> saveExperiment(true));

        frame.setVisible(true);
    }

    private JButton createStyledButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setBackground(backgroundColor);
        button.setForeground(Color.BLACK);
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorderPainted(true);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setFocusPainted(false);
        return button;
    }

    private void openExperiment(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
            try {
                experimento = FileManager.cargarExperimento(fileChooser.getSelectedFile().getAbsolutePath());
                updateList();
                JOptionPane.showMessageDialog(frame, "Experimento cargado exitosamente.");
            } catch (IOException | ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(frame, "Error al abrir el archivo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void addPoblacion(ActionEvent e) {
        JTextField nameField = new JTextField();
        JTextField startDateField = new JTextField(dtf.format(LocalDate.now()));
        JTextField daysField = new JTextField();
        JTextField initialCountField = new JTextField();
        JTextField tempField = new JTextField();
        String[] luminosityOptions = {"Alta", "Media", "Baja"};
        JComboBox<String> luminosityBox = new JComboBox<>(luminosityOptions);
        JTextField initialFoodField = new JTextField();
        JTextField incrementDayField = new JTextField();
        JTextField incrementFoodField = new JTextField();
        JTextField finalFoodField = new JTextField();
        JTextField durationField = new JTextField("30");
        String[] foodPatterns = {"Lineal", "Constante", "Incremental", "Alternante"};
        JComboBox<String> foodPatternBox = new JComboBox<>(foodPatterns);

        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Nombre:"));
        panel.add(nameField);
        panel.add(new JLabel("Fecha Inicio:"));
        panel.add(startDateField);
        panel.add(new JLabel("Duración (días):"));
        panel.add(daysField);
        panel.add(new JLabel("Bacterias Iniciales:"));
        panel.add(initialCountField);
        panel.add(new JLabel("Temperatura:"));
        panel.add(tempField);
        panel.add(new JLabel("Luminosidad:"));
        panel.add(luminosityBox);
        panel.add(new JLabel("Comida Inicial (µg):"));
        panel.add(initialFoodField);
        panel.add(new JLabel("Día Incremento:"));
        panel.add(incrementDayField);
        panel.add(new JLabel("Comida Incremento (µg):"));
        panel.add(incrementFoodField);
        panel.add(new JLabel("Comida Final (µg):"));
        panel.add(finalFoodField);
        panel.add(new JLabel("Patrón de Comida:"));
        panel.add(foodPatternBox);

        int result = JOptionPane.showConfirmDialog(frame, panel, "Añadir Población", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String nombre = nameField.getText();
                LocalDate startDate = LocalDate.parse(startDateField.getText(), dtf);
                int durationDays = Integer.parseInt(daysField.getText());
                int initialCount = Integer.parseInt(initialCountField.getText());
                double temperature = Double.parseDouble(tempField.getText());
                String luminosity = (String) luminosityBox.getSelectedItem();
                int initialFood = Integer.parseInt(initialFoodField.getText());
                int incrementDay = Integer.parseInt(incrementDayField.getText());
                int incrementFood = Integer.parseInt(incrementFoodField.getText());
                int finalFood = Integer.parseInt(finalFoodField.getText());

                if (initialFood > 300000 || incrementFood > 300000 || finalFood > 300000) {
                    JOptionPane.showMessageDialog(frame, "La cantidad de comida no debe superar los 300,000 µg en ningún campo.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int foodPattern = foodPatternBox.getSelectedIndex() + 1;

                LocalDate endDate = startDate.plusDays(durationDays);

                Poblacion poblacion = new Poblacion(nombre, startDate, endDate, initialCount, temperature, luminosity,
                        initialFood, incrementDay, incrementFood, finalFood, durationDays, foodPattern);
                experimento.addPoblacion(poblacion);
                updateList();
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(frame, "Error en el formato de fecha: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Por favor, introduzca números válidos en los campos requeridos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deletePoblacion(ActionEvent e) {
        String selected = listaPoblaciones.getSelectedValue();
        if (selected != null) {
            experimento.removePoblacion(selected);
            updateList();
        } else {
            JOptionPane.showMessageDialog(frame, "Seleccione una población para eliminar.", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void showDetails(ActionEvent e) {
        String selected = listaPoblaciones.getSelectedValue();
        if (selected != null) {
            Poblacion poblacion = experimento.getPoblaciones().stream()
                    .filter(p -> p.getNombre().equals(selected))
                    .findFirst()
                    .orElse(null);

            if (poblacion != null) {
                StringBuilder comidaDiaria = new StringBuilder();
                for (int i = 0; i < poblacion.getComidaPorDia().length; i++) {
                    comidaDiaria.append(String.format("Día %d: %d µg\n", i + 1, poblacion.getComidaPorDia()[i]));
                }

                String details = String.format(
                        "Nombre: %s\nFecha Inicio: %s\nFecha Fin: %s\nBacterias Iniciales: %d\n" +
                                "Temperatura: %.1f\nLuminosidad: %s\nComida Inicial: %d µg\nDía Incremento: %d\n" +
                                "Comida Incremento: %d µg\nComida Final: %d µg\nComida Diaria:\n%s",
                        poblacion.getNombre(),
                        dtf.format(poblacion.getFechaInicio()),
                        dtf.format(poblacion.getFechaFin()),
                        poblacion.getBacteriasIniciales(),
                        poblacion.getTemperatura(),
                        poblacion.getLuminosidad(),
                        poblacion.getComidaInicial(),
                        poblacion.getDiaIncremento(),
                        poblacion.getComidaIncremento(),
                        poblacion.getComidaFinal(),
                        comidaDiaria.toString()
                );
                JOptionPane.showMessageDialog(frame, details, "Detalles de Población", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Población no encontrada.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Seleccione una población para ver detalles.", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void saveExperiment(boolean saveAs) {
        if (saveAs || experimento.getArchivoRuta() == null) {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
                experimento.setArchivoRuta(fileChooser.getSelectedFile().getAbsolutePath());
            } else {
                return;
            }
        }

        try {
            FileManager.guardarExperimento(experimento, experimento.getArchivoRuta());
            JOptionPane.showMessageDialog(frame, "Experimento guardado exitosamente.", "Guardado", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame, "Error al guardar el archivo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateList() {
        listaModelo.clear();
        for (Poblacion p : experimento.getPoblaciones()) {
            listaModelo.addElement(p.getNombre());
        }
    }

    private void sortList(ActionEvent e) {
        JComboBox<String> comboBox = (JComboBox<String>) e.getSource();
        String selected = (String) comboBox.getSelectedItem();
        List<Poblacion> poblaciones = experimento.getPoblaciones();

        switch (selected) {
            case "Ordenar por Fecha de Inicio":
                poblaciones.sort(Comparator.comparing(Poblacion::getFechaInicio));
                break;
            case "Ordenar por Nombre":
                poblaciones.sort(Comparator.comparing(Poblacion::getNombre));
                break;
            case "Ordenar por Número de Bacterias":
                poblaciones.sort(Comparator.comparingInt(Poblacion::getBacteriasIniciales));
                break;
        }

        updateList();
    }

    private void iniciarSimulacion() {
        if (experimento.getPoblaciones().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No hay poblaciones para simular.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        for (Poblacion poblacion : experimento.getPoblaciones()) {
            MonteCarloSimulacion simulacion = new MonteCarloSimulacion(poblacion.getBacteriasIniciales(), poblacion.getComidaInicial(), poblacion.getDuracionDias());
            for (int i = 0; i < poblacion.getDuracionDias(); i++) {
                simulacion.simulateDay();
            }
            new SimulacionGUI(simulacion.getPlato(), poblacion.getDuracionDias(), simulacion);
        }
    }

    private void ejecutarSimulacionMontecarlo() {
        if (experimento.getPoblaciones().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No hay poblaciones para simular.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        for (Poblacion poblacion : experimento.getPoblaciones()) {
            MonteCarloSimulacion monteCarlo = new MonteCarloSimulacion(poblacion.getBacteriasIniciales(), poblacion.getComidaInicial(), poblacion.getDuracionDias());
            new SimulacionGUI(monteCarlo.getPlato(), poblacion.getDuracionDias(), monteCarlo);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AppGUI::new);
    }
}
