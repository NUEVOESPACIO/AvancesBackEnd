package com.mycompany.mavenproject4.exception;

public class DefaultImageUserNotFoundException extends RuntimeException {
    
    public DefaultImageUserNotFoundException() {
        super("Imagen default de perfil de usuario no se encuentra");
    }


}

