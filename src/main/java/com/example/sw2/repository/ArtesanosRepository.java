package com.example.sw2.repository;

import com.example.sw2.entity.Artesanos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtesanosRepository extends JpaRepository<Artesanos,String> {
}
