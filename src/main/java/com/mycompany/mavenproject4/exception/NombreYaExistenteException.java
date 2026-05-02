/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject4.exception;

/**
 *
 * @author Usuario
 */
public class NombreYaExistenteException extends RuntimeException {

    public NombreYaExistenteException() {
        super("El nombre ya ha sido creado");
    }

}
