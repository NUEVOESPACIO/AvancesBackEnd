package com.mycompany.mavenproject4.dto;

public class AuthResponse {
    
    private String token;
    private String rol;
    private Long id;
    

    public AuthResponse(String token, String rol, Long id) {
        this.token = token;  
        this.rol=rol;
        this.id=id;
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
    
    
    
}

    