package com.mycompany.mavenproject4.entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "fotos")
public class Foto {

    public Foto() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idfoto")
    private Long idfoto;

    @Lob
    @Basic(fetch = FetchType.LAZY) // 🔥 importante para rendimiento
    @Column(name = "archivo", nullable = false)
    private byte[] archivo;

    @Column(name = "mime_type", nullable = false, length = 100)
    private String mimeType;

    @Column(name = "nombre_original")
    private String nombreOriginal;

    @Column(name = "size")
    private Long size;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "clasificacion")
    private String clasificacion;

    @Column(name = "tablaasociada")
    private String tablaasociada;

    @Column(name = "idasociado")
    private Long idasociado;

    // ---------------- GETTERS & SETTERS ----------------

    public Long getIdfoto() {
        return idfoto;
    }

    public void setIdfoto(Long idfoto) {
        this.idfoto = idfoto;
    }

    public byte[] getArchivo() {
        return archivo;
    }

    public void setArchivo(byte[] archivo) {
        this.archivo = archivo;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getNombreOriginal() {
        return nombreOriginal;
    }

    public void setNombreOriginal(String nombreOriginal) {
        this.nombreOriginal = nombreOriginal;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    public String getTablaasociada() {
        return tablaasociada;
    }

    public void setTablaasociada(String tablaasociada) {
        this.tablaasociada = tablaasociada;
    }

    public Long getIdasociado() {
        return idasociado;
    }

    public void setIdasociado(Long idasociado) {
        this.idasociado = idasociado;
    }
}