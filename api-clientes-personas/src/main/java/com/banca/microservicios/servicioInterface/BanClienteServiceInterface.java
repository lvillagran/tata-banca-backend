package com.banca.microservicios.servicioInterface;

import com.banca.microservicios.dto.ClienteRequestDTO;
import com.banca.microservicios.exceptions.GenericException;
import com.banca.microservicios.model.BanCliente;

import java.util.List;
import java.util.Optional;

public interface BanClienteServiceInterface {
    BanCliente crearCliente(BanCliente cliente) throws GenericException;

    // Método para buscar un cliente por su identificación
    Optional<BanCliente> findByIdentificacion(String identificacion);

    List<BanCliente> findAll();

    void eliminarClientePorIdentificacion(String identificacion);

    BanCliente actualizarCliente(BanCliente cliente);
}
