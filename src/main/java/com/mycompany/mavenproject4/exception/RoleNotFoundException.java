package com.mycompany.mavenproject4.exception;

public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException() {
        super("Rol No Encontrado");
    }
}