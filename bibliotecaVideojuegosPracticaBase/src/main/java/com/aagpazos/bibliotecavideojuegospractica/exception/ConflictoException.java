package com.aagpazos.bibliotecavideojuegospractica.exception;

/** Conflicto con el estado de los datos: duplicados, borrados imposibles... HTTP 409. */
public class ConflictoException extends RuntimeException {
    public ConflictoException(String mensaje) {
        super(mensaje);
    }
}
