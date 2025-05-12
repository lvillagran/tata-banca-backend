package com.banca.microservicios.serviceImpl;

import com.banca.microservicios.exceptions.GenericException;
import com.banca.microservicios.model.BanCuenta;
import com.banca.microservicios.repository.BanCuentaRepository;
import com.banca.microservicios.servicioInterface.BanCuentaServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class BanCuentaServiceImpl implements BanCuentaServiceInterface {

        @Autowired
        private BanCuentaRepository banCuentaRepository;

        @Override
        public BanCuenta crearCuenta(BanCuenta cuenta) throws GenericException {
            return banCuentaRepository.save(cuenta);
        }

        @Override
        public Optional<BanCuenta> findByNumeroCuenta(String numeroCuenta) {
            return banCuentaRepository.findByNumeroCuenta(numeroCuenta.trim());
        }

        @Override
        public List<BanCuenta> findAll() {
            return banCuentaRepository.findAll();
        }

        @Override
        public void eliminarCuentaPorNumeroCuenta(String numeroCuenta) {
            Optional<BanCuenta> cuenta = banCuentaRepository.findByNumeroCuenta(numeroCuenta.trim());
            cuenta.ifPresent(banCuentaRepository::delete);
        }

        @Override
        public BanCuenta actualizarCuenta(BanCuenta cuenta) {
            return banCuentaRepository.save(cuenta);
        }
    }