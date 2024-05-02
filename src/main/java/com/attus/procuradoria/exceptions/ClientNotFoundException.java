package com.attus.procuradoria.exceptions;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ClientNotFoundException extends EntityNotFoundException {
    public ClientNotFoundException(String message){
        super(message);
    }
    public ClientNotFoundException(String message, Throwable cause ){
        super(message, (Exception) cause);
    }
}
