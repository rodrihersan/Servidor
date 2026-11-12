
package com.example.bibliotecavideojuegos.dao.interfaz;

import com.example.bibliotecavideojuegos.dto.DesplegableDTO;
import com.example.bibliotecavideojuegos.dto.SocioDTO;

import java.time.LocalDate;
import java.util.ArrayList;

public interface ISocioDAO {

    ArrayList<SocioDTO> filtrarSocios(String nombre, String email, Boolean activo);

    ArrayList<DesplegableDTO> listaSociosActivos();

    boolean esSocioActivo(int idSocio);

    LocalDate obtenerFechaNacimiento(int idSocio);

    boolean existeEmail(String email);

    int insertarSocio(SocioDTO socio);
}
