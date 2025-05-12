package com.banca.microservicios.serviceImpl;

import com.banca.microservicios.dto.MovimientoReporteDTO;
import com.banca.microservicios.model.BanCuenta;
import com.banca.microservicios.model.BanMovimientos;
import com.banca.microservicios.repository.BanMovimientoRepository;
import com.banca.microservicios.servicioInterface.BanMovimientoServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BanMovimientoServiceImpl implements BanMovimientoServiceInterface {

    @Autowired
    private BanMovimientoRepository banMovimientosRepository;

    @Override
    public BanMovimientos crearMovimiento(BanMovimientos movimiento) {
        return banMovimientosRepository.save(movimiento);
    }

    @Override
    public List<BanMovimientos> findAll() {
        return banMovimientosRepository.findAll();
    }

    @Override
    public BanMovimientos actualizarMovimiento(BanMovimientos movimiento) {
        return banMovimientosRepository.save(movimiento);
    }

    @Override
    public List<BanMovimientos> findByCuenta(BanCuenta cuenta) {
        return banMovimientosRepository.findByCuenta(cuenta);
    }

    @Override
    public void eliminarPorCuenta(BanCuenta cuenta) {
        List<BanMovimientos> movimientos = banMovimientosRepository.findByCuenta(cuenta);
        if (!movimientos.isEmpty()) {
            banMovimientosRepository.deleteAll(movimientos);
        }
    }



    public List<MovimientoReporteDTO> obtenerMovimientosPorClienteYFechas(String identificaciones, String fechaInicio, String fechaFin) {
        List<Object[]> resultados = banMovimientosRepository.findMovimientosByClienteAndFecha(identificaciones, fechaInicio, fechaFin);

        return resultados.stream().map(obj -> {
            MovimientoReporteDTO dto = new MovimientoReporteDTO();

            // Mapeo con comprobaciones de nulidad
            dto.setFecha(obj[0] != null ? ((java.sql.Timestamp) obj[0]).toLocalDateTime() : null);  // Asegúrate de que la fecha sea correctamente convertida
            dto.setCliente(obj[1] != null ? obj[1].toString() : null);
            dto.setNumeroCuenta(obj[2] != null ? obj[2].toString() : null);
            dto.setTipo(obj[3] != null ? obj[3].toString() : null);
            dto.setSaldoInicial(obj[4] != null ? ((Number) obj[4]).doubleValue() : null);
            dto.setEstado(obj[5] != null ? (Boolean) obj[5] : null);
            dto.setMovimiento(obj[6] != null ? ((Number) obj[6]).doubleValue() : null);
            dto.setSaldoDisponible(obj[7] != null ? ((Number) obj[7]).doubleValue() : null);

            return dto;
        }).toList();
    }

}
