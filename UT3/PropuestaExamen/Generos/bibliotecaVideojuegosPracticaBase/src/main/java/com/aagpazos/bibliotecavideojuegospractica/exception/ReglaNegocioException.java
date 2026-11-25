package com.aagpazos.bibliotecavideojuegospractica.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/** Se incumple una regla de negocio (RN-1...). Se traduce a HTTP 422. */
@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
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
