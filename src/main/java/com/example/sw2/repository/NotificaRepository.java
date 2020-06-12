package com.example.sw2.repository;


import com.example.sw2.entity.Notifica;
import com.example.sw2.entity.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificaRepository extends JpaRepository<Notifica,Integer> {

    @Procedure(procedureName = "delete_notifications")
    void delete_notifications(int idusuario);

    List<Notifica> findNotificasByUsuarios_Idusuarios(int idusuario);

}
