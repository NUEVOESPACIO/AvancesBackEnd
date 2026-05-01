
package com.mycompany.mavenproject4.exception;


public class FotoNoEncontradaException extends RuntimeException {

    public FotoNoEncontradaException() {
        super("Foto Para Borrar No Encontrada");
    }
    
}
