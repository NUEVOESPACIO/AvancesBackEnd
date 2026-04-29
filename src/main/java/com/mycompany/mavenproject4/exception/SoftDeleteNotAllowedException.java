
package com.mycompany.mavenproject4.exception;


    
    public class SoftDeleteNotAllowedException extends RuntimeException {
    public SoftDeleteNotAllowedException() {
        super("No Se puede Borrar Usuarios");
    }    
    
}
