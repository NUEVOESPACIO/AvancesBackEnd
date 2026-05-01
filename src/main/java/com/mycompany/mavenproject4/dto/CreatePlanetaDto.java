
package com.mycompany.mavenproject4.dto;

import jakarta.validation.constraints.Size;
import java.util.List;

public class CreatePlanetaDto {

    private String nombre;
    private Double diametro;

    public byte[] getArchivo() {
        return archivo;
    }

    public void setArchivo(byte[] archivo) {
        this.archivo = archivo;
    }

    public String getMimetype() {
        return mimetype;
    }

    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }
    private Double masa;
    private byte[] archivo;
    private String mimetype;

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

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public List<FotoCreate> getFotos() {
        return fotos;
    }

    public void setFotos(List<FotoCreate> fotos) {
        this.fotos = fotos;
    }
    private String caracteristicas;
    private String comentarios;
    private Long idUsuario;

    @Size(max = 3)
    private List<FotoCreate> fotos;

    // getters y setters
}