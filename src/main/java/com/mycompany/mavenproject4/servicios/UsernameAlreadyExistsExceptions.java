package com.mycompany.mavenproject4.servicios;

public class UsernameAlreadyExistsExceptions extends RuntimeException {

    public UsernameAlreadyExistsExceptions() {
        
        super("Username ya existente");
    }
    
}
