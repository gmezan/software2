package com.example.sw2.repository;


import com.example.sw2.dto.DatosAsignadosTiendaDto;
import com.example.sw2.entity.AsignacionTiendas;
import com.example.sw2.entity.AsignadosSedes;
import com.example.sw2.entity.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

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

    @Query(value="update Inventario i set i.cantidad_total =  i.cantidad_total - ?2 " +
            " where i.codigo_inventario =?1\n",
            nativeQuery = true)
    void stockInventarioActualizado(String codigo, int cantVent);

    @Query(value="update Asignados_sedes a set a.cantidadactual =  a.cantidadactual - ?2 " +
            " where a.producto_inventario =?1\n",
            nativeQuery = true)
    void stockAsignadoSedeActualizado(String codigo, int cantVent);

    @Query(value="SELECT ast.*\n" +
            "FROM Asignacion_tienda ast\n" +
            "WHERE sede=?1",nativeQuery=true)

    List<AsignacionTiendas> buscarPorSede(int sede);

    List<AsignacionTiendas> findAsignacionTiendasByAsignadosSedes_Id_Sede(Usuarios sede);

    @Transactional
    @Modifying
    @Query(value="update Inventario i set i.cantidad_gestor =  i.cantidad_gestor - ?2 " +
            " where i.codigo_inventario =?1\n",
            nativeQuery = true)
    void cantidadGestorDevueltaActualizada(String codigo, int cantDev);

}
