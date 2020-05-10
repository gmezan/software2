package com.example.sw2.repository;
import com.example.sw2.dto.DatosGestorVentasDto;
import com.example.sw2.dto.DatosProductoVentaDto;
import com.example.sw2.entity.Ventas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VentasRepository extends JpaRepository<Ventas,String> {

        @Query(value="select p.nombre as nombreproducto, i.codigo_inventario as codigoinventario," +
                "i.tamanho as tamanhoproducto, i.color as colorproducto, i.foto as fotoproducto, c.nombre as comunidadproducto,"+
                "a.stock as stocksede, t.stock as stockasignadotienda, t.fecha_asignacion as fechaasignacionproducto,"+
                "v.ruc_dni as rucdni, v.nombrecliente as nombrecliente, v.lugarventa as lugarventa, v.fecha as fechaventa,"+
                "v.cantidad as cantidadventa, v.precio_venta as precioventa, v.productoinventario as codigoproducto,"+
                "v.tipodocumento as tipodocumento, v.numerodocumento as numerodocumento\n"+
                "FROM Productos p\n" +
                "inner join Inventario i on (p.codigonom = i.producto)\n" +
                "inner join Comunidades c on (i.comunidad = c.codigo)\n"+
                "inner join Ventas v on (i.codigo_inventario = v.productoinventario)\n"+
                "inner join Asignados_sedes a on (i.codigo_inventario = a.producto_inventario)"+
                "inner join Asignacion_tiendas t on (a.idAsignados = t.productoasignado)",
            nativeQuery = true)
    List<DatosProductoVentaDto> obtenerDatosProducto();

    @Query(value="SELECT p.nombre as nombreproducto, p.codigonom as codigoproducto,\n" +
            "v.tipodocumento as tipodocumento, v.nombrecliente as nombrecliente,\n" +
            "v.ruc_dni as rucdni, v.cantidad as cantidadventa,\n" +
            "v.precio_venta as precioventa, v.fecha as fechaventa,\n" +
            "v.lugarventa as lugarventa FROM Ventas v\n" +
            "INNER JOIN Inventario i ON (v.productoinventario = i.codigo_inventario)\n" +
            "INNER JOIN Productos p ON (i.producto = p.codigonom)",
            nativeQuery = true)
    List<DatosGestorVentasDto> obtenerDatosGestorVentas();
}
