package com.example.sw2.repository;


import com.example.sw2.dto.DatosProductoVentaDto;
import com.example.sw2.entity.AsignacionTiendas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AsignacionTiendasRepository extends JpaRepository<AsignacionTiendas,Integer> {

    @Query(value="select p.nombre as nombreprodcuto, i.codigo_inventario as codigoinventario," +
            "i.tamanho as tamañoproducto, i.color as colorproducto, i.foto as fotoproducto, c.comunidad as comunidadproducto,"+
            "a.stock as stocksede, t.stock as stockasignadotienda, t.fecha_asignacion as fechaasignacionproducto\n"+
            "FROM Productos p\n" +
            "inner join Inventario i on (p.codigonom = i.producto)\n" +
            "inner join Comunidades c on (c.codigo = i.comunidad)\n"+
            "inner join Ventas v on (v.productoinventario = i.producto)\n"+
            "inner join Asignados_sedes a on (a.producto_inventario = i.codigo_inventario)"+
            "inner join Asignacion_tiendas t on (t.productoasignado = a.idasignados)",
            nativeQuery = true)
    List<DatosProductoVentaDto> obtenerDatosProducto();


}
