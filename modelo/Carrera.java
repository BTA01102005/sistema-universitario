package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Carrera {
    private final String nombre;
    private final int duracion;
    private final Coordinador coordinador;
    private final double precioInscripcion;
    private final double precioCuota;
    private final List<Materia> materias = new ArrayList<>();
    private final List<Alumno> alumnos = new ArrayList<>();

    public Carrera(String nombre, int duracion, Coordinador coordinador,
                   double precioInscripcion, double precioCuota) {
        this.nombre = nombre;
        this.duracion = duracion;
        this.coordinador = coordinador;
        this.precioInscripcion = precioInscripcion;
        this.precioCuota = precioCuota;
    }

    public void agregarMateria(Materia materia) {
        if (!materias.contains(materia)) {
            materias.add(materia);
            materia.asignarCarrera(this);
        }
    }

    public void agregarAlumno(Alumno alumno) {
        if (!alumnos.contains(alumno)) {
            alumnos.add(alumno);
        }
    }

    public List<Materia> getMaterias() {
        return Collections.unmodifiableList(materias);
    }

    public List<Alumno> getAlumnos() {
        return Collections.unmodifiableList(alumnos);
    }

    public String getNombre() {
        return nombre;
    }

    public int getDuracion() {
        return duracion;
    }

    public Coordinador getCoordinador() {
        return coordinador;
    }

    public double getPrecioInscripcion() {
        return precioInscripcion;
    }

    public double getPrecioCuota() {
        return precioCuota;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
