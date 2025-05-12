package com.tata.banca.model;

import com.banca.microservicios.Controller.MovimientoController;
import com.banca.microservicios.dto.MovimientoFiltroDTO;
import com.banca.microservicios.dto.MovimientoReporteDTO;
import com.banca.microservicios.dto.MovimientoRequestDTO;
import com.banca.microservicios.dto.MovimientoResponseDTO;
import com.banca.microservicios.model.BanCuenta;
import com.banca.microservicios.model.BanMovimientos;
import com.banca.microservicios.serviceImpl.BanCuentaServiceImpl;
import com.banca.microservicios.serviceImpl.BanMovimientoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MovimientosControllerTests {

    @Mock
    private BanMovimientoServiceImpl banMovimientoService;

    @Mock
    private BanCuentaServiceImpl banCuentaService;

    @InjectMocks
    private MovimientoController movimientoController;

    private BanCuenta cuenta;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        cuenta = new BanCuenta();
        cuenta.setNumeroCuenta("1234567890");
        cuenta.setSaldoDisponible(BigDecimal.valueOf(1000));
        cuenta.setSaldoInicial(BigDecimal.valueOf(1000));
    }

    @Test
    @DisplayName("Debe crear un movimiento de depósito con los valores proporcionados")
    public void testCrearMovimiento_Deposito_Exitoso() {
        MovimientoRequestDTO request = new MovimientoRequestDTO();
        request.setNumeroCuenta("1234567890");
        request.setTipoMovimiento("DEPOSITO");
        request.setValor(BigDecimal.valueOf(500));

        when(banCuentaService.findByNumeroCuenta("1234567890")).thenReturn(Optional.of(cuenta));
        when(banMovimientoService.crearMovimiento(any())).thenReturn(new BanMovimientos());

        ResponseEntity<MovimientoResponseDTO> response = movimientoController.crearMovimiento(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("DEPOSITO creado correctamente.", response.getBody().getMensaje());
    }

    @Test
    @DisplayName("Debe retornar error si el número de cuenta es obligatorio al crear un movimiento")
    public void testCrearMovimiento_FaltaNumeroCuenta() {
        MovimientoRequestDTO request = new MovimientoRequestDTO();
        request.setNumeroCuenta(null);
        request.setTipoMovimiento("DEPOSITO");
        request.setValor(BigDecimal.valueOf(500));

        ResponseEntity<MovimientoResponseDTO> response = movimientoController.crearMovimiento(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("El número de cuenta es obligatorio.", response.getBody().getMensaje());
    }

    @Test
    @DisplayName("Debe listar todos los movimientos de la base de datos")
    public void testListarMovimientos_Exitoso() {
        when(banMovimientoService.findAll()).thenReturn(List.of(new BanMovimientos()));

        ResponseEntity<List<BanMovimientos>> response = movimientoController.listarMovimientos();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    @DisplayName("Debe retornar sin contenido si no hay movimientos registrados")
    public void testListarMovimientos_SinContenido() {
        when(banMovimientoService.findAll()).thenReturn(List.of());

        ResponseEntity<List<BanMovimientos>> response = movimientoController.listarMovimientos();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @DisplayName("Debe eliminar los movimientos asociados a una cuenta")
    public void testEliminarMovimientosPorCuenta_Exitoso() {
        when(banCuentaService.findByNumeroCuenta("1234567890")).thenReturn(Optional.of(cuenta));

        ResponseEntity<MovimientoResponseDTO> response = movimientoController.eliminarPorCuenta("1234567890");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Movimientos eliminados correctamente.", response.getBody().getMensaje());

        verify(banMovimientoService).eliminarPorCuenta(cuenta);
    }

    @Test
    @DisplayName("Debe retornar error si la cuenta no existe al intentar eliminar movimientos")
    public void testEliminarMovimientosPorCuenta_NoEncontrada() {
        when(banCuentaService.findByNumeroCuenta("9876543210")).thenReturn(Optional.empty());

        ResponseEntity<MovimientoResponseDTO> response = movimientoController.eliminarPorCuenta("9876543210");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Cuenta no encontrada.", response.getBody().getMensaje());
    }

    @Test
    @DisplayName("Debe generar un reporte de movimientos por cliente y fecha")
    public void testObtenerMovimientosReporte_Exitoso() {
        MovimientoFiltroDTO request = new MovimientoFiltroDTO();
        request.setIdentificaciones("123456789");
        request.setFechaInicio("2025-01-01");
        request.setFechaFin("2025-12-31");

        when(banMovimientoService.obtenerMovimientosPorClienteYFechas("123456789", "2025-01-01", "2025-12-31"))
                .thenReturn(List.of(new MovimientoReporteDTO()));

        ResponseEntity<List<MovimientoReporteDTO>> response = movimientoController.obtenerMovimientos(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }
}
