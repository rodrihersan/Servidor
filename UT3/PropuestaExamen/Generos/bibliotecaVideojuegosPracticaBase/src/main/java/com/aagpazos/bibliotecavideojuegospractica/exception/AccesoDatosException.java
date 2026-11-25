package com.aagpazos.bibliotecavideojuegospractica.exception;

/** Fallo inesperado de la base de datos. HTTP 500. */
public class AccesoDatosException extends RuntimeException {
    public AccesoDatosException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
