package com.mycompany.mavenproject4.exception;

public class FotoNoEsJpgException extends RuntimeException {

    public FotoNoEsJpgException() {
        super("Foto del Planeta no es JPG");
    }

}
