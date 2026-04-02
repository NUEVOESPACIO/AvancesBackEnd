package com.mycompany.mavenproject4.entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "fotos")
public class Foto {

    // ================= CONSTRUCTOR =================

    public Foto() {}

    // ================= ID =================

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_foto")
    private Long idFoto;

    // ================= ARCHIVO ORIGINAL =================

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "archivo", nullable = false, columnDefinition = "LONGBLOB")
    private byte[] archivo;

    @Column(name = "mime_type", nullable = false, length = 100)
    private String mimeType;

    @Column(name = "nombre_archivo", length = 255, nullable = false)
    private String nombreArchivo;

    @Column(name = "size", nullable = false)
    private Long size;

    // ================= PREVIEW (miniatura) =================

    @Lob
    @Basic(fetch = FetchType.LAZY) // 🔥 CAMBIO IMPORTANTE
    @Column(name = "foto_preview", columnDefinition = "LONGBLOB", nullable = false)
    private byte[] fotoPreview;

    // ================= METADATA =================

    @Column(name = "descripcion", length = 1000)
    private String descripcion;

    // ================= RELACIONES =================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_planeta", nullable = false)
    private Planeta planeta;

    // ================= GETTERS & SETTERS =================

    public Long getIdFoto() {
        return idFoto;
    }

    public void setIdFoto(Long idFoto) {
        this.idFoto = idFoto;
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

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public byte[] getFotoPreview() {
        return fotoPreview;
    }

    public void setFotoPreview(byte[] fotoPreview) {
        this.fotoPreview = fotoPreview;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Planeta getPlaneta() {
        return planeta;
    }

    public void setPlaneta(Planeta planeta) {
        this.planeta = planeta;
    }
}