package vistas;

import modelo.AlumnoDAO;
import modelo.AlumnoDTO;

import java.util.ArrayList;

public class AlumnoVista {
    public void mostrarTodosAlumnos() {
        AlumnoDAO dao = new AlumnoDAO();
        ArrayList<AlumnoDTO> lista = dao.obtenerTodosLosAlumnos();
// Ver datos
        for (AlumnoDTO alumno : lista) {
            System.out.println(alumno.getId() + "-" + alumno.getNombre() + "-" + alumno.getEdad() + "-" + alumno.getEmail() + "-" + alumno.getId());
        }
    }
}
