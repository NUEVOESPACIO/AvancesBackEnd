package com.mycompany.mavenproject4.dto;

public class SimulacionLightDTO {

    private final Long idSimulacion;
    private final String nombre;
    private final String descripcion;
    private final String mimeType;
    private final Long size;

    public SimulacionLightDTO(Long idSimulacion,
                              String nombre,
                              String descripcion,
                              String mimeType,
                              Long size) {

        this.idSimulacion = idSimulacion;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.mimeType = mimeType;
        this.size = size;
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

    public Long getSize() {
        return size;
    }
}