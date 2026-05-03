package com.mycompany.mavenproject4.dto;

public class SimulacionLightDTO {

    private final Long idSimulacion;
    private final String nombre;
    private final String descripcion;
    private final String mimeType;
    private byte[] fotopreview;

    public SimulacionLightDTO(Long idSimulacion, String nombre, String descripcion, String mimeType,  byte[] fotopreview) {
        this.idSimulacion = idSimulacion;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.mimeType = mimeType;

        this.fotopreview = fotopreview;
    }

    public byte[] getFotopreview() {
        return fotopreview;
    }

    public void setFotopreview(byte[] fotopreview) {
        this.fotopreview = fotopreview;
    }

    public Long getIdSimulacion() {
        return idSimulacion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getMimeType() {
        return mimeType;
    }

}
