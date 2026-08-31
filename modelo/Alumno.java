package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Alumno {
    private final String nombre;
    private final String apellido;
    private final String dni;
    private final int legajo;
    private final List<Carrera> carreras = new ArrayList<>();
    private final List<Inscripcion> inscripciones = new ArrayList<>();

    public Alumno(String nombre, String apellido, String dni, int legajo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.legajo = legajo;
    }

    public boolean matricularEn(Carrera carrera) {
        if (carreras.contains(carrera)) {
            return false;
        }
        carreras.add(carrera);
        carrera.agregarAlumno(this);
        return true;
    }

    public boolean estaMatriculadoEn(Carrera carrera) {
        return carreras.contains(carrera);
    }

    public boolean estaInscriptoEn(Materia materia) {
        return buscarInscripcion(materia) != null;
    }

    public boolean inscribirEn(Materia materia) {
        if (!estaMatriculadoEn(materia.getCarrera()) || estaInscriptoEn(materia)) {
            return false;
        }
        Inscripcion inscripcion = new Inscripcion(this, materia);
        inscripciones.add(inscripcion);
        materia.agregarInscripcion(inscripcion);
        return true;
    }

    public Inscripcion buscarInscripcion(Materia materia) {
        for (Inscripcion inscripcion : inscripciones) {
            if (inscripcion.getMateria() == materia) {
                return inscripcion;
            }
        }
        return null;
    }

    public List<Carrera> getCarreras() {
        return Collections.unmodifiableList(carreras);
    }

    public List<Inscripcion> getInscripciones() {
        return Collections.unmodifiableList(inscripciones);
    }

    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }

    public String getDni() {
        return dni;
    }

    public int getLegajo() {
        return legajo;
    }

    @Override
    public String toString() {
        return legajo + " - " + getNombreCompleto();
    }
}
