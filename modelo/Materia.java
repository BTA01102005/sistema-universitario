package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Materia {
    private final String nombre;
    private final int curso;
    private final int cuatrimestre;
    private final Profesor profesor;
    private Carrera carrera;
    private final List<Inscripcion> inscripciones = new ArrayList<>();

    public Materia(String nombre, int curso, int cuatrimestre, Profesor profesor) {
        this.nombre = nombre;
        this.curso = curso;
        this.cuatrimestre = cuatrimestre;
        this.profesor = profesor;
    }

    public void asignarCarrera(Carrera carrera) {
        this.carrera = carrera;
    }

    public void agregarInscripcion(Inscripcion inscripcion) {
        if (!inscripciones.contains(inscripcion)) {
            inscripciones.add(inscripcion);
        }
    }

    public List<Inscripcion> getInscripciones() {
        return Collections.unmodifiableList(inscripciones);
    }

    public String getNombre() {
        return nombre;
    }

    public int getCurso() {
        return curso;
    }

    public int getCuatrimestre() {
        return cuatrimestre;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public Carrera getCarrera() {
        return carrera;
    }

    @Override
    public String toString() {
        return nombre + " (Curso " + curso + ", " + cuatrimestre + "° cuatrimestre)";
    }
}
