package com.banca.microservicios.estructura;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
public abstract class BaseEntidadAuditoria implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "FECHA_ACTUALIZACION")
    private Date fechaActualizacion;

    @Column(name = "OBSERVACION")
    private String observacion;

    @Column(name = "FECHA_REGISTRO")
    private Date fechaRegistro;

    @Column(name = "IP")
    private String ip;

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}