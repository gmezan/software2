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
            "i.tamanho as tamañoproducto, i.color as colorproducto, c.comunidad as comunidadproducto \n"+
            "FROM Productos p\n" +
            "inner join inventario i on (p.codigonom = i.producto)\n" +
            "inner join comunidad c on (c.codigo = i.comunidad)\n"+
            "inner join ventas v on (v.productoinventario = i.producto)\n",
            nativeQuery = true)
    List<DatosProductoVentaDto> obtenerNombreProducto();


    @Query(value="select r.regionDescription as regiondescription, count(e.employeeid) as cantidadempleados\n" +
            "FROM region r\n" +
            "inner join territories t on (r.regionid = t.regionid)\n" +
            "inner join employeeterritories et on (t.territoryid = et.territoryid)\n" +
            "inner join employees e on (e.employeeid = et.employeeid)\n" +
            "group by r.regionid\n", nativeQuery = true)
    List<EmpleadoRegionDto> obtenerEmpleadoPorRegion();

}
