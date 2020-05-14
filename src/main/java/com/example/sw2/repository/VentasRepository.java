package com.example.sw2.repository;
import com.example.sw2.constantes.VentasId;
import com.example.sw2.dto.DatosClientesVentaDto;
import com.example.sw2.dto.DatosGestorVentasDto;
import com.example.sw2.dto.DatosProductoVentaDto;
import com.example.sw2.entity.Ventas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VentasRepository extends JpaRepository<Ventas, VentasId> {


    @Query(value="select p.nombre as nombreproducto, v.productoinventario as codigoproducto," +
            "i.tamanho as tamanhoproducto, i.color as colorproducto, i.foto as fotoproducto, c.nombre as comunidadproducto,"+
            "v.ruc_dni as rucdni, v.nombrecliente as nombrecliente, v.lugarventa as lugarventa, v.fecha as fechaventa,"+
            "v.cantidad as cantidadventa, v.precio_venta as precioventa,"+
            "v.tipodocumento as tipodocumento, v.numerodocumento as numerodocumento\n"+
            "FROM Ventas v\n"+
            "inner join Inventario i on (v.productoinventario = i.codigo_inventario)\n" +
            "inner join Comunidades c on (i.comunidad = c.codigo)\n"+
            "inner join Productos p on (i.producto = p.codigonom)\n"+
            "group by v.nombrecliente",
            nativeQuery = true)
    List<DatosClientesVentaDto> obtenerDatosPorCliente();

    @Query(value="select p.nombre as nombreproducto, c.nombre as comunidadproducto, i.tamanho as tamanhoproducto," +
            "i.color as colorproducto, i.foto as fotoproducto,"+
            "v.fecha as fechaventa,v.cantidad as cantidadventa,"+
            "v.precio_venta as precioventa, v.productoinventario as codigoproducto\n"+
            "FROM Ventas v\n"+
            "inner join Inventario i on (v.productoinventario = i.codigo_inventario)\n" +
            "inner join Comunidades c on (i.comunidad = c.codigo)\n"+
            "inner join Productos p on (i.producto = p.codigonom)\n"+
            "group by v.productoinventario",
            nativeQuery = true)

    List<DatosProductoVentaDto> obtenerDatosPorProducto();

    @Query(value="SELECT p.nombre as nombreproducto, p.codigonom as codigoproducto,\n" +
            "v.numerodocumento as numerodocumento, v.nombrecliente as nombrecliente,\n" +
            "v.ruc_dni as rucdni, v.cantidad as cantidadventa,\n" +
            "v.precio_venta as precioventa, v.fecha as fechaventa,\n" +
            "v.lugarventa as lugarventa FROM Ventas v\n" +
            "INNER JOIN Inventario i ON (v.productoinventario = i.codigo_inventario)\n" +
            "INNER JOIN Productos p ON (i.producto = p.codigonom)",
            nativeQuery = true)
    List<DatosGestorVentasDto> obtenerDatosGestorVentas();

}
