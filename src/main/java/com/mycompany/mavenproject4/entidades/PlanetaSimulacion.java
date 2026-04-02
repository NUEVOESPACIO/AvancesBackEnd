package com.mycompany.mavenproject4.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "planetasensimulaciones")
public class PlanetaSimulacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    public String getNombrePlanetaSimulacion() {
        return nombrePlanetaSimulacion;
    }

    public void setNombrePlanetaSimulacion(String nombrePlanetaSimulacion) {
        this.nombrePlanetaSimulacion = nombrePlanetaSimulacion;
    }

    // 🔗 Relación con Planeta
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idplaneta", nullable = false)
    private Planeta planeta;

    // 🔗 Relación con Simulacion
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idsimulacion", nullable = false)
    private Simulacion simulacion;

    // 🆕 Nombre de la instancia
    @Column(name = "nombre_planeta_simulacion", length = 100, nullable = false)
    private String nombrePlanetaSimulacion;

    @Column(name = "x", nullable = false)
    private Double x;

    @Column(name = "y", nullable = false)
    private Double y;

    @Column(name = "v", nullable = false)
    private Double v;

    @Column(name = "angulo", nullable = false)
    private Double angulo;

    // =========================
    // GETTERS Y SETTERS
    // =========================
    public Long getId() {
        return id;
    }

    public Planeta getPlaneta() {
        return planeta;
    }

    public void setPlaneta(Planeta planeta) {
        this.planeta = planeta;
    }

    public Simulacion getSimulacion() {
        return simulacion;
    }

    public void setSimulacion(Simulacion simulacion) {
        this.simulacion = simulacion;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getV() {
        return v;
    }

    public void setV(Double v) {
        this.v = v;
    }

    public Double getAngulo() {
        return angulo;
    }

    public void setAngulo(Double angulo) {
        this.angulo = angulo;
    }
}
