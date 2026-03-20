package com.mycompany.mavenproject4.dto;

public class SimulacionHeavyDTO {

    private final Long idSimulacion;
    private final byte[] foto;
    private final String mimeType;

    public SimulacionHeavyDTO(Long idSimulacion, byte[] foto, String mimeType) {
        this.idSimulacion = idSimulacion;
        this.foto = foto;
        this.mimeType = mimeType;
    }

    public Long getIdSimulacion() {
        return idSimulacion;
    }

    public byte[] getFoto() {
        return foto;
    }

    public String getMimeType() {
        return mimeType;
    }
}