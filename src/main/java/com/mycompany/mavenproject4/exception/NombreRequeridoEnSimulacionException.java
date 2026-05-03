package com.mycompany.mavenproject4.exception;


public class NombreRequeridoEnSimulacionException extends RuntimeException {
    public NombreRequeridoEnSimulacionException() {
        super("La Simulacion debe tener nombre");
    }
    
    
    
}
