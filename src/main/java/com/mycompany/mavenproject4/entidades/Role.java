package com.mycompany.mavenproject4.entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role {

    // =========================
    // 🔹 ATRIBUTOS
    // =========================

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING) // 👈 CLAVE
    @Column(name = "nombre", nullable = false, unique = true)
    private RoleName nombre;

    // =========================
    // 🔹 CONSTRUCTORES
    // =========================

    public Role() {
    }

    public Role(RoleName nombre) {
        this.nombre = nombre;
    }

    // =========================
    // 🔹 GETTERS Y SETTERS
    // =========================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoleName getNombre() {
        return nombre;
    }

    public void setNombre(RoleName nombre) {
        this.nombre = nombre;
    }
}