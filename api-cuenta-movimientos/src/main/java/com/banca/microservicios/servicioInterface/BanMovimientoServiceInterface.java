package com.banca.microservicios.servicioInterface;

import com.banca.microservicios.dto.MovimientoReporteDTO;
import com.banca.microservicios.model.BanMovimientos;
import com.banca.microservicios.model.BanCuenta;
import java.util.List;

public interface BanMovimientoServiceInterface {

    /** Crear un nuevo movimiento */
    BanMovimientos crearMovimiento(BanMovimientos movimiento);

    /** Obtener todos los movimientos */
    List<BanMovimientos> findAll();

    /** Actualizar un movimiento existente */
    BanMovimientos actualizarMovimiento(BanMovimientos movimiento);

    /** Buscar movimientos por cuenta */
    List<BanMovimientos> findByCuenta(BanCuenta cuenta);

    /** Eliminar movimientos por cuenta */
    void eliminarPorCuenta(BanCuenta cuenta);

    /**
     * Obtiene los movimientos de los clientes especificados en un rango de fechas.
     *
     * @param identificaciones Lista de identificaciones de clientes
     * @param fechaInicio Fecha de inicio en formato "DD/MM/YYYY"
     * @param fechaFin Fecha de fin en formato "DD/MM/YYYY"
     * @return Lista de resultados en formato Object[] (cada uno representa una fila del query)
     */

    List<MovimientoReporteDTO> obtenerMovimientosPorClienteYFechas(String identificaciones, String fechaInicio, String fechaFin);

}