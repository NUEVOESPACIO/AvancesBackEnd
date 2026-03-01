package com.mycompany.mavenproject4.entidades;


import jakarta.persistence.*;

@Entity
@Table(name = "planetas")
public class Planeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_planeta")
    private Long idPlaneta;

    @Column(name = "nombre")
    private String nombre;

    public Long getIdPlaneta() {
        return idPlaneta;
    }

    public void setIdPlaneta(Long idPlaneta) {
        this.idPlaneta = idPlaneta;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNombre_cientifico() {
        return nombre_cientifico;
    }

    public void setNombre_cientifico(String nombre_cientifico) {
        this.nombre_cientifico = nombre_cientifico;
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

    public String getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(String caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public Double getMasa() {
        return masa;
    }

    public void setMasa(Double masa) {
        this.masa = masa;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "nombre_cientifico")
    private String nombre_cientifico;

    @Column(name = "diametro")
    private Double diametro;

    


    @Column(name = "caracteristicas")
    private String caracteristicas;

    @Column(name = "masa")
    private Double masa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    private User user;






}
