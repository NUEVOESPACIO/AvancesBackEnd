package com.mycompany.mavenproject4.entidades;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuarios")
public class User implements UserDetails {  // 👈 agregamos la implementación

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusuario")
    private Long id;

    @Column(name = "Nombre")
    private String nombre;

    @Column(name = "Apellido")
    private String apellido;

    @Column(name = "email")
    private String username;

    @Column(name = "password")
    private String password;

    // 👇 Relación con la tabla roles
    @ManyToOne(fetch = FetchType.EAGER) // cargamos el rol automáticamente
    @JoinColumn(name = "role_id")       // FK en la tabla usuarios
    private Role role;

    // ---- Getters y Setters ----
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getUsername() { // 👈 ya lo usa Spring Security
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() { // 👈 ya lo usa Spring Security
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    // ---- Métodos requeridos por UserDetails ----
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Devuelve el rol del usuario en formato que Spring entienda
        return List.of(new SimpleGrantedAuthority(role.getNombre())); // Ej: ROLE_ADMIN
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // podés personalizarlo más adelante
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}