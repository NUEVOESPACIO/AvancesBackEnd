package com.mycompany.mavenproject4.exception;

public class MasaRequeridaException extends RuntimeException {
    
    public MasaRequeridaException () {
        super("La masa del planeta no puede ser nula");
    }


}

