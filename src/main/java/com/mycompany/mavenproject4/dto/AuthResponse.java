package com.mycompany.mavenproject4.dto;

public class AuthResponse {

    public AuthResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }  
    
    private String token;
    
}