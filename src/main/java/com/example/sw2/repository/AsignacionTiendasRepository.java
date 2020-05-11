package com.example.sw2.repository;


import com.example.sw2.dto.DatosAsignadosTiendaDto;
import com.example.sw2.dto.DatosProductoVentaDto;
import com.example.sw2.dto.ProductoAsignadoSedeVentaDto;
import com.example.sw2.dto.ProductoInventarioVentaDto;
import com.example.sw2.entity.AsignacionTiendas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AsignacionTiendasRepository extends JpaRepository<AsignacionTiendas,Integer> {

    @Query(value="select p.nombre as nombreproducto, a.precioventa as precioventa," +
            "att.stock as stockasignadotienda, att.fecha_asignacion as fechaasignacionproducto, t.nombre as nombretienda,"+
            "i.tamanho as tamanhoproducto, i.color as colorproducto\n"+
            "FROM Productos p\n" +
            "inner join Inventario i on (p.codigonom = i.producto)\n" +
            "inner join Asignados_sedes a on (i.codigo_inventario = a.producto_inventario)"+
            "inner join Asignacion_tiendas att on (a.idAsignados = att.productoasignado)"+
            "inner join Tienda t on (att.tienda =  t.idtienda)"+
            "group by att.tienda",
            nativeQuery = true)
    List<DatosAsignadosTiendaDto> obtenerDatosAsignados();

    @Query(value="select i.cantidad_total as cantidadtotal\n"+
            "FROM Ventas v\n"+
            "where v.productoinventario = ?1,"+
            "inner join Inventario i on (v.productoinventario = i.codigo_inventario)\n" +
            "group by v.productoinventario",
            nativeQuery = true)
    List<ProductoAsignadoSedeVentaDto> productoInventarioVendido(String codigo);

    @Query(value="select a.cantidadactual as cantidadactual\n"+
            "FROM Ventas v\n"+
            "where v.productoinventario = ?1,"+
            "inner join Asignados_sedes a on (v.productoinventario = a.producto_inventario)"+
            "group by v.productoinventario",
            nativeQuery = true) 
    List<ProductoInventarioVentaDto> productoAsignadoSedeVendido(String codigo);


}
