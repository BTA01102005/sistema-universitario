package ui;

import modelo.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class MainFrame extends JFrame {
    private final Universidad universidad;
    private final JTextArea salida = new JTextArea();

    public MainFrame(Universidad universidad) {
        this.universidad = universidad;

        setTitle("Sistema de Gestión Universitaria");
        setSize(950, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        construirInterfaz();
        mostrarInicio();
    }

    private void construirInterfaz() {
        JPanel principal = new JPanel(new BorderLayout(15, 15));
        principal.setBorder(new EmptyBorder(15, 15, 15, 15));
        setContentPane(principal);

        JPanel menu = new JPanel();
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setPreferredSize(new Dimension(250, 0));

        JLabel titulo = new JLabel("<html><b>SISTEMA UNIVERSITARIO</b></html>");
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setBorder(new EmptyBorder(5, 5, 20, 5));
        menu.add(titulo);

        agregarBoton(menu, "Matricular alumno", e -> matricularAlumno());
        agregarBoton(menu, "Inscribir a materia", e -> inscribirMateria());
        agregarBoton(menu, "Registrar inasistencia", e -> registrarInasistencia());
        agregarBoton(menu, "Cargar situación final", e -> cargarSituacionFinal());
        agregarBoton(menu, "Alumnos de una carrera", e -> mostrarAlumnosCarrera());
        agregarBoton(menu, "Alumnos de una materia", e -> mostrarAlumnosMateria());
        agregarBoton(menu, "Materias de una carrera", e -> mostrarMateriasCarrera());
        agregarBoton(menu, "Lista de alumnos", e -> mostrarTodosLosAlumnos());

        menu.add(Box.createVerticalGlue());

        JButton salir = new JButton("Salir");
        salir.setAlignmentX(Component.CENTER_ALIGNMENT);
        salir.addActionListener(e -> System.exit(0));
        menu.add(salir);

        salida.setEditable(false);
        salida.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        salida.setLineWrap(true);
        salida.setWrapStyleWord(true);
        salida.setBorder(new EmptyBorder(12, 12, 12, 12));

        principal.add(menu, BorderLayout.WEST);
        principal.add(new JScrollPane(salida), BorderLayout.CENTER);
    }

    private void agregarBoton(JPanel panel, String texto, java.awt.event.ActionListener accion) {
        JButton boton = new JButton(texto);
        boton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.setFocusPainted(false);
        boton.addActionListener(accion);
        panel.add(boton);
        panel.add(Box.createVerticalStrut(8));
    }

    private void mostrarInicio() {
        salida.setText("SISTEMA DE GESTI\u00d3N UNIVERSITARIA\n\n"
            + "Seleccione una operaci\u00f3n del men\u00fa.\n\n"
            + "El sistema fue inicializado con:\n"
            + "\u2022 2 carreras.\n"
            + "\u2022 Coordinador asignado a cada carrera.\n"
            + "\u2022 Materias de distintos cursos y cuatrimestres.\n"
            + "\u2022 Profesores compartidos entre materias y carreras.\n"
            + "\u2022 2 alumnos de ejemplo.\n\n"
            + "Las operaciones respetan la regla de que un alumno\n"
            + "solamente puede inscribirse a materias de una carrera\n"
            + "en la que se encuentra matriculado.");
    }

    private Alumno seleccionarAlumno() {
        if (universidad.getAlumnos().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay alumnos registrados.");
            return null;
        }

        Alumno elegido = (Alumno) JOptionPane.showInputDialog(
                this, "Seleccione un alumno:", "Alumno",
                JOptionPane.QUESTION_MESSAGE, null,
                universidad.getAlumnos().toArray(), universidad.getAlumnos().get(0));

        return elegido;
    }

    private Carrera seleccionarCarrera() {
        if (universidad.getCarreras().isEmpty()) return null;

        return (Carrera) JOptionPane.showInputDialog(
                this, "Seleccione una carrera:", "Carrera",
                JOptionPane.QUESTION_MESSAGE, null,
                universidad.getCarreras().toArray(), universidad.getCarreras().get(0));
    }

    private Materia seleccionarMateria(Carrera carrera) {
        if (carrera == null || carrera.getMaterias().isEmpty()) {
            JOptionPane.showMessageDialog(this, "La carrera no tiene materias.");
            return null;
        }

        return (Materia) JOptionPane.showInputDialog(
                this, "Seleccione una materia:", "Materia",
                JOptionPane.QUESTION_MESSAGE, null,
                carrera.getMaterias().toArray(), carrera.getMaterias().get(0));
    }

    private void matricularAlumno() {
        JTextField nombre = new JTextField();
        JTextField apellido = new JTextField();
        JTextField dni = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 2, 8, 8));
        panel.add(new JLabel("Nombre:"));
        panel.add(nombre);
        panel.add(new JLabel("Apellido:"));
        panel.add(apellido);
        panel.add(new JLabel("DNI:"));
        panel.add(dni);

        int opcion = JOptionPane.showConfirmDialog(
                this, panel, "Registrar nuevo alumno", JOptionPane.OK_CANCEL_OPTION);

        if (opcion != JOptionPane.OK_OPTION) return;

        if (nombre.getText().trim().isEmpty() || apellido.getText().trim().isEmpty() || dni.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos.");
            return;
        }

        Alumno alumno = universidad.registrarAlumno(
                nombre.getText().trim(), apellido.getText().trim(), dni.getText().trim());

        Carrera carrera = seleccionarCarrera();
        if (carrera != null) {
            universidad.matricularAlumno(alumno, carrera);
            salida.setText("Alumno registrado y matriculado correctamente.\n\n" + alumno
                    + "\nCarrera: " + carrera.getNombre());
        }
    }

    private void inscribirMateria() {
        Alumno alumno = seleccionarAlumno();
        if (alumno == null) return;

        if (alumno.getCarreras().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "El alumno no está matriculado en ninguna carrera.");
            return;
        }

        Carrera carrera = (Carrera) JOptionPane.showInputDialog(
                this, "Seleccione una carrera en la que está matriculado:",
                "Carrera", JOptionPane.QUESTION_MESSAGE, null,
                alumno.getCarreras().toArray(), alumno.getCarreras().get(0));

        if (carrera == null) return;

        Materia materia = seleccionarMateria(carrera);
        if (materia == null) return;

        if (universidad.inscribirAlumnoMateria(alumno, materia)) {
            salida.setText("INSCRIPCIÓN REALIZADA\n\nAlumno: " + alumno.getNombreCompleto()
                    + "\nLegajo: " + alumno.getLegajo()
                    + "\nCarrera: " + carrera.getNombre()
                    + "\nMateria: " + materia.getNombre());
        } else {
            JOptionPane.showMessageDialog(this,
                    "No fue posible realizar la inscripción. El alumno ya está inscripto.");
        }
    }

    private Inscripcion seleccionarInscripcion() {
        Alumno alumno = seleccionarAlumno();
        if (alumno == null || alumno.getInscripciones().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El alumno no tiene inscripciones.");
            return null;
        }

        return (Inscripcion) JOptionPane.showInputDialog(
                this, "Seleccione una materia:",
                "Inscripción", JOptionPane.QUESTION_MESSAGE, null,
                alumno.getInscripciones().toArray(), alumno.getInscripciones().get(0));
    }

    private void registrarInasistencia() {
        Inscripcion inscripcion = seleccionarInscripcion();
        if (inscripcion == null) return;

        inscripcion.registrarInasistencia();

        salida.setText("INASISTENCIA REGISTRADA\n\nAlumno: "
                + inscripcion.getAlumno().getNombreCompleto()
                + "\nMateria: " + inscripcion.getMateria().getNombre()
                + "\nTotal de inasistencias: " + inscripcion.getInasistencias());
    }

    private void cargarSituacionFinal() {
        Inscripcion inscripcion = seleccionarInscripcion();
        if (inscripcion == null) return;

        SituacionFinal situacion = (SituacionFinal) JOptionPane.showInputDialog(
                this, "Seleccione la situación final:",
                "Situación final", JOptionPane.QUESTION_MESSAGE, null,
                SituacionFinal.values(), SituacionFinal.REGULAR);

        if (situacion == null) return;

        inscripcion.cargarSituacionFinal(situacion);

        salida.setText("SITUACIÓN FINAL CARGADA\n\nAlumno: "
                + inscripcion.getAlumno().getNombreCompleto()
                + "\nMateria: " + inscripcion.getMateria().getNombre()
                + "\nSituación: " + situacion
                + "\nInasistencias: " + inscripcion.getInasistencias());
    }

    private void mostrarAlumnosCarrera() {
        Carrera carrera = seleccionarCarrera();
        if (carrera == null) return;

        StringBuilder texto = new StringBuilder();
        texto.append("ALUMNOS MATRICULADOS\n\n");
        texto.append("Carrera: ").append(carrera.getNombre()).append("\n");
        texto.append("Coordinador: ").append(carrera.getCoordinador()).append("\n\n");

        if (carrera.getAlumnos().isEmpty()) {
            texto.append("No hay alumnos matriculados.");
        } else {
            for (Alumno alumno : carrera.getAlumnos()) {
                texto.append("• ").append(alumno)
                        .append(" | DNI: ").append(alumno.getDni()).append("\n");
            }
        }

        salida.setText(texto.toString());
    }

    private void mostrarAlumnosMateria() {
        Carrera carrera = seleccionarCarrera();
        if (carrera == null) return;

        Materia materia = seleccionarMateria(carrera);
        if (materia == null) return;

        String[] opciones = {"Cursando (inscriptos)", "Finalizados"};
        String opcion = (String) JOptionPane.showInputDialog(
                this, "¿Qué desea visualizar?", "Estado de alumnos",
                JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

        if (opcion == null) return;

        StringBuilder texto = new StringBuilder();
        texto.append("ALUMNOS DE MATERIA\n\n");
        texto.append("Carrera: ").append(carrera.getNombre()).append("\n");
        texto.append("Materia: ").append(materia.getNombre()).append("\n");
        texto.append("Profesor: ").append(materia.getProfesor()).append("\n\n");

        boolean finalizados = opcion.startsWith("Finalizados");
        boolean encontrados = false;

        for (Inscripcion inscripcion : materia.getInscripciones()) {
            if (inscripcion.haFinalizado() == finalizados) {
                encontrados = true;
                texto.append("• ").append(inscripcion.getAlumno().getNombreCompleto())
                        .append(" | Legajo: ").append(inscripcion.getAlumno().getLegajo());

                if (finalizados) {
                    texto.append(" | Situación: ").append(inscripcion.getSituacionFinal())
                            .append(" | Inasistencias: ").append(inscripcion.getInasistencias());
                }
                texto.append("\n");
            }
        }

        if (!encontrados) {
            texto.append("No hay alumnos que cumplan el criterio seleccionado.");
        }

        salida.setText(texto.toString());
    }

    private void mostrarMateriasCarrera() {
        Carrera carrera = seleccionarCarrera();
        if (carrera == null) return;

        StringBuilder texto = new StringBuilder();
        texto.append("MATERIAS DE LA CARRERA\n\n");
        texto.append(carrera.getNombre()).append("\n\n");

        for (Materia materia : carrera.getMaterias()) {
            texto.append("• ").append(materia.getNombre())
                    .append("\n  Curso: ").append(materia.getCurso())
                    .append("\n  Cuatrimestre: ").append(materia.getCuatrimestre())
                    .append("\n  Profesor: ").append(materia.getProfesor())
                    .append("\n\n");
        }

        salida.setText(texto.toString());
    }

    private void mostrarTodosLosAlumnos() {
        StringBuilder texto = new StringBuilder("ALUMNOS REGISTRADOS\n\n");

        for (Alumno alumno : universidad.getAlumnos()) {
            texto.append("• ").append(alumno)
                    .append(" | DNI: ").append(alumno.getDni())
                    .append(" | Carreras: ");

            List<Carrera> carreras = alumno.getCarreras();
            if (carreras.isEmpty()) {
                texto.append("ninguna");
            } else {
                for (int i = 0; i < carreras.size(); i++) {
                    if (i > 0) texto.append(", ");
                    texto.append(carreras.get(i).getNombre());
                }
            }
            texto.append("\n");
        }

        salida.setText(texto.toString());
    }
}
