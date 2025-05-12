package com.banca.microservicios.dto;

import com.banca.microservicios.model.BanCliente;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class ClienteResponseDTO extends ResponseDTO {

    private BanCliente cliente;
}
