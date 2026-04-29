package com.mycompany.mavenproject4.dto;

public class UserUpdateRequest {

    private String username;
    private String nombre;
    private String apellido;

    public UserUpdateRequest(String username, String nombre, String apellido, String email, String password, String perfilAcademico, byte[] foto, String mimeType, String RoleName) {
        this.username = username;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
        this.perfilAcademico = perfilAcademico;
        this.foto = foto;
        this.mimeType = mimeType;
        this.RoleName = RoleName;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getRoleName() {
        return RoleName;
    }

    public void setRoleName(String RoleName) {
        this.RoleName = RoleName;
    }
    private String email;
    private String password;
    private String perfilAcademico;
    private byte[] foto;
    private String mimeType;
    private String RoleName;

}
