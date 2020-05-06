package com.example.sw2.repository;


import com.example.sw2.entity.Notifica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificaRepository extends JpaRepository<Notifica,Integer> {
}
