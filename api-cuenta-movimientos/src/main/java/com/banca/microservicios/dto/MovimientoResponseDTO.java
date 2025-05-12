package com.banca.microservicios.dto;

import com.banca.microservicios.model.BanMovimientos;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovimientoResponseDTO extends ResponseDTO {

    private BanMovimientos movimientos;
}
