package com.example.sw2.repository;


import com.example.sw2.constantes.AsignadosSedesId;
import com.example.sw2.entity.AsignadosSedes;
import com.example.sw2.entity.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AsignadosSedesRepository extends JpaRepository<AsignadosSedes, AsignadosSedesId> {

    @Query(value="SELECT ass.*\n" +
            "FROM Asignados_sedes ass\n" +
            "WHERE sede=?1",nativeQuery=true)

    List<AsignadosSedes> buscarPorSede(int sede);

}
