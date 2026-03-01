
package com.mycompany.mavenproject4.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "simplementefotos")
public class OnlyPhotos {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idfoto")
    private Long idf;
    
    @Lob
    @Column(name = "archivo")
    private byte[] archivo;

    public Long getIdf() {
        return idf;
    }

    public void setIdf(Long idf) {
        this.idf = idf;
    }

    public byte[] getArchivo() {
        return archivo;
    }

    public void setArchivo(byte[] archivo) {
        this.archivo = archivo;
    }
    
    
    
}
