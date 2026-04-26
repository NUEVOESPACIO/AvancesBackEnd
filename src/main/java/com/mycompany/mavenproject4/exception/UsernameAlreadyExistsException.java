package com.mycompany.mavenproject4.exception;

public class UsernameAlreadyExistsException extends RuntimeException {
    
    public UsernameAlreadyExistsException() {
        super("Email ya existente");
    }
    
}



