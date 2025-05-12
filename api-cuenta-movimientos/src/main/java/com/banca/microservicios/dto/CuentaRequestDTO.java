package com.banca.microservicios.dto;

import java.math.BigDecimal;

public class CuentaRequestDTO extends RequestDTO {

    private static final long serialVersionUID = 1L;

    private String tipoCuenta;
    private BigDecimal saldoInicial;
    private boolean estado;
    private String identificacionCliente; // Ahora se usa la identificación del cliente

    // Getters y Setters

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public BigDecimal getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(BigDecimal saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getIdentificacionCliente() {
        return identificacionCliente;
    }

    public void setIdentificacionCliente(String identificacionCliente) {
        this.identificacionCliente = identificacionCliente;
    }
}
