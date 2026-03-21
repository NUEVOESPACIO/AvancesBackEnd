package com.mycompany.mavenproject4.dto;

public class SimulacionHeavyDTO {

    private Long idSimulacion;
    private byte[] foto;
    private String mimeType;
    private Long size;

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public SimulacionHeavyDTO(Long idSimulacion, byte[] foto, String mimeType) {
        this.idSimulacion = idSimulacion;
        this.foto = foto;
        this.mimeType = mimeType;
    }

    public SimulacionHeavyDTO(Long idSimulacion, byte[] foto, String mimeType, Long size) {
        this.idSimulacion = idSimulacion;
        this.foto = foto;
        this.mimeType = mimeType;
        this.size = size;
    }

    public SimulacionHeavyDTO() {
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
