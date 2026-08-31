package modelo;

public class Coordinador {
    private final String nombre;
    private final String apellido;
    private final String dni;

    public Coordinador(String nombre, String apellido, String dni) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
    }

    @Override
    public String toString() {
        return nombre + " " + apellido;
    }

    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }

    public String getDni() {
        return dni;
    }
}
