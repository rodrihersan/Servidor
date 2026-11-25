
package com.aagpazos.bibliotecavideojuegospractica.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/** Conflicto con el estado de los datos: duplicados, borrados imposibles... HTTP 409. */
@ResponseStatus(HttpStatus.CONFLICT)
public class ConflictoException extends RuntimeException {

    public ConflictoException(String mensaje) {
        super(mensaje);
    }
}

