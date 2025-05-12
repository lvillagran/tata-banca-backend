package com.banca.microservicios.Controller;

import com.banca.microservicios.Feign.PersonaClientFeign;
import com.banca.microservicios.Feign.PersonaDTO;
import com.banca.microservicios.dto.CuentaRequestDTO;
import com.banca.microservicios.dto.CuentaResponseDTO;
import com.banca.microservicios.exceptions.GenericException;
import com.banca.microservicios.model.BanCuenta;
import com.banca.microservicios.serviceImpl.BanCuentaServiceImpl;
import com.banca.microservicios.util.getServerIPAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Random;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/operaciones/cuentas")
public class CuentaController {

    private static final Logger log = LoggerFactory.getLogger(CuentaController.class);

    @Autowired
    private BanCuentaServiceImpl cuentaService;

    /** Crear cuenta bancaria */
    @PostMapping("/crear")
    public ResponseEntity<CuentaResponseDTO> crearCuenta(@RequestBody CuentaRequestDTO request) {
        CuentaResponseDTO response = new CuentaResponseDTO();

        try {
            if (request.getIdentificacionCliente() == null || request.getIdentificacionCliente().trim().isEmpty()) {
                response.setMensaje("La identificación del cliente es obligatoria.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

           /** Llamar Cliente Feing */
           /* PersonaDTO persona = personaClientFeign.consultarPorIdentificacion(request.getIdentificacionCliente());
            if (persona == null) {
                response.setMensaje("Cliente no encontrado con esa identificación.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }*/

            BanCuenta cuenta = new BanCuenta();
            cuenta.setNumeroCuenta(generarNumeroCuenta());
            cuenta.setTipoCuenta(request.getTipoCuenta().trim().toUpperCase());
            cuenta.setSaldoInicial(request.getSaldoInicial() != null ? request.getSaldoInicial() : BigDecimal.ZERO);
            cuenta.setSaldoDisponible(request.getSaldoInicial() != null ? request.getSaldoInicial() : BigDecimal.ZERO);
            cuenta.setEstado(true);
            cuenta.setIdentificacionCliente(request.getIdentificacionCliente());
            cuenta.setNombreCliente("PEDRO VELA");
            cuenta.setFechaRegistro(new Date());
            cuenta.setObservacion("CREACION CUENTA");
            cuenta.setIp(getServerIPAddress.getServerIPAddress());

            BanCuenta cuentaCreada = cuentaService.crearCuenta(cuenta);

            response.setCuenta(cuentaCreada);
            response.setMensaje("Cuenta creada correctamente.");
            response.setFechaEjecucion(new Date());

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (GenericException e) {
            response.setMensaje("Error al crear la cuenta: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } catch (Exception e) {
            response.setMensaje("Error desconocido al crear la cuenta.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /** Listar todas las cuentas */
    @GetMapping("/listar")
    public ResponseEntity<List<BanCuenta>> listarCuentas() {
        List<BanCuenta> cuentas = cuentaService.findAll();
        if (cuentas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(cuentas);
    }

    /** Eliminar cuenta por número de cuenta */
    @DeleteMapping("/eliminar/{numeroCuenta}")
    public ResponseEntity<CuentaResponseDTO> eliminarCuenta(@PathVariable String numeroCuenta) {
        CuentaResponseDTO response = new CuentaResponseDTO();

        Optional<BanCuenta> cuentaOpt = cuentaService.findByNumeroCuenta(numeroCuenta.trim());

        if (cuentaOpt.isEmpty()) {
            response.setMensaje("Cuenta con número: " + numeroCuenta + " no encontrada.");
            response.setFechaEjecucion(new Date());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        cuentaService.eliminarCuentaPorNumeroCuenta(numeroCuenta.trim());

        response.setMensaje("Cuenta eliminada correctamente.");
        response.setFechaEjecucion(new Date());
        return ResponseEntity.ok(response);
    }

    /** Actualizar cuenta bancaria */
    @PutMapping("/actualizar/{numeroCuenta}")
    public ResponseEntity<CuentaResponseDTO> actualizarCuenta(@PathVariable String numeroCuenta, @RequestBody CuentaRequestDTO request) {
        CuentaResponseDTO response = new CuentaResponseDTO();

        Optional<BanCuenta> cuentaOpt = cuentaService.findByNumeroCuenta(numeroCuenta.trim());

        if (cuentaOpt.isEmpty()) {
            response.setMensaje("Cuenta con número: " + numeroCuenta + " no encontrada.");
            response.setFechaEjecucion(new Date());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        BanCuenta cuenta = cuentaOpt.get();
        cuenta.setTipoCuenta(request.getTipoCuenta().trim().toUpperCase());
        cuenta.setSaldoInicial(request.getSaldoInicial());
        cuenta.setEstado(request.isEstado());
        cuenta.setFechaActualizacion(new Date());
        cuenta.setIp(getServerIPAddress.getServerIPAddress());

        BanCuenta cuentaActualizada = cuentaService.actualizarCuenta(cuenta);

        response.setCuenta(cuentaActualizada);
        response.setMensaje("Cuenta actualizada correctamente.");
        response.setFechaEjecucion(new Date());

        return ResponseEntity.ok(response);
    }


        public static String generarNumeroCuenta() {
            Random random = new Random();
            StringBuilder numeroCuenta = new StringBuilder();
            for (int i = 0; i < 10; i++) {
                numeroCuenta.append(random.nextInt(10)); // 0 al 9
            }
            return numeroCuenta.toString();
        }

        public static void main(String[] args) {
            System.out.println("Número de cuenta generado: " + generarNumeroCuenta());
        }


}