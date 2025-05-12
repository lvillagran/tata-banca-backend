package com.banca.microservicios.model;

import com.banca.microservicios.estructura.BaseEntidadAuditoria;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "TAB_MOVIMIENTOS", schema = "operaciones")
public class BanMovimientos extends BaseEntidadAuditoria implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Date fechaMovimiento;
    private  String movimiento;
    private BigDecimal valor;
    private BigDecimal saldo;
    private  String numeroCuenta;
    private  Boolean estado;
    private BanCuenta cuenta;


    @Id
    @GeneratedValue(generator = "secTabMovimiento", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "secTabMovimiento", allocationSize = 1, initialValue = 1, sequenceName = "SEC_MOVIMIENTO")
    @Column(name = "ID_MOVIMIENTO")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "FECHA_MOVIMIENTO")
    public Date getFechaMovimiento() {
        return fechaMovimiento;
    }

    public void setFechaMovimiento(Date fechaMovimiento) {
        this.fechaMovimiento = fechaMovimiento;
    }

    @Column(name = "MOVIMIENTO")
    public String getMovimiento() {
        return movimiento;
    }

    public void setMovimiento(String movimiento) {
        this.movimiento = movimiento;
    }


    @Column(name = "VALOR_MOVIMIENTO")
    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    @Column(name = "SALDO")
    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }


    @ManyToOne
    @JoinColumn(name = "ID_CUENTA")
    public BanCuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(BanCuenta cuenta) {
        this.cuenta = cuenta;
    }

    @Column(name = "numero_cuenta")
    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    @Column(name = "ESTADO")
    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}