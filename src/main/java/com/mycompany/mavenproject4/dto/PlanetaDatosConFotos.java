package com.mycompany.mavenproject4.dto;

import com.mycompany.mavenproject4.entidades.Foto;
import java.util.List;


public class PlanetaDatosConFotos {
    
    private Long idPlaneta;
    private String nombre;
    private Double diametro;
    private Double masa;

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

    public List<Foto> getFotos() {
        return fotos;
    }

    public void setFotos(List<Foto> fotos) {
        this.fotos = fotos;
    }
    private String caracteristicas;
    private String comentarios;
    private String mimeType;
    private byte[] fotoLogoOriginal;
    private byte[] fotoLogoThumb;
    private long userid;
    private List<Foto> fotos;

    
}
