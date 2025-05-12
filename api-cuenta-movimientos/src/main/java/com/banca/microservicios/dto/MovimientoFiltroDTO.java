package com.banca.microservicios.dto;

import java.util.List;

public class MovimientoFiltroDTO {

    private String identificaciones;
    private String fechaInicio;
    private String fechaFin;

    // Getters y setters
    public String getIdentificaciones() {
        return identificaciones;
    }

    public void setIdentificaciones(String identificaciones) {
        this.identificaciones = identificaciones;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }
}
