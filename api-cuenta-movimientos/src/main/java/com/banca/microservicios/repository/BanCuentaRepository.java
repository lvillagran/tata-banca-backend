package com.banca.microservicios.repository;

import com.banca.microservicios.model.BanCuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BanCuentaRepository extends JpaRepository<BanCuenta, Long> {

    Optional<BanCuenta> findByNumeroCuenta(String numeroCuenta);

}