package com.mycompany.mavenproject4.dto;

public class SimulacionLightDTO {

    private final Long idSimulacion;
    private final String nombre;
    private final String descripcion;
    private final String mimeType;
    private final Long sizepreview;
    private byte[] fotopreview;


    public SimulacionLightDTO(Long idSimulacion, String nombre, String descripcion, String mimeType, Long sizepreview, byte[] fotopreview) {
        this.idSimulacion = idSimulacion;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.mimeType = mimeType;
        this.sizepreview = sizepreview;
        this.fotopreview = fotopreview;
    }

    public byte[] getFotopreview() {
        return fotopreview;
    }

    public Long getSizepreview() {
        return sizepreview;
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