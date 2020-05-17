package com.example.sw2.repository;


import com.example.sw2.entity.AsignadosSedes;
import com.example.sw2.entity.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AsignadosSedesRepository extends JpaRepository<AsignadosSedes,Integer> {

    List<AsignadosSedes> findBySede(Usuarios sede);

}
