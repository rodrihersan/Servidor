package com.aagpazos.bibliotecavideojuegospractica.exception;

/** Se incumple una regla de negocio (RN-1...). Se traduce a HTTP 422. */
public class ReglaNegocioException extends RuntimeException {
    private final String regla;

    public ReglaNegocioException(String regla, String mensaje) {
        super(mensaje);
        this.regla = regla;
    }

    public String getRegla() {
        return regla;
    }

    public String obtenerMensajeUsuario() {
        return "[" + regla + "] " + getMessage();
    }
}
