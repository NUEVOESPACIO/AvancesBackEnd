package com.mycompany.mavenproject4.dto;

public class PlanetaDatosSinFotos {
    
    private Long idPlaneta;
    private String nombre;
    private Double diametro;
    private Double masa;
    private String caracteristicas;
    private String comentarios;
    private String mimeType;

    public PlanetaDatosSinFotos(Long idPlaneta, String nombre, Double diametro, Double masa, String caracteristicas, String comentarios, String mimeType, byte[] fotoLogoOriginal, byte[] fotoLogoThumb, long userid) {
        this.idPlaneta = idPlaneta;
        this.nombre = nombre;
        this.diametro = diametro;
        this.masa = masa;
        this.caracteristicas = caracteristicas;
        this.comentarios = comentarios;
        this.mimeType = mimeType;
        this.fotoLogoOriginal = fotoLogoOriginal;
        this.fotoLogoThumb = fotoLogoThumb;
        this.userid = userid;
    }

    public PlanetaDatosSinFotos() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Long getIdPlaneta() {
        return idPlaneta;
    }

    public void setIdPlaneta(Long idPlaneta) {
        this.idPlaneta = idPlaneta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getDiametro() {
        return diametro;
    }

    public void setDiametro(Double diametro) {
        this.diametro = diametro;
    }

    public Double getMasa() {
        return masa;
    }

    public void setMasa(Double masa) {
        this.masa = masa;
    }

    public String getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(String caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public byte[] getFotoLogoOriginal() {
        return fotoLogoOriginal;
    }

    public void setFotoLogoOriginal(byte[] fotoLogoOriginal) {
        this.fotoLogoOriginal = fotoLogoOriginal;
    }

    public byte[] getFotoLogoThumb() {
        return fotoLogoThumb;
    }

    public void setFotoLogoThumb(byte[] fotoLogoThumb) {
        this.fotoLogoThumb = fotoLogoThumb;
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }
    private byte[] fotoLogoOriginal;
    private byte[] fotoLogoThumb;
    private long userid;

    
}
