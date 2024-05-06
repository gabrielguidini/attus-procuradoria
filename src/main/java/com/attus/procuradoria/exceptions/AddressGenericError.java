package com.attus.procuradoria.exceptions;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AddressGenericError extends EntityNotFoundException {
    public AddressGenericError(String message) {
        super(message);
    }
}
