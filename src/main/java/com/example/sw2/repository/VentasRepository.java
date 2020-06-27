package com.example.sw2.repository;
import com.example.sw2.constantes.VentasId;
import com.example.sw2.dto.DatosClientesVentaDto;
import com.example.sw2.dto.DatosGestorVentasDto;
import com.example.sw2.dto.DatosProductoVentaDto;
import com.example.sw2.dtoReportes.AnhosDtos;
import com.example.sw2.dtoReportes.Anual2020Dto;
import com.example.sw2.dtoReportes.ReporteVenta;
import com.example.sw2.entity.Tienda;
import com.example.sw2.entity.Usuarios;
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


    /////REPORTES DEL 2020
    @Query(value = "SELECT sub.ruc_dni , sub.nombrecliente , sub.lugarventa , sub.productoinventario FROM (SELECT ven.*,YEAR(ven.fecha_creacion) AS \"anho\" FROM mosqoy.Ventas ven) sub WHERE sub.anho = 2020", nativeQuery = true)
    List<Anual2020Dto> anual2020();

    /////REPORTES ANUALES
    @Query(value="SELECT DISTINCT(anho) FROM (SELECT YEAR(ven.fecha) AS \"anho\" FROM mosqoy.Ventas ven) sub",nativeQuery=true)
    List<AnhosDtos> obteneranhos();

    @Query(value="SELECT * FROM mosqoy.Ventas ven WHERE YEAR(ven.fecha_creacion) = ?1",nativeQuery=true)
    List<ReporteVenta> obtenerReporteAnual(int anho);

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven, mosqoy.Inventario inv, mosqoy.Productos pro WHERE ven.productoinventario=inv.codigo_inventario AND inv.producto=pro.codigonom AND YEAR(ven.fecha) = ?1 AND pro.codigonom = ?2",nativeQuery=true)
    List<ReporteVenta> obtenerReporteAnualxProducto(int anho, String codProd);

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven, mosqoy.Inventario inv, mosqoy.Comunidades com WHERE ven.productoinventario = inv.codigo_inventario AND inv.comunidad = com.codigo AND YEAR(ven.fecha) = ?1 AND com.codigo = ?2",nativeQuery=true)
    List<ReporteVenta> obtenerReporteAnualxComunidad(int anho, String codCom);

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven WHERE YEAR(ven.fecha) = ?1 AND ven.nombrecliente = ?2",nativeQuery=true)
    List<ReporteVenta> obtenerReporteAnualxNombreCliente(int anho, String nomCliente);
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
    List<ReporteVenta> obtenerVentasGENERALESPorAnho(int anho);

    /////////// FIN ventas de una comunidad por anho especifico

    /////////// ventas de una comunidad por anho especifico

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven, mosqoy.Inventario inv, mosqoy.Comunidades com WHERE ven.productoinventario = inv.codigo_inventario AND inv.comunidad = ?1 AND YEAR(ven.fecha) = ?2",nativeQuery=true)
    List<ReporteVenta> obtenerVentasDeComunidadPorAnho(String comunidadId, int anho);

    /////////// FIN ventas de una comunidad por anho especifico

    /////////// ventas de una comunidad por TRIMESTRE especifico en un anho especifico

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven, mosqoy.Inventario inv, mosqoy.Comunidades com WHERE ven.productoinventario = inv.codigo_inventario AND inv.comunidad = ?1 AND MONTH(ven.fecha) > 0 AND MONTH(ven.fecha) < 4 AND YEAR(ven.fecha) = ?2",nativeQuery=true)
    List<ReporteVenta> obtenerVentasDeComunidadDelPRIMERTrimestre(String comunidadId, int anho);

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven, mosqoy.Inventario inv, mosqoy.Comunidades com WHERE ven.productoinventario = inv.codigo_inventario AND inv.comunidad = ?1 AND MONTH(ven.fecha) > 3 AND MONTH(ven.fecha) < 7 AND YEAR(ven.fecha) = ?2",nativeQuery=true)
    List<ReporteVenta> obtenerVentasDeComunidadDelSEGUNDOTrimestre(String comunidadId, int anho);

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven, mosqoy.Inventario inv, mosqoy.Comunidades com WHERE ven.productoinventario = inv.codigo_inventario AND inv.comunidad = ?1 AND MONTH(ven.fecha) > 6 AND MONTH(ven.fecha) < 10 AND YEAR(ven.fecha) = ?2",nativeQuery=true)
    List<ReporteVenta> obtenerVentasDeComunidadDelTERCERTrimestre(String comunidadId, int anho);

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven, mosqoy.Inventario inv, mosqoy.Comunidades com WHERE ven.productoinventario = inv.codigo_inventario AND inv.comunidad = ?1 AND MONTH(ven.fecha) > 9 AND MONTH(ven.fecha) < 13 AND YEAR(ven.fecha) = ?2",nativeQuery=true)
    List<ReporteVenta> obtenerVentasDeComunidadDelCUARTOTrimestre(String comunidadId, int anho);

    /////////// FIN ventas de una comunidad por TRIMESTRE especifico en un anho especifico

    /////////// ventas de una comunidad por MES especifico en un anho especifico

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven, mosqoy.Inventario inv WHERE ven.productoinventario = inv.codigo_inventario AND inv.comunidad = ?1 AND MONTH(ven.fecha) = 1 AND YEAR(ven.fecha) = ?2",nativeQuery=true)
    List<ReporteVenta> obtenerVentasDeComunidadDeENERO(String comunidadId, int anho);

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven, mosqoy.Inventario inv WHERE ven.productoinventario = inv.codigo_inventario AND inv.comunidad = ?1 AND MONTH(ven.fecha) = 2 AND YEAR(ven.fecha) = ?2",nativeQuery=true)
    List<ReporteVenta> obtenerVentasDeComunidadDeFEBRERO(String comunidadId, int anho);

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven, mosqoy.Inventario inv WHERE ven.productoinventario = inv.codigo_inventario AND inv.comunidad = ?1 AND MONTH(ven.fecha) = 3 AND YEAR(ven.fecha) = ?2",nativeQuery=true)
    List<ReporteVenta> obtenerVentasDeComunidadDeMARZO(String comunidadId, int anho);

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven, mosqoy.Inventario inv WHERE ven.productoinventario = inv.codigo_inventario AND inv.comunidad = ?1 AND MONTH(ven.fecha) = 4 AND YEAR(ven.fecha) = ?2",nativeQuery=true)
    List<ReporteVenta> obtenerVentasDeComunidadDeABRIL(String comunidadId, int anho);

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven, mosqoy.Inventario inv WHERE ven.productoinventario = inv.codigo_inventario AND inv.comunidad = ?1 AND MONTH(ven.fecha) = 5 AND YEAR(ven.fecha) = ?2",nativeQuery=true)
    List<ReporteVenta> obtenerVentasDeComunidadDeMAYO(String comunidadId, int anho);

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven, mosqoy.Inventario inv WHERE ven.productoinventario = inv.codigo_inventario AND inv.comunidad = ?1 AND MONTH(ven.fecha) = 6 AND YEAR(ven.fecha) = ?2",nativeQuery=true)
    List<ReporteVenta> obtenerVentasDeComunidadDeJUNIO(String comunidadId, int anho);

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven, mosqoy.Inventario inv WHERE ven.productoinventario = inv.codigo_inventario AND inv.comunidad = ?1 AND MONTH(ven.fecha) = 7 AND YEAR(ven.fecha) = ?2",nativeQuery=true)
    List<ReporteVenta> obtenerVentasDeComunidadDeJULIO(String comunidadId, int anho);

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven, mosqoy.Inventario inv WHERE ven.productoinventario = inv.codigo_inventario AND inv.comunidad = ?1 AND MONTH(ven.fecha) = 8 AND YEAR(ven.fecha) = ?2",nativeQuery=true)
    List<ReporteVenta> obtenerVentasDeComunidadDeAGOSTO(String comunidadId, int anho);

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven, mosqoy.Inventario inv WHERE ven.productoinventario = inv.codigo_inventario AND inv.comunidad = ?1 AND MONTH(ven.fecha) = 9 AND YEAR(ven.fecha) = ?2",nativeQuery=true)
    List<ReporteVenta> obtenerVentasDeComunidadDeSETIEMBRE(String comunidadId, int anho);

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven, mosqoy.Inventario inv WHERE ven.productoinventario = inv.codigo_inventario AND inv.comunidad = ?1 AND MONTH(ven.fecha) = 10 AND YEAR(ven.fecha) = ?2",nativeQuery=true)
    List<ReporteVenta> obtenerVentasDeComunidadDeOCTUBRE(String comunidadId, int anho);

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven, mosqoy.Inventario inv WHERE ven.productoinventario = inv.codigo_inventario AND inv.comunidad = ?1 AND MONTH(ven.fecha) = 11 AND YEAR(ven.fecha) = ?2",nativeQuery=true)
    List<ReporteVenta> obtenerVentasDeComunidadDeNOVIEMBRE(String comunidadId, int anho);

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven, mosqoy.Inventario inv WHERE ven.productoinventario = inv.codigo_inventario AND inv.comunidad = ?1 AND MONTH(ven.fecha) = 12 AND YEAR(ven.fecha) = ?2",nativeQuery=true)
    List<ReporteVenta> obtenerVentasDeComunidadDeDICIEMBRE(String comunidadId, int anho);

    /////////// FIN ventas de una comunidad por MES especifico en un anho especifico



























    /////////// ventas de un producto por anho especifico

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven, mosqoy.Inventario inv WHERE ven.productoinventario = inv.codigo_inventario AND inv.producto = ?1 AND YEAR(ven.fecha) = ?2",nativeQuery=true)
    List<ReporteVenta> obtenerVentasDeProductoPorAnho(String productoId, int anho);

    /////////// FIN ventas de un producto por anho especifico

    /////////// ventas de una producto por TRIMESTRE especifico en un anho especifico

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven, mosqoy.Inventario inv WHERE ven.productoinventario = inv.codigo_inventario AND inv.producto = ?1 AND YEAR(ven.fecha) = ?2 AND MONTH(ven.fecha) > 0 AND MONTH(ven.fecha) < 4",nativeQuery=true)
    List<ReporteVenta> obtenerVentasDeProductoDelPRIMERTrimestre(String productoId, int anho);

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven, mosqoy.Inventario inv WHERE ven.productoinventario = inv.codigo_inventario AND inv.producto = ?1 AND YEAR(ven.fecha) = ?2 AND MONTH(ven.fecha) > 3 AND MONTH(ven.fecha) < 7",nativeQuery=true)
    List<ReporteVenta> obtenerVentasDeProductoDelSEGUNDOTrimestre(String productoId, int anho);

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven, mosqoy.Inventario inv WHERE ven.productoinventario = inv.codigo_inventario AND inv.producto = ?1 AND YEAR(ven.fecha) = ?2 AND MONTH(ven.fecha) > 6 AND MONTH(ven.fecha) < 10",nativeQuery=true)
    List<ReporteVenta> obtenerVentasDeProductoDelTERCERTrimestre(String productoId, int anho);

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven, mosqoy.Inventario inv WHERE ven.productoinventario = inv.codigo_inventario AND inv.producto = ?1 AND YEAR(ven.fecha) = ?2 AND MONTH(ven.fecha) > 9 AND MONTH(ven.fecha) < 13",nativeQuery=true)
    List<ReporteVenta> obtenerVentasDeProductoDelCUARTOTrimestre(String productoId, int anho);

    /////////// FIN ventas de un producto por TRIMESTRE especifico en un anho especifico

    /////////// ventas de un producto por MES especifico en un anho especifico

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven, mosqoy.Inventario inv WHERE ven.productoinventario = inv.codigo_inventario AND inv.producto = ?1 AND YEAR(ven.fecha) = ?2 AND MONTH(ven.fecha) = 1",nativeQuery=true)
    List<ReporteVenta> obtenerVentasDeProductoDeENERO(String productoId, int anho);

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven, mosqoy.Inventario inv WHERE ven.productoinventario = inv.codigo_inventario AND inv.producto = ?1 AND YEAR(ven.fecha) = ?2 AND MONTH(ven.fecha) = 2",nativeQuery=true)
    List<ReporteVenta> obtenerVentasDeProductoDeFEBRERO(String productoId, int anho);

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven, mosqoy.Inventario inv WHERE ven.productoinventario = inv.codigo_inventario AND inv.producto = ?1 AND YEAR(ven.fecha) = ?2 AND MONTH(ven.fecha) = 3",nativeQuery=true)
    List<ReporteVenta> obtenerVentasDeProductoDeMARZO(String productoId, int anho);

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven, mosqoy.Inventario inv WHERE ven.productoinventario = inv.codigo_inventario AND inv.producto = ?1 AND YEAR(ven.fecha) = ?2 AND MONTH(ven.fecha) = 4",nativeQuery=true)
    List<ReporteVenta> obtenerVentasDeProductoDeABRIL(String productoId, int anho);

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven, mosqoy.Inventario inv WHERE ven.productoinventario = inv.codigo_inventario AND inv.producto = ?1 AND YEAR(ven.fecha) = ?2 AND MONTH(ven.fecha) = 5",nativeQuery=true)
    List<ReporteVenta> obtenerVentasDeProductoDeMAYO(String productoId, int anho);

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven, mosqoy.Inventario inv WHERE ven.productoinventario = inv.codigo_inventario AND inv.producto = ?1 AND YEAR(ven.fecha) = ?2 AND MONTH(ven.fecha) = 6",nativeQuery=true)
    List<ReporteVenta> obtenerVentasDeProductoDeJUNIO(String productoId, int anho);

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven, mosqoy.Inventario inv WHERE ven.productoinventario = inv.codigo_inventario AND inv.producto = ?1 AND YEAR(ven.fecha) = ?2 AND MONTH(ven.fecha) = 7",nativeQuery=true)
    List<ReporteVenta> obtenerVentasDeProductoDeJULIO(String productoId, int anho);

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven, mosqoy.Inventario inv WHERE ven.productoinventario = inv.codigo_inventario AND inv.producto = ?1 AND YEAR(ven.fecha) = ?2 AND MONTH(ven.fecha) = 8",nativeQuery=true)
    List<ReporteVenta> obtenerVentasDeProductoDeAGOSTO(String productoId, int anho);

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven, mosqoy.Inventario inv WHERE ven.productoinventario = inv.codigo_inventario AND inv.producto = ?1 AND YEAR(ven.fecha) = ?2 AND MONTH(ven.fecha) = 9",nativeQuery=true)
    List<ReporteVenta> obtenerVentasDeProductoDeSETIEMBRE(String productoId, int anho);

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven, mosqoy.Inventario inv WHERE ven.productoinventario = inv.codigo_inventario AND inv.producto = ?1 AND YEAR(ven.fecha) = ?2 AND MONTH(ven.fecha) = 10",nativeQuery=true)
    List<ReporteVenta> obtenerVentasDeProductoDeOCTUBRE(String productoId, int anho);

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven, mosqoy.Inventario inv WHERE ven.productoinventario = inv.codigo_inventario AND inv.producto = ?1 AND YEAR(ven.fecha) = ?2 AND MONTH(ven.fecha) = 11",nativeQuery=true)
    List<ReporteVenta> obtenerVentasDeProductoDeNOVIEMBRE(String productoId, int anho);

    @Query(value="SELECT ven.* FROM mosqoy.Ventas ven, mosqoy.Inventario inv WHERE ven.productoinventario = inv.codigo_inventario AND inv.producto = ?1 AND YEAR(ven.fecha) = ?2 AND MONTH(ven.fecha) = 12",nativeQuery=true)
    List<ReporteVenta> obtenerVentasDeProductoDeDICIEMBRE(String productoId, int anho);

    /////////// FIN ventas de un producto por MES especifico en un anho especifico







}
