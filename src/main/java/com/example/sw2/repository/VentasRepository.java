package com.example.sw2.repository;
import com.example.sw2.dto.DatosProductoVentaDto;
import com.example.sw2.entity.Ventas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VentasRepository extends JpaRepository<Ventas,String> {

    @Query(value="select p.nombre as nombreprodcuto, i.codigo_inventario as codigoinventario," +
            "i.tamanho as tamañoproducto, i.color as colorproducto, i.foto as fotoproducto, c.nombre as comunidadproducto,"+
            "a.stock as stocksede, t.stock as stockasignadotienda, t.fecha_asignacion as fechaasignacionproducto\n"+
            "FROM Productos p\n" +
            "inner join Inventario i on (p.codigonom = i.producto)\n" +
            "inner join Comunidades c on (i.comunidad = c.codigo)\n"+
            "inner join Ventas v on (i.producto = v.productoinventario)\n"+
            "inner join Asignados_sedes a on (i.codigo_inventario = a.producto_inventario)"+
            "inner join Asignacion_tiendas t on (a.idasignados = t.productoasignado)",
            nativeQuery = true)
    List<DatosProductoVentaDto> obtenerDatosProducto();
}
