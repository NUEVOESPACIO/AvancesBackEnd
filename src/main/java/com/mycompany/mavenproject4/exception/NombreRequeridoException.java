/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject4.exception;

public class NombreRequeridoException extends RuntimeException {
    public NombreRequeridoException () {
    super("El nombre no puede ser nullo y debe ser unico");
    }
    
}
