package com.banca.microservicios.servicioInterface;

import com.banca.microservicios.exceptions.GenericException;
import com.banca.microservicios.model.BanCuenta;

import java.util.List;
import java.util.Optional;


public interface BanCuentaServiceInterface {

        // Método para crear una nueva cuenta
        BanCuenta crearCuenta(BanCuenta cuenta) throws GenericException;

        // Método para buscar una cuenta por su número de cuenta
        Optional<BanCuenta> findByNumeroCuenta(String numeroCuenta);

        // Método para obtener todas las cuentas
        List<BanCuenta> findAll();

        // Método para eliminar una cuenta por número de cuenta
        void eliminarCuentaPorNumeroCuenta(String numeroCuenta);

        // Método para actualizar una cuenta existente
        BanCuenta actualizarCuenta(BanCuenta cuenta);
    }
