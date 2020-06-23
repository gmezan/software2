package com.example.sw2.repository;


import com.example.sw2.constantes.AsignadosSedesId;
import com.example.sw2.dto.DatosClientesVentaDto;
import com.example.sw2.dto.DevolucionDto;
import com.example.sw2.entity.AsignadosSedes;
import com.example.sw2.entity.Usuarios;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface AsignadosSedesRepository extends JpaRepository<AsignadosSedes, AsignadosSedesId> {

    @Query(value="SELECT ass.*\n" +
            "FROM Asignados_sedes ass\n" +
            "WHERE sede=?1",nativeQuery=true)

    List<AsignadosSedes> buscarPorSede(int sede);

    @Query(value="select p.nombre as nombreproducto, i.codigo_inventario as codigo,a.fecha_envio as fecha," +
            "a.stock as cantidad, u.nombre as nombre, u.foto as foto, u.apellido as apellido, a.precioventa as precio,"+
            "u.correo as correo, u.telefono as telefono, u.dni as dniSede, a.estadoasignacion as estado\n"+
            "FROM Asignados_sedes a\n"+
            "inner join Inventario i on (a.producto_inventario = i.codigo_inventario)\n" +
            "inner join Productos p on (i.producto = p.codigonom)\n"+
            "inner join Usuarios u on (a.sede = u.dni) "+
            "where a.estadoasignacion = ?1 and a.gestor = ?2",
            nativeQuery = true)
    List<DevolucionDto> DatosDevolucion(int estado, int dni);

    List<AsignadosSedes> findAsignadosSedesById_Sede_idusuarios(int id);

    List<AsignadosSedes> findAsignadosSedesById_Gestor_idusuarios(int id);

    @Procedure(name = "devol_sede_gestor")
    void devol_sede_gestor(int cant_devol, int gestor, int sede, String codigo, int estado, Float precio);

    @Procedure(name = "rechazar_devol_sede")
    void rechazar_devol_sede(int cant_devol, int gestor, int sede, String codigo, int estado, Float precio);

    List<AsignadosSedes> findAllByOrderByFechacreacionDesc();

    AsignadosSedes findById_Productoinventario_CodigoinventarioAndId_PrecioventaAndId_Estadoasignacion(String codigo, Float precio, int estado);

}
