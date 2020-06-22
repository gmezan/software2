package com.example.sw2.repository;


import com.example.sw2.dto.NotificationNumber;
import com.example.sw2.entity.Notifica;
import com.example.sw2.entity.Usuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public interface NotificaRepository extends JpaRepository<Notifica,Integer> {
    
    @Procedure(procedureName = "delete_notifications")
    void delete_notifications(int idusuario);

    List<Notifica> findNotificasByUsuario_Idusuarios(int idusuario);

    @Query(value = "select *  from Notificaciones where usuario=?1 order by fecha_creacion asc limit ?2", nativeQuery = true)
    List<Notifica> shortList(int idusuario, int limit);

    @Query(value = "select count(*) as `value` from Notificaciones where usuario=?1", nativeQuery = true)
    NotificationNumber getNotificationNumber(int id);


}
