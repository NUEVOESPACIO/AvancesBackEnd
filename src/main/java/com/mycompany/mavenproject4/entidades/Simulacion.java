package com.mycompany.mavenproject4.entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "simulaciones")
public class Simulacion {

    // =========================
    // ID
    // =========================
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_simulacion")
    private Long idSimulacion;

    // =========================
    // DATOS BÁSICOS
    // =========================
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "descripcion", nullable = true)
    private String descripcion;

    // =========================
    // IMAGEN ORIGINAL
    // =========================
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "foto", columnDefinition = "LONGBLOB", nullable = false)
    private byte[] foto;

    @Column(name = "mime_type", nullable = false)
    private String mimeType;

    @Column(name = "size", nullable = false)
    private Long size;

    // =========================
    // IMAGEN PREVIEW
    // =========================
    @Lob
    @Basic(fetch = FetchType.EAGER)
    @Column(name = "foto_preview", columnDefinition = "LONGBLOB", nullable = false)
    private byte[] fotoPreview;

    // =========================
    // RELACIONES
    // =========================
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idusuario", nullable = false)
    private User user;

    // =========================
    // GETTERS
    // =========================
    public Long getIdSimulacion() {
        return idSimulacion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public byte[] getFoto() {
        return foto;
    }

    public String getMimeType() {
        return mimeType;
    }

    public Long getSize() {
        return size;
    }

    public byte[] getFotoPreview() {
        return fotoPreview;
    }

    public User getUser() {
        return user;
    }

    // =========================
    // SETTERS
    // =========================
    public void setIdSimulacion(Long idSimulacion) {
        this.idSimulacion = idSimulacion;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public void setFotoPreview(byte[] fotoPreview) {
        this.fotoPreview = fotoPreview;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
