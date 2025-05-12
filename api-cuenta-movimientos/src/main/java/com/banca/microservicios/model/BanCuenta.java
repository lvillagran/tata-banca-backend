package com.banca.microservicios.model;


import com.banca.microservicios.estructura.BaseEntidadAuditoria;
import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "TAB_CUENTA", schema = "operaciones")
public class BanCuenta extends BaseEntidadAuditoria implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String numeroCuenta;
    private  String tipoCuenta;
    private BigDecimal saldoInicial;
    private  boolean estado;

    private BigDecimal saldoDisponible;

 /** Llenado desde otro microservicio*/
    private String identificacionCliente;
    private String nombreCliente;


    @Id
    @GeneratedValue(generator = "secTabCuenta", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "secTabCuenta", allocationSize = 1, initialValue = 1, sequenceName = "SEC_CUENTA")
    @Column(name = "ID_CUENTA")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "numero_cuenta")
    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    @Column(name = "TIPO_CUENTA")
    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    @Column(name = "SALDO_INICIAL")
    public BigDecimal getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(BigDecimal saldoInicial) {
        this.saldoInicial = saldoInicial;
    }


    @Column(name = "ESTADO")
    public boolean getEstado() {
        return estado;
    }


    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Column(name = "IDENTIFICACION_CLIENTE", length = 10)
    public String getIdentificacionCliente() {
        return identificacionCliente;
    }

    public void setIdentificacionCliente(String identificacionCliente) {
        this.identificacionCliente = identificacionCliente;
    }

    @Column(name = "NOMBRE_CLIENTE" , length = 100)
    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    @Column(name = "saldo_disponible" )
    public BigDecimal getSaldoDisponible() {
        return saldoDisponible;
    }

    public void setSaldoDisponible(BigDecimal saldoDisponible) {
        this.saldoDisponible = saldoDisponible;
    }
}