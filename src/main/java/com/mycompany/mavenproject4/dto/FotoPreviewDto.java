package com.mycompany.mavenproject4.dto;

public class FotoPreviewDto {

    private Long id;
    private String nombreArchivo;
    private String descripcion;
    private byte[] preview;
    private Long idplaneta;

    public FotoPreviewDto(Long id, String nombreArchivo, String descripcion, byte[] preview, Long idplaneta) {
        this.id = id;
        this.nombreArchivo = nombreArchivo;
        this.descripcion = descripcion;
        this.preview = preview;
        this.idplaneta = idplaneta;
    }



    // getters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public byte[] getPreview() {
        return preview;
    }

    public void setPreview(byte[] preview) {
        this.preview = preview;
    }

    public Long getIdplaneta() {
        return idplaneta;
    }

    public void setIdplaneta(Long idplaneta) {
        this.idplaneta = idplaneta;
    }
}