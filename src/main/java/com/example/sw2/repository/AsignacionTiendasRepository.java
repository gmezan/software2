package com.example.sw2.repository;


import com.example.sw2.dto.DatosProductoVentaDto;
import com.example.sw2.entity.AsignacionTiendas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AsignacionTiendasRepository extends JpaRepository<AsignacionTiendas,Integer> {

    @Query(value="select p.nombre as nombreprodcuto, a.precioventa as precioventa," +
            "a_t.stock as stockasignadotienda, a_t.fecha_asignacion as fechaasignacionproducto, t.nombre as nombretienda\n"+
            "FROM Productos p\n" +
            "inner join Inventario i on (p.codigonom = i.producto)\n" +
            "inner join Asignados_sedes a on (i.codigo_inventario = a.producto_inventario)"+
            "inner join Asignacion_tiendas a_t on (a.idasignados = a_t.productoasignado)"+
            "inner join Tienda t on (a_t.tienda =   t.idtienda)",
            nativeQuery = true)
    List<DatosProductoVentaDto> obtenerDatosAsignados();


}
