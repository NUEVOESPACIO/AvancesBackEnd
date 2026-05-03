package com.mycompany.mavenproject4.dto;

public class PlanetaSimulacionRequest {

    private Long idPlaneta;
    private Long idSimulacion;
    private String nombrePlanetaSimulacion;
    private Double x;
    private Double y;
    private Double v;
    private Double angulo;
    private String payload;

    public Long getIdPlaneta() {
        return idPlaneta;
    }

    public void setIdPlaneta(Long idPlaneta) {
        this.idPlaneta = idPlaneta;
    }

    public Long getIdSimulacion() {
        return idSimulacion;
    }

    public void setIdSimulacion(Long idSimulacion) {
        this.idSimulacion = idSimulacion;
    }

    public String getNombrePlanetaSimulacion() {
        return nombrePlanetaSimulacion;
    }

    public void setNombrePlanetaSimulacion(String nombrePlanetaSimulacion) {
        this.nombrePlanetaSimulacion = nombrePlanetaSimulacion;
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

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}