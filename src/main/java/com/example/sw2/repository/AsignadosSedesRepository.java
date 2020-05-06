package com.example.sw2.repository;


import com.example.sw2.entity.AsignadosSedes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AsignadosSedesRepository extends JpaRepository<AsignadosSedes,Integer> {
}
