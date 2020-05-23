package com.example.sw2.repository;


import com.example.sw2.constantes.AsignadosSedesId;
import com.example.sw2.dto.DatosClientesVentaDto;
import com.example.sw2.dto.DevolucionDto;
import com.example.sw2.entity.AsignadosSedes;
import com.example.sw2.entity.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AsignadosSedesRepository extends JpaRepository<AsignadosSedes, AsignadosSedesId> {

    @Query(value="SELECT ass.*\n" +
            "FROM Asignados_sedes ass\n" +
            "WHERE sede=?1",nativeQuery=true)

    List<AsignadosSedes> buscarPorSede(int sede);

    @Query(value="select p.nombre as nombreproducto," +
            "a.cantidadactual as cantidad, u.nombre as nombre, u.foto as foto, u.apellido as apellido,"+
            "u.correo as correo, u.telefono as telefono\n"+
            "FROM Asignados_sedes a\n"+
            "inner join Inventario i on (a.producto_inventario = i.codigo_inventario)\n" +
            "inner join Productos p on (i.producto = p.codigonom)\n"+
            "inner join Usuarios u on (a.sede = u.dni) "+
            "where a.estadoasignacion = ?1",
            nativeQuery = true)
    List<DevolucionDto> DatosDevolucion(int estado);

}
