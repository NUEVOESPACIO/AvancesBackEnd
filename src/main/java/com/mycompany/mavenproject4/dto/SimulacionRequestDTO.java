package com.mycompany.mavenproject4.dto;

public class SimulacionRequestDTO {

    private String nombre;
    private String descripcion;
    private String fotoBase64;
    private String mimeType;
    private Long userId;

    public Long getUserId() {
        return userId;
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

    public String getFotoBase64() {
        return fotoBase64;
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

    public void setFotoBase64(String fotoBase64) {
        this.fotoBase64 = fotoBase64;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }
}
