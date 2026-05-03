package com.mycompany.mavenproject4.dto;

public class SimulacionRequestDTO {

    private String nombre;
    private String descripcion;
    private byte[] fotoHeavy;
    private String mimeType;
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public SimulacionRequestDTO() {
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


    // GETTERS
    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }



    public String getMimeType() {
        return mimeType;
    }

    // SETTERS
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public byte[] getFotoHeavy() {
        return fotoHeavy;
    }

    public void setFotoHeavy(byte[] fotoHeavy) {
        this.fotoHeavy = fotoHeavy;
    }

 

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }
}
