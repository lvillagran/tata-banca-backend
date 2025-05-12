package com.banca.microservicios.dto;

import com.banca.microservicios.model.BanCuenta;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CuentaResponseDTO extends ResponseDTO {

    private BanCuenta cuenta;
}
