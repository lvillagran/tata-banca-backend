package com.banca.microservicios.repository;

import com.banca.microservicios.model.BanMovimientos;
import com.banca.microservicios.model.BanCuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface BanMovimientoRepository extends JpaRepository<BanMovimientos, Long> {

    // Buscar todos los movimientos por la cuenta asociada
    List<BanMovimientos> findByCuenta(BanCuenta cuenta);

    // Buscar movimientos por cuenta ordenados por fecha descendente
    List<BanMovimientos> findByCuentaOrderByFechaMovimientoDesc(BanCuenta cuenta);


    /** Método para obtener los movimientos según el query proporcionado */
    @Query(value = "SELECT tm.fecha_movimiento, tp.nombre as cliente, tc.numero_cuenta, tc.tipo_cuenta, tc.saldo_inicial, tm.estado, tm.valor_movimiento as movimiento, tm.saldo as SaldoDisponible "
            + "FROM operaciones.tab_movimientos tm "
            + "JOIN operaciones.tab_cuenta tc ON tc.numero_cuenta = tm.numero_cuenta "
            + "JOIN mantenimiento.tab_cliente tp ON tp.identificacion = tc.identificacion_cliente "
            + "WHERE tp.identificacion IN (:identificaciones) "
            + "AND tm.fecha_movimiento BETWEEN TO_DATE(:fechaInicio, 'DD/MM/YYYY') AND TO_DATE(:fechaFin, 'DD/MM/YYYY')", nativeQuery = true)
    List<Object[]> findMovimientosByClienteAndFecha(
            @Param("identificaciones") String identificaciones,
            @Param("fechaInicio") String fechaInicio,
            @Param("fechaFin") String fechaFin);
}
