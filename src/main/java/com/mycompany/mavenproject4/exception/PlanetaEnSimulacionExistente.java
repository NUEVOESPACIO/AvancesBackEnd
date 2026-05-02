
package com.mycompany.mavenproject4.exception;
   
public class PlanetaEnSimulacionExistente extends RuntimeException {

    public PlanetaEnSimulacionExistente() {
        super("No se puede borrar este planeta porque esta siendo usado en alguna simulacion");
    }

}
    

