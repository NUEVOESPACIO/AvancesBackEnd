package com.mycompany.mavenproject4.exception;

public class EmailAlreadyExistsException extends RuntimeException {
    
    public EmailAlreadyExistsException() {
        super("Email ya existente");
    }


}


