package modelo;

public class Inscripcion {
    private final Alumno alumno;
    private final Materia materia;
    private int inasistencias;
    private SituacionFinal situacionFinal;

    public Inscripcion(Alumno alumno, Materia materia) {
        this.alumno = alumno;
        this.materia = materia;
        this.inasistencias = 0;
        this.situacionFinal = null;
    }

    public void registrarInasistencia() {
        inasistencias++;
    }

    public void registrarAsistencia() {
        // La asistencia se registra como una clase realizada sin incrementar inasistencias.
    }

    public void cargarSituacionFinal(SituacionFinal situacionFinal) {
        this.situacionFinal = situacionFinal;
    }

    public boolean haFinalizado() {
        return situacionFinal != null;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public Materia getMateria() {
        return materia;
    }

    public int getInasistencias() {
        return inasistencias;
    }

    public SituacionFinal getSituacionFinal() {
        return situacionFinal;
    }
}
