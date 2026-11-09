package modelo;

public class CursoDTO {
    private int id;
    private String nombre;
    private int duracion_horas;
    private double precio;

    public CursoDTO(int id, int duracion_horas, String nombre, double precio) {
        this.id = id;
        this.duracion_horas = duracion_horas;
        this.nombre = nombre;
        this.precio = precio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDuracion_horas() {
        return duracion_horas;
    }

    public void setDuracion_horas(int duracion_horas) {
        this.duracion_horas = duracion_horas;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }
}
