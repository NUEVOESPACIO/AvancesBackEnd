package com.mycompany.mavenproject4.entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "simulacion")
public class Simulacion {

    // =========================
    // CAMPOS
    // =========================

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_simulacion")
    private Long idSimulacion;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "foto", nullable = true, columnDefinition = "LONGBLOB")
    private byte[] foto;

    @Column(name = "mime_type")
    private String mimeType;

    @Column(name = "size")
    private Long size;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idusuario")
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

    public void setUser(User user) {
        this.user = user;
    }
}