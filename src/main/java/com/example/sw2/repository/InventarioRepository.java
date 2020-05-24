package com.example.sw2.repository;


import com.example.sw2.entity.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario,String> {

    List<Inventario> findInventariosByArtesanos_Codigo(String codigo);
    Inventario findByCodigoinventario(String cod);

}
