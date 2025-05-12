package com.tata.banca.model;
import com.banca.microservicios.Controller.CuentaController;
import com.banca.microservicios.dto.CuentaRequestDTO;
import com.banca.microservicios.dto.CuentaResponseDTO;
import com.banca.microservicios.exceptions.GenericException;
import com.banca.microservicios.model.BanCuenta;
import com.banca.microservicios.serviceImpl.BanCuentaServiceImpl;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CuentaControllerTests {

    @Mock
    private BanCuentaServiceImpl cuentaService;

    @InjectMocks
    private CuentaController cuentaController;

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
    @DisplayName("Debe crear una cuenta con éxito con los valores proporcionados")
    public void testCrearCuenta_Exitoso() throws GenericException {
        CuentaRequestDTO request = new CuentaRequestDTO();
        request.setIdentificacionCliente("123456789");
        request.setTipoCuenta("Ahorros");
        request.setSaldoInicial(BigDecimal.valueOf(1000));

        when(cuentaService.crearCuenta(any())).thenReturn(cuenta);

        ResponseEntity<CuentaResponseDTO> response = cuentaController.crearCuenta(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Cuenta creada correctamente.", response.getBody().getMensaje());
    }


    @Test
    @DisplayName("Debe devolver error al crear una cuenta sin identificación del cliente")
    public void testCrearCuenta_FaltaIdentificacion() {
        CuentaRequestDTO request = new CuentaRequestDTO();
        request.setIdentificacionCliente(null);
        request.setTipoCuenta("Ahorros");

        ResponseEntity<CuentaResponseDTO> response = cuentaController.crearCuenta(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("La identificación del cliente es obligatoria.", response.getBody().getMensaje());
    }

    @Test
    @DisplayName("Debe listar todas las cuentas con éxito")
    public void testListarCuentas_Exitoso() {
        when(cuentaService.findAll()).thenReturn(List.of(cuenta));

        ResponseEntity<List<BanCuenta>> response = cuentaController.listarCuentas();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    @DisplayName("Debe devolver un estado No Content cuando no haya cuentas")
    public void testListarCuentas_SinCuentas() {
        when(cuentaService.findAll()).thenReturn(List.of());

        ResponseEntity<List<BanCuenta>> response = cuentaController.listarCuentas();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @DisplayName("Debe eliminar una cuenta con éxito")
    public void testEliminarCuenta_Exitoso() {
        when(cuentaService.findByNumeroCuenta("1234567890")).thenReturn(Optional.of(cuenta));

        ResponseEntity<CuentaResponseDTO> response = cuentaController.eliminarCuenta("1234567890");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Cuenta eliminada correctamente.", response.getBody().getMensaje());

        verify(cuentaService).findByNumeroCuenta("1234567890");
    }

    @Test
    @DisplayName("Debe devolver error si la cuenta no existe al intentar eliminarla")
    public void testEliminarCuenta_NoEncontrada() {
        when(cuentaService.findByNumeroCuenta("9876543210")).thenReturn(Optional.empty());

        ResponseEntity<CuentaResponseDTO> response = cuentaController.eliminarCuenta("9876543210");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Cuenta con número: 9876543210 no encontrada.", response.getBody().getMensaje());
    }

    @Test
    @DisplayName("Debe actualizar una cuenta con éxito con los valores proporcionados")
    public void testActualizarCuenta_Exitoso() {
        CuentaRequestDTO request = new CuentaRequestDTO();
        request.setTipoCuenta("Corriente");
        request.setSaldoInicial(BigDecimal.valueOf(2000));
        request.setEstado(true);

        when(cuentaService.findByNumeroCuenta("1234567890")).thenReturn(Optional.of(cuenta));
        when(cuentaService.actualizarCuenta(any())).thenReturn(cuenta);

        ResponseEntity<CuentaResponseDTO> response = cuentaController.actualizarCuenta("1234567890", request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Cuenta actualizada correctamente.", response.getBody().getMensaje());
    }

    @Test
    @DisplayName("Debe devolver error si la cuenta no existe al intentar actualizarla")
    public void testActualizarCuenta_NoEncontrada() {
        CuentaRequestDTO request = new CuentaRequestDTO();
        request.setTipoCuenta("Corriente");
        request.setSaldoInicial(BigDecimal.valueOf(2000));
        request.setEstado(true);

        when(cuentaService.findByNumeroCuenta("9876543210")).thenReturn(Optional.empty());

        ResponseEntity<CuentaResponseDTO> response = cuentaController.actualizarCuenta("9876543210", request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Cuenta con número: 9876543210 no encontrada.", response.getBody().getMensaje());
    }
}
