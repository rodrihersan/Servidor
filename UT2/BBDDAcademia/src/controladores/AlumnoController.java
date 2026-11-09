package controladores;

import modelo.AlumnoDAO;
import modelo.AlumnoDTO;

import java.util.ArrayList;

public class AlumnoController {
    public ArrayList<AlumnoDTO> obtenerTodosLosAlumnos(){
        AlumnoDAO dao = new AlumnoDAO();
        ArrayList<AlumnoDTO> lista = dao.obtenerTodosLosAlumnos();
        return lista;
    }
}
