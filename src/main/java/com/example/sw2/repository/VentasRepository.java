package com.example.sw2.repository;
import com.example.sw2.constantes.VentasId;
import com.example.sw2.dto.DatosGestorVentasDto;
import com.example.sw2.dto.DatosProductoVentaDto;
import com.example.sw2.dtoReportes.ReportesClienteDto;
import com.example.sw2.dtoReportes.ReportesComunidadDto;
import com.example.sw2.dtoReportes.ReportesSedesDto;
import com.example.sw2.dtoReportes.ReportesTotalDto;
import com.example.sw2.entity.Ventas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VentasRepository extends JpaRepository<Ventas, VentasId> {

    @Query(value="select p.nombre as nombreproducto, c.nombre as comunidadproducto, i.tamanho as tamanhoproducto," +
            "i.color as colorproducto, i.foto as fotoproducto,"+
            "v.fecha as fechaventa,sum(v.cantidad) as cantidadventa,"+
            "v.precio_venta as precioventa, v.productoinventario as codigoproducto\n"+
            "FROM Ventas v\n"+
            "inner join Inventario i on (v.productoinventario = i.codigo_inventario)\n" +
            "inner join Comunidades c on (i.comunidad = c.codigo)\n"+
            "inner join Productos p on (i.producto = p.codigonom)\n"+
            "group by v.productoinventario",
            nativeQuery = true)
    List<DatosProductoVentaDto> obtenerDatosPorProducto();

    @Query(value="SELECT p.nombre as nombreproducto, p.codigonom as codigoproducto,\n" +
            "v.tipodocumento as tipodocumento , v.numerodocumento as numerodocumento, \n" +
            "v.nombrecliente as nombrecliente, v.ruc_dni as rucdni, \n" +
            "v.cantidad as cantidadventa, v.precio_venta as precioventa, \n" +
            "v.fecha as fechaventa, v.lugarventa as lugarventa FROM Ventas v \n" +
            "INNER JOIN Inventario i ON (v.productoinventario = i.codigo_inventario)\n" +
            "INNER JOIN Productos p ON (i.producto = p.codigonom)",
            nativeQuery = true)
    List<DatosGestorVentasDto> obtenerDatosGestorVentas();

    @Query(value="SELECT ven.* FROM Ventas ven WHERE vendedor = ?1",nativeQuery=true)
    List<Ventas> buscarPorGestor(int gestor);

    List<Ventas> findByVendedor_Idusuarios(int dni);


    @Query(value="SELECT * FROM mosqoy.Ventas ven WHERE YEAR(ven.fecha_creacion) = ?1",nativeQuery=true)
    List<ReportesTotalDto> obtenerReporteAnual(int anho);

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven, mosqoy.Inventario inv, mosqoy.Productos pro WHERE ven.productoinventario=inv.codigo_inventario AND inv.producto=pro.codigonom AND YEAR(ven.fecha) = ?1 AND pro.codigonom = ?2",nativeQuery=true)
    List<ReportesTotalDto> obtenerReporteAnualxProducto(int anho, String codProd);

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven, mosqoy.Inventario inv, mosqoy.Comunidades com WHERE ven.productoinventario = inv.codigo_inventario AND inv.comunidad = com.codigo AND YEAR(ven.fecha) = ?1 AND com.codigo = ?2",nativeQuery=true)
    List<ReportesTotalDto> obtenerReporteAnualxComunidad(int anho, String codCom);

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven WHERE YEAR(ven.fecha) = ?1 AND ven.nombrecliente = ?2",nativeQuery=true)
    List<ReportesTotalDto> obtenerReporteAnualxNombreCliente(int anho, String nomCliente);
    //////FIN REPORTES ANUALES

    @Query(value = "select p.nombre as nombreproducto, c.nombre as comunidadproducto, i.tamanho as tamanhoproducto, i.color as colorproducto, i.foto as fotoproducto,\n" +
            "            v.fecha as fechaventa,sum(v.cantidad) as cantidadventa, v.precio_venta as precioventa, v.productoinventario as codigoproducto\n" +
            "            FROM Ventas v inner join Inventario i on (v.productoinventario = i.codigo_inventario)\n" +
            "            inner join Comunidades c on (i.comunidad = c.codigo) inner join Productos p on (i.producto = p.codigonom) WHERE YEAR(fecha) = YEAR(CURRENT_DATE - INTERVAL 1 MONTH)\n" +
            "AND MONTH(fecha) = MONTH(CURRENT_DATE - INTERVAL 1 MONTH) group by v.productoinventario", nativeQuery = true)
    List<DatosProductoVentaDto> obtenerDatosPorProductoUltimoMes();

    @Query(value = "select p.nombre as nombreproducto, c.nombre as comunidadproducto, i.tamanho as tamanhoproducto, i.color as colorproducto, i.foto as fotoproducto,\n" +
            "            v.fecha as fechaventa,sum(v.cantidad) as cantidadventa, v.precio_venta as precioventa, v.productoinventario as codigoproducto\n" +
            "            FROM Ventas v inner join Inventario i on (v.productoinventario = i.codigo_inventario)\n" +
            "            inner join Comunidades c on (i.comunidad = c.codigo) inner join Productos p on (i.producto = p.codigonom) WHERE YEAR(fecha) = YEAR(CURRENT_DATE - INTERVAL 3 MONTH)\n" +
            "AND MONTH(fecha) <= MONTH(CURRENT_DATE - INTERVAL 1 MONTH) and month(fecha) >= month(current_date - interval 3 month) group by v.productoinventario", nativeQuery = true)
    List<DatosProductoVentaDto> obtenerDatosPorProductoUltimoTrimestre();


    /////////// ventas de una comunidad por anho especifico

    @Query(value="SELECT * FROM mosqoy.Ventas ven WHERE YEAR(ven.fecha) = ?1",nativeQuery=true)
    List<ReportesTotalDto> obtenerVentasGENERALESPorAnho(int anho);

    /////////// FIN ventas de una comunidad por anho especifico

    /////////// ventas de una comunidad por anho especifico

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven, mosqoy.Inventario inv, mosqoy.Comunidades com WHERE ven.productoinventario = inv.codigo_inventario AND inv.comunidad = ?1 AND YEAR(ven.fecha) = ?2",nativeQuery=true)
    List<ReportesTotalDto> obtenerVentasDeComunidadPorAnho(String comunidadId, int anho);

    /////////// FIN ventas de una comunidad por anho especifico

    /////////// ventas de una comunidad por TRIMESTRE especifico en un anho especifico

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven, mosqoy.Inventario inv, mosqoy.Comunidades com WHERE ven.productoinventario = inv.codigo_inventario AND inv.comunidad = ?1 AND MONTH(ven.fecha) > 0 AND MONTH(ven.fecha) < 4 AND YEAR(ven.fecha) = ?2",nativeQuery=true)
    List<ReportesTotalDto> obtenerVentasDeComunidadDelPRIMERTrimestre(String comunidadId, int anho);

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven, mosqoy.Inventario inv, mosqoy.Comunidades com WHERE ven.productoinventario = inv.codigo_inventario AND inv.comunidad = ?1 AND MONTH(ven.fecha) > 3 AND MONTH(ven.fecha) < 7 AND YEAR(ven.fecha) = ?2",nativeQuery=true)
    List<ReportesTotalDto> obtenerVentasDeComunidadDelSEGUNDOTrimestre(String comunidadId, int anho);

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven, mosqoy.Inventario inv, mosqoy.Comunidades com WHERE ven.productoinventario = inv.codigo_inventario AND inv.comunidad = ?1 AND MONTH(ven.fecha) > 6 AND MONTH(ven.fecha) < 10 AND YEAR(ven.fecha) = ?2",nativeQuery=true)
    List<ReportesTotalDto> obtenerVentasDeComunidadDelTERCERTrimestre(String comunidadId, int anho);

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven, mosqoy.Inventario inv, mosqoy.Comunidades com WHERE ven.productoinventario = inv.codigo_inventario AND inv.comunidad = ?1 AND MONTH(ven.fecha) > 9 AND MONTH(ven.fecha) < 13 AND YEAR(ven.fecha) = ?2",nativeQuery=true)
    List<ReportesTotalDto> obtenerVentasDeComunidadDelCUARTOTrimestre(String comunidadId, int anho);

    /////////// FIN ventas de una comunidad por TRIMESTRE especifico en un anho especifico

    /////////// ventas de una comunidad por MES especifico en un anho especifico

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven, mosqoy.Inventario inv WHERE ven.productoinventario = inv.codigo_inventario AND inv.comunidad = ?1 AND MONTH(ven.fecha) = 1 AND YEAR(ven.fecha) = ?2",nativeQuery=true)
    List<ReportesTotalDto> obtenerVentasDeComunidadDeENERO(String comunidadId, int anho);

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven, mosqoy.Inventario inv WHERE ven.productoinventario = inv.codigo_inventario AND inv.comunidad = ?1 AND MONTH(ven.fecha) = 2 AND YEAR(ven.fecha) = ?2",nativeQuery=true)
    List<ReportesTotalDto> obtenerVentasDeComunidadDeFEBRERO(String comunidadId, int anho);

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven, mosqoy.Inventario inv WHERE ven.productoinventario = inv.codigo_inventario AND inv.comunidad = ?1 AND MONTH(ven.fecha) = 3 AND YEAR(ven.fecha) = ?2",nativeQuery=true)
    List<ReportesTotalDto> obtenerVentasDeComunidadDeMARZO(String comunidadId, int anho);

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven, mosqoy.Inventario inv WHERE ven.productoinventario = inv.codigo_inventario AND inv.comunidad = ?1 AND MONTH(ven.fecha) = 4 AND YEAR(ven.fecha) = ?2",nativeQuery=true)
    List<ReportesTotalDto> obtenerVentasDeComunidadDeABRIL(String comunidadId, int anho);

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven, mosqoy.Inventario inv WHERE ven.productoinventario = inv.codigo_inventario AND inv.comunidad = ?1 AND MONTH(ven.fecha) = 5 AND YEAR(ven.fecha) = ?2",nativeQuery=true)
    List<ReportesTotalDto> obtenerVentasDeComunidadDeMAYO(String comunidadId, int anho);

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven, mosqoy.Inventario inv WHERE ven.productoinventario = inv.codigo_inventario AND inv.comunidad = ?1 AND MONTH(ven.fecha) = 6 AND YEAR(ven.fecha) = ?2",nativeQuery=true)
    List<ReportesTotalDto> obtenerVentasDeComunidadDeJUNIO(String comunidadId, int anho);

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven, mosqoy.Inventario inv WHERE ven.productoinventario = inv.codigo_inventario AND inv.comunidad = ?1 AND MONTH(ven.fecha) = 7 AND YEAR(ven.fecha) = ?2",nativeQuery=true)
    List<ReportesTotalDto> obtenerVentasDeComunidadDeJULIO(String comunidadId, int anho);

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven, mosqoy.Inventario inv WHERE ven.productoinventario = inv.codigo_inventario AND inv.comunidad = ?1 AND MONTH(ven.fecha) = 8 AND YEAR(ven.fecha) = ?2",nativeQuery=true)
    List<ReportesTotalDto> obtenerVentasDeComunidadDeAGOSTO(String comunidadId, int anho);

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven, mosqoy.Inventario inv WHERE ven.productoinventario = inv.codigo_inventario AND inv.comunidad = ?1 AND MONTH(ven.fecha) = 9 AND YEAR(ven.fecha) = ?2",nativeQuery=true)
    List<ReportesTotalDto> obtenerVentasDeComunidadDeSETIEMBRE(String comunidadId, int anho);

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven, mosqoy.Inventario inv WHERE ven.productoinventario = inv.codigo_inventario AND inv.comunidad = ?1 AND MONTH(ven.fecha) = 10 AND YEAR(ven.fecha) = ?2",nativeQuery=true)
    List<ReportesTotalDto> obtenerVentasDeComunidadDeOCTUBRE(String comunidadId, int anho);

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven, mosqoy.Inventario inv WHERE ven.productoinventario = inv.codigo_inventario AND inv.comunidad = ?1 AND MONTH(ven.fecha) = 11 AND YEAR(ven.fecha) = ?2",nativeQuery=true)
    List<ReportesTotalDto> obtenerVentasDeComunidadDeNOVIEMBRE(String comunidadId, int anho);

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven, mosqoy.Inventario inv WHERE ven.productoinventario = inv.codigo_inventario AND inv.comunidad = ?1 AND MONTH(ven.fecha) = 12 AND YEAR(ven.fecha) = ?2",nativeQuery=true)
    List<ReportesTotalDto> obtenerVentasDeComunidadDeDICIEMBRE(String comunidadId, int anho);

    /////////// FIN ventas de una comunidad por MES especifico en un anho especifico



























    /////////// ventas de un producto por anho especifico

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven, mosqoy.Inventario inv WHERE ven.productoinventario = inv.codigo_inventario AND inv.producto = ?1 AND YEAR(ven.fecha) = ?2",nativeQuery=true)
    List<ReportesTotalDto> obtenerVentasDeProductoPorAnho(String productoId, int anho);

    /////////// FIN ventas de un producto por anho especifico

    /////////// ventas de una producto por TRIMESTRE especifico en un anho especifico

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven, mosqoy.Inventario inv WHERE ven.productoinventario = inv.codigo_inventario AND inv.producto = ?1 AND YEAR(ven.fecha) = ?2 AND MONTH(ven.fecha) > 0 AND MONTH(ven.fecha) < 4",nativeQuery=true)
    List<ReportesTotalDto> obtenerVentasDeProductoDelPRIMERTrimestre(String productoId, int anho);

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven, mosqoy.Inventario inv WHERE ven.productoinventario = inv.codigo_inventario AND inv.producto = ?1 AND YEAR(ven.fecha) = ?2 AND MONTH(ven.fecha) > 3 AND MONTH(ven.fecha) < 7",nativeQuery=true)
    List<ReportesTotalDto> obtenerVentasDeProductoDelSEGUNDOTrimestre(String productoId, int anho);

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven, mosqoy.Inventario inv WHERE ven.productoinventario = inv.codigo_inventario AND inv.producto = ?1 AND YEAR(ven.fecha) = ?2 AND MONTH(ven.fecha) > 6 AND MONTH(ven.fecha) < 10",nativeQuery=true)
    List<ReportesTotalDto> obtenerVentasDeProductoDelTERCERTrimestre(String productoId, int anho);

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven, mosqoy.Inventario inv WHERE ven.productoinventario = inv.codigo_inventario AND inv.producto = ?1 AND YEAR(ven.fecha) = ?2 AND MONTH(ven.fecha) > 9 AND MONTH(ven.fecha) < 13",nativeQuery=true)
    List<ReportesTotalDto> obtenerVentasDeProductoDelCUARTOTrimestre(String productoId, int anho);

    /////////// FIN ventas de un producto por TRIMESTRE especifico en un anho especifico

    /////////// ventas de un producto por MES especifico en un anho especifico

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven, mosqoy.Inventario inv WHERE ven.productoinventario = inv.codigo_inventario AND inv.producto = ?1 AND YEAR(ven.fecha) = ?2 AND MONTH(ven.fecha) = 1",nativeQuery=true)
    List<ReportesTotalDto> obtenerVentasDeProductoDeENERO(String productoId, int anho);

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven, mosqoy.Inventario inv WHERE ven.productoinventario = inv.codigo_inventario AND inv.producto = ?1 AND YEAR(ven.fecha) = ?2 AND MONTH(ven.fecha) = 2",nativeQuery=true)
    List<ReportesTotalDto> obtenerVentasDeProductoDeFEBRERO(String productoId, int anho);

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven, mosqoy.Inventario inv WHERE ven.productoinventario = inv.codigo_inventario AND inv.producto = ?1 AND YEAR(ven.fecha) = ?2 AND MONTH(ven.fecha) = 3",nativeQuery=true)
    List<ReportesTotalDto> obtenerVentasDeProductoDeMARZO(String productoId, int anho);

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven, mosqoy.Inventario inv WHERE ven.productoinventario = inv.codigo_inventario AND inv.producto = ?1 AND YEAR(ven.fecha) = ?2 AND MONTH(ven.fecha) = 4",nativeQuery=true)
    List<ReportesTotalDto> obtenerVentasDeProductoDeABRIL(String productoId, int anho);

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven, mosqoy.Inventario inv WHERE ven.productoinventario = inv.codigo_inventario AND inv.producto = ?1 AND YEAR(ven.fecha) = ?2 AND MONTH(ven.fecha) = 5",nativeQuery=true)
    List<ReportesTotalDto> obtenerVentasDeProductoDeMAYO(String productoId, int anho);

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven, mosqoy.Inventario inv WHERE ven.productoinventario = inv.codigo_inventario AND inv.producto = ?1 AND YEAR(ven.fecha) = ?2 AND MONTH(ven.fecha) = 6",nativeQuery=true)
    List<ReportesTotalDto> obtenerVentasDeProductoDeJUNIO(String productoId, int anho);

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven, mosqoy.Inventario inv WHERE ven.productoinventario = inv.codigo_inventario AND inv.producto = ?1 AND YEAR(ven.fecha) = ?2 AND MONTH(ven.fecha) = 7",nativeQuery=true)
    List<ReportesTotalDto> obtenerVentasDeProductoDeJULIO(String productoId, int anho);

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven, mosqoy.Inventario inv WHERE ven.productoinventario = inv.codigo_inventario AND inv.producto = ?1 AND YEAR(ven.fecha) = ?2 AND MONTH(ven.fecha) = 8",nativeQuery=true)
    List<ReportesTotalDto> obtenerVentasDeProductoDeAGOSTO(String productoId, int anho);

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven, mosqoy.Inventario inv WHERE ven.productoinventario = inv.codigo_inventario AND inv.producto = ?1 AND YEAR(ven.fecha) = ?2 AND MONTH(ven.fecha) = 9",nativeQuery=true)
    List<ReportesTotalDto> obtenerVentasDeProductoDeSETIEMBRE(String productoId, int anho);

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven, mosqoy.Inventario inv WHERE ven.productoinventario = inv.codigo_inventario AND inv.producto = ?1 AND YEAR(ven.fecha) = ?2 AND MONTH(ven.fecha) = 10",nativeQuery=true)
    List<ReportesTotalDto> obtenerVentasDeProductoDeOCTUBRE(String productoId, int anho);

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven, mosqoy.Inventario inv WHERE ven.productoinventario = inv.codigo_inventario AND inv.producto = ?1 AND YEAR(ven.fecha) = ?2 AND MONTH(ven.fecha) = 11",nativeQuery=true)
    List<ReportesTotalDto> obtenerVentasDeProductoDeNOVIEMBRE(String productoId, int anho);

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven, mosqoy.Inventario inv WHERE ven.productoinventario = inv.codigo_inventario AND inv.producto = ?1 AND YEAR(ven.fecha) = ?2 AND MONTH(ven.fecha) = 12",nativeQuery=true)
    List<ReportesTotalDto> obtenerVentasDeProductoDeDICIEMBRE(String productoId, int anho);

    /////////// FIN ventas de un producto por MES especifico en un anho especifico






    //SEDES ALEX

    @Query(value="SELECT CONCAT(u.nombre,' ',u.apellido) as nombre, u.dni, u.correo, u.telefono, sum(v.precio_venta) as sumaventas, sum(v.cantidad) as cantidadvendidos FROM Ventas v inner join Usuarios u ON (v.vendedor = u.dni) WHERE YEAR(v.fecha) = ?1 AND u.rol = 2 group by v.vendedor",nativeQuery=true)
    List<ReportesSedesDto> obtenerReporteAnualSede(int anho);

    @Query(value="SELECT CONCAT(u.nombre,' ',u.apellido) as nombre, u.dni, u.correo, u.telefono, sum(v.precio_venta) as sumaventas, sum(v.cantidad) as cantidadvendidos FROM Ventas v inner join Usuarios u ON (v.vendedor = u.dni) WHERE QUARTER(v.fecha) = ?1 AND YEAR(v.fecha) = ?2 AND u.rol = 2 group by v.vendedor",nativeQuery=true)
    List<ReportesSedesDto> obtenerReporteTrimestralSede(int trimestre, int anho);

    @Query(value="SELECT CONCAT(u.nombre,' ',u.apellido) as nombre, u.dni, u.correo, u.telefono, sum(v.precio_venta) as sumaventas, sum(v.cantidad) as cantidadvendidos FROM Ventas v inner join Usuarios u ON (v.vendedor = u.dni) WHERE MONTH(v.fecha) = ?1 AND YEAR(v.fecha) = ?2 AND u.rol = 2 group by v.vendedor ",nativeQuery=true)
    List<ReportesSedesDto> obtenerReporteMensualSede(int mes, int anho);

    //CLIENTES

    @Query(value="SELECT v.nombrecliente as nombre , v.ruc_dni, v.productoinventario as producto, sum(v.precio_venta) as sumaventas, sum(v.cantidad) as cantidadvendidos FROM Ventas v WHERE YEAR(v.fecha) = ?2 group by v.ruc_dni",nativeQuery=true)
    List<ReportesClienteDto> obtenerReporteAnualCliente(int anho);

    @Query(value="SELECT v.nombrecliente as nombre , v.ruc_dni, v.productoinventario as producto, sum(v.precio_venta) as sumaventas, sum(v.cantidad) as cantidadvendidos FROM Ventas v WHERE QUARTER(v.fecha) = ?1 AND YEAR(v.fecha) = ?2 group by v.ruc_dni",nativeQuery=true)
    List<ReportesClienteDto> obtenerReporteTrimestralCliente(int trimestre, int anho);

    @Query(value="SELECT v.nombrecliente as nombre , v.ruc_dni, v.productoinventario as producto, sum(v.precio_venta) as sumaventas, sum(v.cantidad) as cantidadvendidos FROM Ventas v WHERE MONTH(v.fecha) = ?1 AND YEAR(v.fecha) = ?2 group by v.ruc_dni",nativeQuery=true)
    List<ReportesClienteDto> obtenerReporteMensualCliente(int mes, int anho);

    // FIN SEDES ALEX

    //COMUNIDAD

    @Query(value="",nativeQuery=true)
    List<ReportesComunidadDto> obtenerReporteAnualComunidad(int anho);

    @Query(value="SELECT CONCAT(u.nombre,' ',u.apellido) as nombre, u.dni, u.correo, u.telefono, sum(v.precio_venta) as sumaventas, sum(v.cantidad) as cantidadvendidos FROM Ventas v inner join Usuarios u ON (v.vendedor = u.dni) WHERE QUARTER(v.fecha) = ?1 AND YEAR(v.fecha) = ?2 AND u.rol = 2 group by v.vendedor",nativeQuery=true)
    List<ReportesComunidadDto> obtenerReporteTrimestralComunidad(int trimestre, int anho);

    @Query(value="SELECT CONCAT(u.nombre,' ',u.apellido) as nombre, u.dni, u.correo, u.telefono, sum(v.precio_venta) as sumaventas, sum(v.cantidad) as cantidadvendidos FROM Ventas v inner join Usuarios u ON (v.vendedor = u.dni) WHERE MONTH(v.fecha) = ?1 AND YEAR(v.fecha) = ?2 AND u.rol = 2 group by v.vendedor ",nativeQuery=true)
    List<ReportesComunidadDto> obtenerReporteMensualComunidad(int mes, int anho);





}
