package com.aagpazos.bibliotecavideojuegospractica.exception;

/** Datos mal formados o fuera de rango. HTTP 400. */
public class DatosInvalidosException extends RuntimeException {
    public DatosInvalidosException(String mensaje) {
        super(mensaje);
    }
}
