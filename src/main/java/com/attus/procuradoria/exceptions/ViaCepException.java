package com.attus.procuradoria.exceptions;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ViaCepException extends EntityNotFoundException {
    public ViaCepException(String message){
        super(message);
    }
}
