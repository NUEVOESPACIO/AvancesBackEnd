package com.mycompany.mavenproject4.dto;

public class UserInfo {

    private String username;
    private String nombre;
    private String apellido;    

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPerfilAcademico() {
        return perfilAcademico;
    }

    public void setPerfilAcademico(String perfilAcademico) {
        this.perfilAcademico = perfilAcademico;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String RoleName) {
        this.roleName = RoleName;
    }
    private String email;    
    private String perfilAcademico;
    private byte[] foto;    
    private String roleName;

}

    
