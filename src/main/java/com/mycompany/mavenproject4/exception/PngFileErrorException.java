
package com.mycompany.mavenproject4.exception;

public class PngFileErrorException extends RuntimeException {
    public PngFileErrorException () {
    super("Archivo PNG no se puedo comprimir");
    }
    
}
