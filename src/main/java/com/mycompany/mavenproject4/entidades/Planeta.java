package com.mycompany.mavenproject4.entidades;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "planetas")
public class Planeta {

    // ================= ID =================

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_planeta")
    private Long idPlaneta;

    // ================= DATOS =================

    @Column(name = "nombre_identificatorio", nullable = false)
    private String nombre;

    @Column(name = "diametro")
    private Double diametro;

    @Column(name = "masa")
    private Double masa;

    @Column(name = "caracteristicas", length = 2000)
    private String caracteristicas;

    @Column(name = "comentarios", length = 2000)
    private String comentarios;

    // ================= LOGO (imagen liviana) =================

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "foto_logo", columnDefinition = "LONGBLOB")
    private byte[] fotoLogo;

    @Column(name = "mime_type", length = 100)
    private String mimeType;

    // ================= RELACIONES =================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    private User user;

    @OneToMany(
        mappedBy = "planeta",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    private List<Foto> fotos = new ArrayList<>();

    // ================= HELPERS =================

    public void addFoto(Foto foto) {
        fotos.add(foto);
        foto.setPlaneta(this);
    }

    public void removeFoto(Foto foto) {
        fotos.remove(foto);
        foto.setPlaneta(null);
    }

    // ================= GETTERS & SETTERS =================

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

    public byte[] getFotoLogo() {
        return fotoLogo;
    }

    public void setFotoLogo(byte[] fotoLogo) {
        this.fotoLogo = fotoLogo;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Foto> getFotos() {
        return fotos;
    }

    public void setFotos(List<Foto> fotos) {
        this.fotos = fotos;
    }
}