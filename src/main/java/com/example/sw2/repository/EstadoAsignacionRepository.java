package com.example.sw2.repository;

import com.example.sw2.entity.EstadoAsignacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoAsignacionRepository extends JpaRepository<EstadoAsignacion,Integer> {
}
