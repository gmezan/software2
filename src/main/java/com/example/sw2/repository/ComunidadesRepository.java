package com.example.sw2.repository;

import com.example.sw2.entity.Comunidades;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ComunidadesRepository extends JpaRepository<Comunidades,String> {

    Optional<Comunidades> findComunidadesByNombre(String nombre);

}

