package com.example.sw2.repository;
import com.example.sw2.constantes.VentasId;
import com.example.sw2.dto.DatosClientesVentaDto;
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

}
