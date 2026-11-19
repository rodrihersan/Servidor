package com.aagpazos.bibliotecavideojuegospractica.exception;

/** Un id (de la ruta o del cuerpo) no existe. HTTP 404. */
public class RecursoNoEncontradoException extends RuntimeException {
    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
