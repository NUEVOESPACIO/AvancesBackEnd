package com.mycompany.mavenproject4.dto;

public class AuthResponse {
    
    private String token;
    private String rol;
    private Long id;
    private String username;
    private String nombre;
    private String apellido;
    private String email;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
         
  


    public AuthResponse(String token, String rol, Long id, String username, String nombre, String apellido, String email) {
        this.token = token;
        this.rol = rol;
        this.id = id;
        this.username = username;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = rol;
    }
    
    
    
}

    