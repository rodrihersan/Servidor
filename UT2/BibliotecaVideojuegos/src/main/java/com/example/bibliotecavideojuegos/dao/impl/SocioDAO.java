
package com.example.bibliotecavideojuegos.dao.impl;

import com.example.bibliotecavideojuegos.dao.interfaz.ISocioDAO;
import com.example.bibliotecavideojuegos.dto.DesplegableDTO;
import com.example.bibliotecavideojuegos.dto.SocioDTO;
import com.example.bibliotecavideojuegos.repository.SocioRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;

@Repository
public class SocioDAO implements ISocioDAO {

    private final SocioRepository socioRepository;

    public SocioDAO(SocioRepository socioRepository) {
        this.socioRepository = socioRepository;
    }

    @Override
    public ArrayList<SocioDTO> filtrarSocios(String nombre, String email, Boolean activo) {
        return socioRepository.filtrarSocios(nombre, email, activo);
    }

    @Override
    public ArrayList<DesplegableDTO> listaSociosActivos() {
        return socioRepository.listaSociosActivos();
    }

    @Override
    public boolean esSocioActivo(int idSocio) {
        return socioRepository.esSocioActivo(idSocio);
    }

    @Override
    public LocalDate obtenerFechaNacimiento(int idSocio) {
        return socioRepository.obtenerFechaNacimiento(idSocio);
    }

    @Override
    public boolean existeEmail(String email) {
        return socioRepository.existeEmail(email);
    }

    @Override
    public int insertarSocio(SocioDTO socio) {
        return socioRepository.insertarSocio(socio);
    }
}
