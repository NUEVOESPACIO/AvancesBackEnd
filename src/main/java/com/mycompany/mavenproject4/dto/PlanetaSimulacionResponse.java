package com.mycompany.mavenproject4.dto;

public class PlanetaSimulacionResponse {

    // Datos de PlanetaSimulacion
    private String nombrePlanetaSimulacion;
    private Double x;
    private Double y;
    private Double v;
    private Double angulo;
    private String payload;

    // Datos del Planeta
    private String nombrePlaneta;
    private Double diametro;
    private Double masa;
    private byte[] foto;
    private String mimeType;

    public PlanetaSimulacionResponse(
            String nombrePlanetaSimulacion,
            Double x,
            Double y,
            Double v,
            Double angulo,
            String payload,
            String nombrePlaneta,
            Double diametro,
            Double masa,
            byte[] foto,
            String mimeType
    ) {
        this.nombrePlanetaSimulacion = nombrePlanetaSimulacion;
        this.x = x;
        this.y = y;
        this.v = v;
        this.angulo = angulo;
        this.payload = payload;
        this.nombrePlaneta = nombrePlaneta;
        this.diametro = diametro;
        this.masa = masa;
        this.foto = foto;
        this.mimeType = mimeType;
    }

    // getters (omitidos por brevedad)
}