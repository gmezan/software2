package com.example.sw2.repository;


import com.example.sw2.entity.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario,String> {
    List<Inventario> findAllByOrderByFechamodificacionDesc();
    List<Inventario> findAllByOrderByNumpedidoDesc();
    List<Inventario> findInventariosByArtesanos_Codigo(String codigo);
    Inventario findByCodigoinventario(String cod);
    Optional<Inventario> findInventariosByNumpedido(int num);

    @Query(value = "select * from Inventario i where i.fecha_vencimiento_consignacion is not null and month(i.fecha_vencimiento_consignacion)=?1", nativeQuery = true)
    List<Inventario> findInvPorFechasDeVencimiento(int m);

    //List<Inventario> findInventariosByFechavencimientoconsignacion_Month(int i);

    Optional<Inventario> findInventarioByCodigoinventarioAndCantidadgestorIsGreaterThan(String cod, int num);
}
