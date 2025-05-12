package com.banca.microservicios.Controller;

import com.banca.microservicios.dto.ClienteRequestDTO;
import com.banca.microservicios.dto.ClienteResponseDTO;
import com.banca.microservicios.exceptions.GenericException;
import com.banca.microservicios.model.BanCliente;
import com.banca.microservicios.serviceImpl.BanClienteServiceImpl;
import com.banca.microservicios.util.getServerIPAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.web.bind.annotation.*;

import static com.banca.microservicios.util.MensajesSistema.*;

@RestController
@RequestMapping("/api/v1/mantenimiento/clientes")
public class ClienteController {

    @Autowired
    private BanClienteServiceImpl banClienteService;

    private static final Logger log = LoggerFactory.getLogger(ClienteController.class);


    /** Registrar cliente */
    @PostMapping("/crear")
    public ResponseEntity<ClienteResponseDTO> createCliente(@RequestBody ClienteRequestDTO request) {
        ClienteResponseDTO clienteResponseDTO = new ClienteResponseDTO();
        BanCliente cliente = new BanCliente();

        try {
            if (request.getIdentificacion().trim().isEmpty()) {
                clienteResponseDTO.setMensaje("La identificación es requerida.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(clienteResponseDTO);
            }

            Optional<BanCliente> clienteExistente = banClienteService.findByIdentificacion(request.getIdentificacion().trim());

            if (clienteExistente.isPresent()) {
                clienteResponseDTO.setFechaEjecucion(new Date());
                clienteResponseDTO.setMensaje(CLIENTE_EXISTE);
                return ResponseEntity.status(HttpStatus.CONFLICT).body(clienteResponseDTO); // 409 Conflict
            }else{
            cliente.setIdentificacion(request.getIdentificacion().trim());
            cliente.setNombre(request.getNombre().trim().toUpperCase());
            cliente.setGenero(request.getGenero().trim().toUpperCase());
            cliente.setEdad(request.getEdad().trim());
            cliente.setUsuario(request.getIdentificacion().trim());
            cliente.setDireccion(request.getDireccion().trim().toUpperCase());
            cliente.setTelefono(request.getTelefono().trim());
            cliente.setContrasena(generarContrasena());
            cliente.setEstado(true);
            cliente.setObservacion(REGISTRO_CLIENTE);
            cliente.setIp(getServerIPAddress.getServerIPAddress());
            cliente.setFechaRegistro(new Date());

            BanCliente clienteCreado = banClienteService.crearCliente(cliente);

            /** Validamos si el cliente fue creado correctamente */
            if (clienteCreado != null && clienteCreado.getIdentificacion() != null) {
                clienteResponseDTO.setMensaje(TRANSACCION_EXITOSA);
                clienteResponseDTO.setFechaEjecucion(new Date());
                clienteResponseDTO.setCliente(clienteCreado); //
                return ResponseEntity.status(HttpStatus.CREATED).body(clienteResponseDTO);
            } else {
                clienteResponseDTO.setMensaje("Error al registrar el cliente");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(clienteResponseDTO);
              }
            }

        } catch (GenericException e) {
            clienteResponseDTO.setMensaje("Error al registrar el cliente: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(clienteResponseDTO);
        } catch (Exception e) {
            // Manejo de errores inesperados
            clienteResponseDTO.setMensaje("Error desconocido al registrar el cliente.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(clienteResponseDTO);
        }
    }

    /** Listar clientes registrados */
    @GetMapping("/listar")
    public ResponseEntity<List<BanCliente>> listarClientes() {
        return Optional.ofNullable(banClienteService.findAll())
                .filter(list -> !list.isEmpty())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    /** Eliminar cliente por numero de identificacion */
    @DeleteMapping("/eliminar/{identificacion}")
    public ResponseEntity<ClienteResponseDTO> eliminarCliente(@PathVariable String identificacion) {
        ClienteResponseDTO response = new ClienteResponseDTO();

        return banClienteService.findByIdentificacion(identificacion)
                .map(cliente -> {
                    banClienteService.eliminarClientePorIdentificacion(identificacion);
                    response.setMensaje("Cliente eliminado correctamente.");
                    response.setFechaEjecucion(new Date());
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {
                    response.setMensaje("Cliente con Identificacion: " + identificacion + " no encontrado");
                    response.setFechaEjecucion(new Date());
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                });
    }


    /** Actualizar cliente por identificación */
    @PutMapping("/actualizar/{identificacion}")
    public ResponseEntity<ClienteResponseDTO> actualizarCliente(@PathVariable String identificacion, @RequestBody ClienteRequestDTO request) {

        ClienteResponseDTO clienteResponseDTO = new ClienteResponseDTO();

        try {
            Optional<BanCliente> clienteOptional = banClienteService.findByIdentificacion(identificacion.trim());

            if (clienteOptional.isEmpty()) {
                clienteResponseDTO.setMensaje("Cliente con Identificacion: " + identificacion + " no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(clienteResponseDTO);
            }
            BanCliente cliente = clienteOptional.get();

            cliente.setNombre(request.getNombre().trim().toUpperCase());
            cliente.setGenero(request.getGenero().trim().toUpperCase());
            cliente.setEdad(request.getEdad().trim());
            cliente.setDireccion(request.getDireccion().trim().toUpperCase());
            cliente.setTelefono(request.getTelefono().trim());
            cliente.setContrasena(request.getContrasena().trim());
            cliente.setObservacion("ACTUALIZACIÓN DE DATOS");
            cliente.setFechaActualizacion(new Date());
            cliente.setIp(getServerIPAddress.getServerIPAddress());

            BanCliente clienteActualizado = banClienteService.actualizarCliente(cliente);

            clienteResponseDTO.setMensaje("Cliente actualizado exitosamente");
            clienteResponseDTO.setFechaEjecucion(new Date());
            clienteResponseDTO.setCliente(clienteActualizado);

            return ResponseEntity.ok(clienteResponseDTO);

        } catch (Exception e) {
            clienteResponseDTO.setMensaje("Error al actualizar el cliente: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(clienteResponseDTO);
        }
    }


    //** Endpoint para consultar una persona por su identificacion Cliente Feign */
    @GetMapping("/consultarPersonaPorIdentificacion/{identificacion}")
    public ResponseEntity<BanCliente> consultarPersonaPorIdentificacion(@PathVariable String identificacion) {
        Optional<BanCliente> personaOpt = banClienteService.findByIdentificacion(identificacion);

        if (personaOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(personaOpt.get());
    }


        public static String generarContrasena() {
            Random random = new Random();
            StringBuilder numeroCuenta = new StringBuilder();

            for (int i = 0; i < 5; i++) {
                numeroCuenta.append(random.nextInt(10)); // 0 al 9
            }

            return numeroCuenta.toString();
        }
}