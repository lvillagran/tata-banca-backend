package com.banca.microservicios.Controller;

import com.banca.microservicios.dto.MovimientoFiltroDTO;
import com.banca.microservicios.dto.MovimientoReporteDTO;
import com.banca.microservicios.dto.MovimientoRequestDTO;
import com.banca.microservicios.dto.MovimientoResponseDTO;
import com.banca.microservicios.model.BanCuenta;
import com.banca.microservicios.model.BanMovimientos;
import com.banca.microservicios.serviceImpl.BanCuentaServiceImpl;
import com.banca.microservicios.serviceImpl.BanMovimientoServiceImpl;
import com.banca.microservicios.util.getServerIPAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.banca.microservicios.util.MensajesSistema.DEPOSITO;
import static com.banca.microservicios.util.MensajesSistema.RETIRO;

@RestController
@RequestMapping("/api/v1/operaciones/movimientos")
public class MovimientoController {

    @Autowired
    private BanMovimientoServiceImpl banMovimientoService;

    @Autowired
    private BanCuentaServiceImpl banCuentaService;

    /** Crear movimiento */
    @PostMapping("/crear")
    public ResponseEntity<MovimientoResponseDTO> crearMovimiento(@RequestBody MovimientoRequestDTO request) {
        MovimientoResponseDTO response = new MovimientoResponseDTO();

        try {
            if (request.getNumeroCuenta() == null) {
                response.setMensaje("El número de cuenta es obligatorio.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            Optional<BanCuenta> cuentaOpt = banCuentaService.findByNumeroCuenta(request.getNumeroCuenta());
            if (cuentaOpt.isEmpty()) {
                response.setMensaje("Cuenta no encontrada.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            BanCuenta cuenta = cuentaOpt.get();
            BigDecimal saldoActual = cuenta.getSaldoDisponible();
            BigDecimal valorMovimiento = request.getValor();

            /** Reglas de validación */
            if (valorMovimiento == null || valorMovimiento.compareTo(BigDecimal.ZERO) == 0) {
                response.setMensaje("El valor del movimiento no puede ser nulo o cero.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            BigDecimal nuevoSaldo;

            /** Si el tipo de movimiento es DEPOSITO, sumamos el valor */
            if (DEPOSITO.equalsIgnoreCase(request.getTipoMovimiento())) {
                nuevoSaldo = saldoActual.add(valorMovimiento);
            }

            /** Si el tipo de movimiento es RETIRO, restamos el valor */
            else if (RETIRO.equalsIgnoreCase(request.getTipoMovimiento())) {
                nuevoSaldo = saldoActual.subtract(valorMovimiento);
            } else {
                response.setMensaje("Tipo de movimiento inválido.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            /** Validar si hay fondos suficientes si el movimiento es negativo y tipo RETIRO */
            if (RETIRO.equalsIgnoreCase(request.getTipoMovimiento()) && nuevoSaldo.compareTo(BigDecimal.ZERO) < 0) {
                response.setMensaje("Saldo insuficiente para realizar el retiro.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            /** Crear el movimiento */
            BanMovimientos movimiento = new BanMovimientos();
            movimiento.setFechaMovimiento(new Date());
            movimiento.setMovimiento(request.getTipoMovimiento().trim().toUpperCase() + " valor " + valorMovimiento);
            movimiento.setValor(valorMovimiento);
            movimiento.setCuenta(cuenta);
            movimiento.setFechaRegistro(new Date());
            movimiento.setEstado(true);
            movimiento.setObservacion("CREACION " + request.getTipoMovimiento().trim().toUpperCase());
            movimiento.setNumeroCuenta(request.getNumeroCuenta().trim().toUpperCase());
            movimiento.setSaldo(nuevoSaldo);
            movimiento.setIp(getServerIPAddress.getServerIPAddress());

            BanMovimientos movimientoCreado = banMovimientoService.crearMovimiento(movimiento);

            response.setMovimientos(movimientoCreado);
            response.setMensaje(request.getTipoMovimiento().trim().toUpperCase() + " creado correctamente.");
            response.setFechaEjecucion(new Date());

            /** Actualizar saldo disponible de la cuenta */
            cuenta.setSaldoDisponible(nuevoSaldo);
            banCuentaService.actualizarCuenta(cuenta); // Debes tener este método implementado

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            response.setMensaje("Error desconocido al crear el movimiento.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    /** Listar todos los movimientos */
    @GetMapping("/listar")
    public ResponseEntity<List<BanMovimientos>> listarMovimientos() {
        List<BanMovimientos> movimientos = banMovimientoService.findAll();
        if (movimientos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(movimientos);
    }

    /** Eliminar movimientos por ID de cuenta */
    @DeleteMapping("/eliminar/{numeroCuenta}")
    public ResponseEntity<MovimientoResponseDTO> eliminarPorCuenta(@PathVariable String numeroCuenta) {
        MovimientoResponseDTO response = new MovimientoResponseDTO();

        Optional<BanCuenta> cuentaOpt = banCuentaService.findByNumeroCuenta(numeroCuenta);
        if (cuentaOpt.isEmpty()) {
            response.setMensaje("Cuenta no encontrada.");
            response.setFechaEjecucion(new Date());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        banMovimientoService.eliminarPorCuenta(cuentaOpt.get());

        response.setMensaje("Movimientos eliminados correctamente.");
        response.setFechaEjecucion(new Date());
        return ResponseEntity.ok(response);
    }

    /** Consulta reporte movimientos */
    @PostMapping("/reporte")
    public ResponseEntity<List<MovimientoReporteDTO>> obtenerMovimientos(@RequestBody MovimientoFiltroDTO request) {
        String identificacion = request.getIdentificaciones();
        String fechaInicio = request.getFechaInicio();
        String fechaFin = request.getFechaFin();

        List<MovimientoReporteDTO> movimientos = banMovimientoService.obtenerMovimientosPorClienteYFechas(identificacion, fechaInicio, fechaFin);

        return ResponseEntity.ok(movimientos);
    }
}
