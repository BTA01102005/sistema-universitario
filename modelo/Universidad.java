package modelo;
 
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
 
public class Universidad {
    private final List<Carrera> carreras = new ArrayList<>();
    private final List<Alumno> alumnos = new ArrayList<>();
    private int proximoLegajo = 1001;
 
    public static Universidad crearUniversidadInicial() {
        Universidad universidad = new Universidad();
 
        Coordinador coord1 = new Coordinador("Laura", "Gómez", "20111222");
        Coordinador coord2 = new Coordinador("Marcelo", "Pérez", "20333444");
 
        Profesor prof1 = new Profesor("Ana", "Rodríguez", "25111222");
        Profesor prof2 = new Profesor("Carlos", "Fernández", "26333444");
        Profesor prof3 = new Profesor("Lucía", "Martínez", "27444555");
 
        Carrera sistemas = new Carrera(
                "Ingeniería en Sistemas de Información", 5, coord1, 50000, 85000);
 
        Carrera administracion = new Carrera(
                "Licenciatura en Administración", 4, coord2, 45000, 75000);
 
        // Sistemas: 2 materias por curso, en los 5 cursos de duración de la carrera.
        sistemas.agregarMateria(new Materia("Arquitectura de Computadoras", 1, 1, prof2));
        sistemas.agregarMateria(new Materia("Análisis Matemático I", 1, 2, prof1));
        sistemas.agregarMateria(new Materia("Programación II", 2, 1, prof1));
        sistemas.agregarMateria(new Materia("Matemática II", 2, 2, prof2));
        sistemas.agregarMateria(new Materia("Ingeniería de Software", 3, 1, prof3));
        sistemas.agregarMateria(new Materia("Bases de Datos", 3, 2, prof2));
        sistemas.agregarMateria(new Materia("Redes", 4, 1, prof3));
        sistemas.agregarMateria(new Materia("Gestión de Proyectos", 4, 2, prof2));
        sistemas.agregarMateria(new Materia("Paradigmas y Lenguajes II", 5, 1, prof1));
        sistemas.agregarMateria(new Materia("Sistemas Operativos", 5, 2, prof3));
 
        // Administración: 2 materias por curso, en los 4 cursos de duración de la carrera.
        administracion.agregarMateria(new Materia("Contabilidad I", 1, 1, prof2));
        administracion.agregarMateria(new Materia("Economía I", 1, 2, prof3));
        administracion.agregarMateria(new Materia("Administración General", 2, 1, prof3));
        administracion.agregarMateria(new Materia("Matemática Financiera", 2, 2, prof1));
        administracion.agregarMateria(new Materia("Costos", 3, 1, prof2));
        administracion.agregarMateria(new Materia("Marketing", 3, 2, prof3));
        administracion.agregarMateria(new Materia("Finanzas", 4, 1, prof1));
        administracion.agregarMateria(new Materia("Gestión Estratégica", 4, 2, prof2));
 
        universidad.carreras.add(sistemas);
        universidad.carreras.add(administracion);
 
        // Algunos alumnos iniciales para que las consultas puedan probarse.
        Alumno a1 = universidad.registrarAlumno("Juan", "Pérez", "40111222");
        Alumno a2 = universidad.registrarAlumno("Sofía", "Gómez", "42333444");
        universidad.matricularAlumno(a1, sistemas);
        universidad.matricularAlumno(a2, administracion);
        universidad.inscribirAlumnoMateria(a1, sistemas.getMaterias().get(0));
        universidad.inscribirAlumnoMateria(a2, administracion.getMaterias().get(0));
 
        return universidad;
    }
 
    public Alumno registrarAlumno(String nombre, String apellido, String dni) {
        Alumno alumno = new Alumno(nombre, apellido, dni, proximoLegajo++);
        alumnos.add(alumno);
        return alumno;
    }
 
    public boolean matricularAlumno(Alumno alumno, Carrera carrera) {
        return alumno.matricularEn(carrera);
    }
 
    public boolean inscribirAlumnoMateria(Alumno alumno, Materia materia) {
        return alumno.inscribirEn(materia);
    }
 
    public Alumno buscarAlumnoPorLegajo(int legajo) {
        for (Alumno alumno : alumnos) {
            if (alumno.getLegajo() == legajo) {
                return alumno;
            }
        }
        return null;
    }
 
    public Carrera buscarCarreraPorNombre(String nombre) {
        for (Carrera carrera : carreras) {
            if (carrera.getNombre().equals(nombre)) {
                return carrera;
            }
        }
        return null;
    }
 
    public List<Carrera> getCarreras() {
        return Collections.unmodifiableList(carreras);
    }
 
    public List<Alumno> getAlumnos() {
        return Collections.unmodifiableList(alumnos);
    }
}