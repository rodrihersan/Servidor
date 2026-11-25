package com.aagpazos.bibliotecavideojuegospractica.service;

import com.aagpazos.bibliotecavideojuegospractica.dao.interfaces.ISocioDAO;
import com.aagpazos.bibliotecavideojuegospractica.dto.SocioDTO;
import com.aagpazos.bibliotecavideojuegospractica.exception.ConflictoException;
import com.aagpazos.bibliotecavideojuegospractica.exception.DatosInvalidosException;
import com.aagpazos.bibliotecavideojuegospractica.exception.RecursoNoEncontradoException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;

@Service
public class SocioService {
    private final ISocioDAO socioDAO;

    public SocioService(ISocioDAO socioDAO) {
        this.socioDAO = socioDAO;
    }

    public ArrayList<SocioDTO> listar(SocioDTO filtro) {
        return socioDAO.buscar(filtro);
    }

    public SocioDTO obtener(int id) {
        SocioDTO socio = socioDAO.buscarPorId(id);
        if (socio == null) {
            throw new RecursoNoEncontradoException("No existe el socio con id " + id);
        }
        return socio;
    }

    public SocioDTO crear(SocioDTO req) {
        validar(req);
        String email = req.getEmail().trim();
        if (socioDAO.existeEmail(email)) {
            throw new ConflictoException("Ya existe un socio con el email " + email);
        }
        // Un socio nuevo siempre se da de alta como activo y con la fecha de hoy
        SocioDTO nuevo = new SocioDTO(0, req.getNombre().trim(), req.getApellidos().trim(), email,
                req.getFechaNacimiento(), LocalDate.now(), true, 0, 0);
        int id = socioDAO.insertar(nuevo);
        return obtener(id);
    }

    public SocioDTO actualizar(int id, SocioDTO req) {
        SocioDTO actual = obtener(id); // 404 si no existe
        validar(req);
        String email = req.getEmail().trim();
        if (!actual.getEmail().equalsIgnoreCase(req.getEmail()) && socioDAO.existeEmail(email)) {
            throw new ConflictoException("Ya existe otro socio con el email " + email);
        }
        boolean activo = actual.isActivo();
        if(req.isActivo() != null)
            activo = req.isActivo();
        socioDAO.actualizar(new SocioDTO(id, req.getNombre().trim(), req.getApellidos().trim(), email,
                req.getFechaNacimiento(), actual.getFechaAlta(), activo, 0, 0));
        return obtener(id);
    }

    public void borrar(int id) {
        obtener(id); // 404 si no existe
        int prestamos = socioDAO.contarPrestamos(id);
        if (prestamos > 0) {
            throw new ConflictoException("No se puede borrar el socio: tiene " + prestamos
                    + " préstamo(s) registrados. Dalo de baja en su lugar (activo = false)");
        }
        socioDAO.borrar(id);
    }

    private void validar(SocioDTO req) {
        if (req == null) {
            throw new DatosInvalidosException("Falta el cuerpo de la petición");
        }
        if (req.getNombre() == null || req.getNombre().isBlank() || req.getNombre().length() > 40) {
            throw new DatosInvalidosException("El nombre es obligatorio y no puede superar los 40 caracteres");
        }
        if (req.getApellidos() == null || req.getApellidos().isBlank() || req.getApellidos().length() > 80) {
            throw new DatosInvalidosException("Los apellidos son obligatorios y no pueden superar los 80 caracteres");
        }
        if (req.getEmail() == null || req.getEmail().length() > 100 || !req.getEmail().matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            throw new DatosInvalidosException("El email no tiene un formato válido");
        }
        if (req.getFechaNacimiento() == null || !req.getFechaNacimiento().isBefore(LocalDate.now())) {
            throw new DatosInvalidosException("La fecha de nacimiento es obligatoria y debe ser anterior a hoy");
        }
    }
}
