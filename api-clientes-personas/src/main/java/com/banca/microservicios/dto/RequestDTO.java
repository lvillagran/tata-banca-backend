package com.banca.microservicios.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class RequestDTO {
    private Date fechaEjecucion;
    private String mensaje;
}
