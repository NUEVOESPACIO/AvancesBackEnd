package com.mycompany.mavenproject4.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super("Usuario no encontrado");
    }

}