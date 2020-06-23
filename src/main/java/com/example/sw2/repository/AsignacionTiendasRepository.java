package com.example.sw2.repository;


import com.example.sw2.dto.DatosAsignadosTiendaDto;
import com.example.sw2.entity.AsignacionTiendas;
import com.example.sw2.entity.AsignadosSedes;
import com.example.sw2.entity.Tienda;
import com.example.sw2.entity.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface AsignacionTiendasRepository extends JpaRepository<AsignacionTiendas,Integer> {

    @Query(value="select p.nombre as nombreproducto, a.precioventa as precioventa," +
            "att.stock as stockasignadotienda, att.fecha_asignacion as fechaasignacionproducto, t.nombre as nombretienda,"+
            "i.tamanho as tamanhoproducto, i.color as colorproducto, att.idtiendas as idasignados\n"+
            "FROM Productos p\n" +
            "inner join Inventario i on (p.codigonom = i.producto)\n" +
            "inner join Asignados_sedes a on (i.codigo_inventario = a.producto_inventario)"+
            "inner join Asignacion_tiendas att on (a.idAsignados = att.productoasignado)"+
            "inner join Tienda t on (att.tienda =  t.idtienda)"+
            "group by att.tienda",
            nativeQuery = true)
    List<DatosAsignadosTiendaDto> obtenerDatosAsignados();

    @Query(value="SELECT ast.*\n" +
            "FROM Asignacion_tienda ast\n" +
            "WHERE sede=?1",nativeQuery=true)

    List<AsignacionTiendas> buscarPorSede(int sede);

    List<AsignacionTiendas> findAsignacionTiendasByStockGreaterThanAndAsignadosSedes_Id_Sede(int a, Usuarios sede);
    List<AsignacionTiendas> findAsignacionTiendasByTiendaAndAsignadosSedes(Tienda tienda,AsignadosSedes as);

    //Optional<AsignacionTiendas> findByTiendaAndAsignadosSedes_Id_Precioventa(int tienda, BigDecimal precio);

    @Procedure(name = "tienda_registra")
    void tienda_registra(int dni_gestor, int dni_sede, String codigo,
                                int estado, BigDecimal precio, int cant, int aTienda);

    @Procedure(name = "tienda_devolucion")
    void tienda_devolucion(int dni_gestor, int dni_sede, String codigo,
                      int estado, Float precio, int cant, int aTienda);


}
