package com.example.sw2.repository;

import com.example.sw2.constantes.VentasId;
import com.example.sw2.dtoReportes.AnhosDtos;
import com.example.sw2.dtoReportes.ReporteVenta;
import com.example.sw2.dtoReportes.SedesDtos;
import com.example.sw2.entity.Ventas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VentasRepositoryXxSedesNClientes extends JpaRepository<Ventas, VentasId> {


    //SEDES
    @Query(value="SELECT DISTINCT(sede) FROM (SELECT u.dni AS \"sede\" FROM mosqoy.Usuarios u where u.rol = 2) sub",nativeQuery=true)
    List<SedesDtos> obtenerSedes();

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven WHERE YEAR(ven.fecha) = ?1 AND ven.vendedor = ?2",nativeQuery=true)
    List<ReporteVenta> obtenerReporteAnualSede(int anho, int sede);

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven WHERE QUARTER(ven.fecha) = ?1 AND YEAR(ven.fecha) = ?2 AND ven.vendedor = ?3 ",nativeQuery=true)
    List<ReporteVenta> obtenerReporteTrimestralSede(int trimestre,int anho, int sede);

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven WHERE MONTH(ven.fecha) = ?1 AND YEAR(ven.fecha) = ?2 AND ven.vendedor = ?3 ",nativeQuery=true)
    List<ReporteVenta> obtenerReporteMensualSede(int mes,int anho, int sede);

    //CLIENTES
    @Query(value="SELECT DISTINCT(cliente) FROM (SELECT v.ruc_dni AS \"cliente\" FROM mosqoy.Ventas v) sub",nativeQuery=true)
    List<SedesDtos> obtenerClientes();

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven WHERE YEAR(ven.fecha) = ?1 AND ven.ruc_dni = ?2",nativeQuery=true)
    List<ReporteVenta> obtenerReporteAnualCliente(int anho, int cliente);

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven WHERE QUARTER(ven.fecha) = ?1 AND YEAR(ven.fecha) = ?2 AND ven.ruc_dni = ?3 ",nativeQuery=true)
    List<ReporteVenta> obtenerReporteTrimestralCliente(int trimestre,int anho, int cliente);

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven WHERE MONTH(ven.fecha) = ?1 AND YEAR(ven.fecha) = ?2 AND ven.ruc_dni = ?3 ",nativeQuery=true)
    List<ReporteVenta> obtenerReporteMensualCliente(int mes,int anho, int cliente);
}
