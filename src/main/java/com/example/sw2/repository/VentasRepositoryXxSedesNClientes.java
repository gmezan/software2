package com.example.sw2.repository;

import com.example.sw2.constantes.VentasId;
import com.example.sw2.dtoReportes.ReportesTotalDto;
import com.example.sw2.dtoReportes.ReportesSedesDto;
import com.example.sw2.entity.Ventas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VentasRepositoryXxSedesNClientes extends JpaRepository<Ventas, VentasId> {


    //SEDES ALEX
    @Query(value="select distinct v.vendedor from mosqoy.Ventas v inner join mosqoy.Usuarios u on (v.vendedor = u.dni) where u.rol=2;",nativeQuery=true)
    List<ReportesSedesDto> obtenerSedes();

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven WHERE YEAR(ven.fecha) = ?1 AND ven.vendedor = ?2",nativeQuery=true)
    List<ReportesTotalDto> obtenerReporteAnualSede(int anho, int sede);

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven WHERE QUARTER(ven.fecha) = ?1 AND YEAR(ven.fecha) = ?2 AND ven.vendedor = ?3 ",nativeQuery=true)
    List<ReportesTotalDto> obtenerReporteTrimestralSede(int trimestre, int anho, int sede);

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven WHERE MONTH(ven.fecha) = ?1 AND YEAR(ven.fecha) = ?2 AND ven.vendedor = ?3 ",nativeQuery=true)
    List<ReportesTotalDto> obtenerReporteMensualSede(int mes, int anho, int sede);

    //CLIENTES
    @Query(value="SELECT DISTINCT(cliente) FROM (SELECT v.ruc_dni AS \"cliente\" FROM mosqoy.Ventas v) sub",nativeQuery=true)
    List<ReportesSedesDto> obtenerClientes();

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven WHERE YEAR(ven.fecha) = ?1 AND ven.ruc_dni = ?2",nativeQuery=true)
    List<ReportesTotalDto> obtenerReporteAnualCliente(int anho, int cliente);

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven WHERE QUARTER(ven.fecha) = ?1 AND YEAR(ven.fecha) = ?2 AND ven.ruc_dni = ?3 ",nativeQuery=true)
    List<ReportesTotalDto> obtenerReporteTrimestralCliente(int trimestre, int anho, int cliente);

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven WHERE MONTH(ven.fecha) = ?1 AND YEAR(ven.fecha) = ?2 AND ven.ruc_dni = ?3 ",nativeQuery=true)
    List<ReportesTotalDto> obtenerReporteMensualCliente(int mes, int anho, int cliente);

    // FIN SEDES ALEX
}
