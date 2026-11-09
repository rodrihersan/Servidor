package modelo;

public class AlumnoDTO {
    private int id;
    private String nombre;
    private String email;
    private int edad;

    public AlumnoDTO(int id, String nombre, String email, int edad) {
        this.nombre = nombre;
        this.email = email;
        this.edad = edad;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
