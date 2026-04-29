
package com.mycompany.mavenproject4.dto;

public class FotoCreate {

    public byte[] getArchivo() {
        return archivo;
    }

    public void setArchivo(byte[] archivo) {
        this.archivo = archivo;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getIdplaneta() {
        return idplaneta;
    }

    public void setIdplaneta(Long idplaneta) {
        this.idplaneta = idplaneta;
    }
    
    private byte[] archivo;
    private String nombreArchivo;
    private String descripcion;
    private Long idplaneta;
    
}
