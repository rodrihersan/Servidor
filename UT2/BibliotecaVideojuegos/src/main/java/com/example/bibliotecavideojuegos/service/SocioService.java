
package com.example.bibliotecavideojuegos.service;

import com.example.bibliotecavideojuegos.dao.interfaz.ISocioDAO;
import com.example.bibliotecavideojuegos.dto.SocioDTO;
import com.example.bibliotecavideojuegos.exception.ReglaNegocioException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;

@Service
public class SocioService {

    private final ISocioDAO socioDAO;

    public SocioService(ISocioDAO socioDAO) {
        this.socioDAO = socioDAO;
    }

    public ArrayList<SocioDTO> filtrarSocios(String nombre, String email, Boolean activo) {
        return socioDAO.filtrarSocios(nombre, email, activo);
    }

    public void insertarSocio(String nombre, String apellidos, String email, LocalDate fechaNacimiento) {

        if (nombre == null || nombre.isBlank()) {
            throw new ReglaNegocioException("El nombre es obligatorio.");
        }

        if (apellidos == null || apellidos.isBlank()) {
            throw new ReglaNegocioException("Los apellidos son obligatorios.");
        }

        if (email == null || email.isBlank()) {
            throw new ReglaNegocioException("El email es obligatorio.");
        }

        if (fechaNacimiento == null) {
            throw new ReglaNegocioException("La fecha de nacimiento es obligatoria.");
        }

        if (fechaNacimiento.isAfter(LocalDate.now())) {
            throw new ReglaNegocioException("La fecha de nacimiento no puede ser futura.");
        }

        if (socioDAO.existeEmail(email)) {
            throw new ReglaNegocioException("Ya existe un socio registrado con ese email.");
        }

        SocioDTO socio = new SocioDTO(
                null,
                nombre,
                apellidos,
                email,
                fechaNacimiento,
                true,
                0,
                LocalDate.now()
        );

        int filasAfectadas = socioDAO.insertarSocio(socio);

        if (filasAfectadas != 1) {
            throw new ReglaNegocioException("No se ha podido registrar el socio.");
        }
    }
}
